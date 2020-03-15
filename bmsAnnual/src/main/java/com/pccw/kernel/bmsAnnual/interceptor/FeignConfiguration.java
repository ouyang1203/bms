package com.pccw.kernel.bmsAnnual.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 给feign调用的时候添加请求头Token
 * 请求头上需要有Authorization ：Bearer defa5c25-44a5-4d2c-8535-568abf239826
 * */
@Configuration
public class FeignConfiguration implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        template.header("Authorization",request.getHeader("Authorization"));
	}

}
