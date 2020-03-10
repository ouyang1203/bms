package com.pccw.kernel.bmsGateway.filter;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 所有经过网关的请求日志输出，目前发现get请求会在网关启动时进两次这个方法，恼火
 * */
@Component
public class AccessLogGlobalFilter implements GlobalFilter , Ordered {
	
	private Logger log_ = LoggerFactory.getLogger(AccessLogGlobalFilter.class);
	
	private static final String REQUEST_PREFIX = "{'Request Info': { ";
	 
    private static final String REQUEST_TAIL = " }";
 
    private static final String RESPONSE_PREFIX = "{'Response Info':{ ";
 
    private static final String RESPONSE_TAIL = " }";
 
    //记录请求开始时间
    private static final String START_TIME = "startTime";
 
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	StringBuilder normalMsg = new StringBuilder();
    	//设置网关请求开始时间
    	exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
        ServerHttpRequest request = exchange.getRequest();
        RecorderServerHttpRequestDecorator requestDecorator = new RecorderServerHttpRequestDecorator(request);
        InetSocketAddress address = requestDecorator.getRemoteAddress();
        HttpMethod method = requestDecorator.getMethod();
        URI url = requestDecorator.getURI();
        HttpHeaders headers = requestDecorator.getHeaders();
        Flux<DataBuffer> body = requestDecorator.getBody();
        //读取requestBody传参
        AtomicReference<String> requestBody = new AtomicReference<>("");
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            requestBody.set(charBuffer.toString());
        });
        String requestParams = requestBody.get();
        String realIp = headers.getFirst("X-Real-IP");
        if(StringUtils.isBlank(realIp)) {
        	realIp = requestDecorator.getRemoteAddress().toString();
        }
        if(StringUtils.isBlank(requestParams)) {
        	requestParams = "''";
        }
        normalMsg.append(REQUEST_PREFIX);
        normalMsg.append("'header':").append(headers);
        normalMsg.append(",'params':").append(requestParams);
        normalMsg.append(",'address':").append(address.getHostName() + address.getPort());
        normalMsg.append(",'method':").append(method.name());
        normalMsg.append(",'url':").append(url.getPath());
        normalMsg.append(REQUEST_TAIL);
 
        ServerHttpResponse response = exchange.getResponse();
 
        DataBufferFactory bufferFactory = response.bufferFactory();
        normalMsg.append(RESPONSE_PREFIX);
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        // probably should reuse buffers
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放内存
                        DataBufferUtils.release(dataBuffer);
                        String responseResult = new String(content, Charset.forName("UTF-8"));
                        normalMsg.append("'status':").append(this.getStatusCode());
                        normalMsg.append(",'header':").append(this.getHeaders());
                        normalMsg.append(",'responseResult':").append(responseResult);
                        normalMsg.append(RESPONSE_TAIL);
                        log_.info(normalMsg.toString());
                        //将response写回到请求中(这里不写的话feign调用会在调用方出现read time out 异常)
                        byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                // if body is not a flux. never got there.
                return super.writeWith(body); 
            }
        };
 
        return chain.filter(exchange.mutate()
        		.request(requestDecorator)
        		.response(decoratedResponse)
        		.build()).then(Mono.fromRunnable(()->{
        			Long startTime = exchange.getAttribute(START_TIME);
        			if(null!=startTime) {
        				Long executeTime = (System.currentTimeMillis() - startTime);
        				log_.info("url :{} executeTime is:{} ms",url,executeTime);
        			}
        		}) );
    }
 
    @Override
    public int getOrder() {
        return -2;
    }
}
