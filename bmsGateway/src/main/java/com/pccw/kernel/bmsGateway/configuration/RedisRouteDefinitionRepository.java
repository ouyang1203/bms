//package com.pccw.kernel.bmsGateway.configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
//import org.springframework.cloud.gateway.support.NotFoundException;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
///**
//*
//* 核心配置类，加载数据库的路由配置信息到redis
//* 将定义好的路由表信息通过此类读写到redis中
//* 调试过程中发现开启这个注解则会导致路由功能失效,不使用
//*/
//@Component
//public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {
//	
//	public static final String GATEWAY_ROUTES = "gateway:routes";
//	@Autowired
//	private RedisTemplate redisTemplate;
//
//	@Override
//	public Flux<RouteDefinition> getRouteDefinitions() {
//		List<RouteDefinition> routeDefinitions = new ArrayList<>();
//        redisTemplate.opsForHash().values(GATEWAY_ROUTES).stream().forEach(routeDefinition -> {
//            routeDefinitions.add(JSON.parseObject(routeDefinition.toString(), RouteDefinition.class));
//        });
//        return Flux.fromIterable(routeDefinitions);
//	}
//
//	@Override
//	public Mono<Void> save(Mono<RouteDefinition> route) {
//		 return route
//	                .flatMap(routeDefinition -> {
//	                    redisTemplate.opsForHash().put(GATEWAY_ROUTES, routeDefinition.getId(),
//	                            JSON.toJSONString(routeDefinition));
//	                    return Mono.empty();
//	                });
//	}
//
//	@Override
//	public Mono<Void> delete(Mono<String> routeId) {
//		 return routeId.flatMap(id -> {
//	            if (redisTemplate.opsForHash().hasKey(GATEWAY_ROUTES, id)) {
//	                redisTemplate.opsForHash().delete(GATEWAY_ROUTES, id);
//	                return Mono.empty();
//	            }
//	            return Mono.defer(() -> Mono.error(new NotFoundException("路由文件没有找到: " + id)));
//	        });
//	}
//	
//}
