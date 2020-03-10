package com.pccw.kernel.bmsGateway.service;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.pccw.kernel.bmsGateway.configuration.RedisRouteDefinitionRepository;
import com.pccw.kernel.bmsGateway.mapper.GatewayRouteMapper;
import com.pccw.kernel.bmsGateway.vo.GatewayRouteVo;

import reactor.core.publisher.Mono;

@Service
public class GatewayRouteService implements ApplicationEventPublisherAware{
	
	@Autowired
	private GatewayRouteMapper gatewayRouteMapper;
	//使用redis存储路由信息
//	@Autowired
//	private RedisRouteDefinitionRepository routeDefinitionWriter;
	@Autowired
	private RouteDefinitionWriter routeDefinitionWriter;
	
	private ApplicationEventPublisher publisher;
	
	public List<GatewayRouteVo> findAll(){
		return gatewayRouteMapper.findAll();
	}
	
	public GatewayRouteVo findGatewayRouteById(Integer id) {
		return gatewayRouteMapper.findGatewayRouteById(id);
	}
	/**
	 * 加载数据库中的路由
	 * */
	public void refreshRoute() throws Exception{
		List<GatewayRouteVo> routes = this.findAll();
		routes.forEach(item->{
			try {
				//添加路由信息到gateway
				routeDefinitionWriter.save(Mono.just(assembleRouteDefinition(item))).subscribe();
				//刷新路由状态
				notifyChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * 添加路由
	 * */
	public void addGatewayRoute(String routeJson) throws Exception{
		//JSON对象转换为路由对象
		GatewayRouteVo vo = assembleGatewayRoute(routeJson);
		//路由数据入库
		gatewayRouteMapper.addGatewayRoute(vo);
		//添加路由信息到gateway
		routeDefinitionWriter.save(Mono.just(assembleRouteDefinition(vo))).subscribe();
		//刷新路由状态
		notifyChanged();
	}
	/**
	 * 更新路由
	 * */
	public void updateGatewayRoute(String routeJson) throws Exception{
		//JSON对象转换为路由对象
		GatewayRouteVo vo = assembleGatewayRoute(routeJson);
		//删除原有路由
		routeDefinitionWriter.delete(Mono.just(vo.getRouteId()));
		//添加路由信息到gateway
		routeDefinitionWriter.save(Mono.just(assembleRouteDefinition(vo))).subscribe();
		gatewayRouteMapper.updateGatewayRoute(vo);
		notifyChanged();
	}
	/**
	 * 删除路由
	 * @param json{id:1,route_id:"annual-service"}
	 * */
//	public Mono<ResponseEntity<Object>> deleteGatewayRoute(String jsonStr) throws Exception{
	public void deleteGatewayRoute(String jsonStr) throws Exception{
		JSONObject json = JSONObject.parseObject(jsonStr);
		String route_id = json.getString("route_id");
		Integer id = json.getInteger("id");
		routeDefinitionWriter.delete(Mono.just(route_id)).subscribe() ;
		gatewayRouteMapper.deleteGatewayRoute(id);
		notifyChanged();
		/***
		return routeDefinitionWriter.delete(Mono.just(route_id)).then(Mono.defer(() -> {
			gatewayRouteMapper.deleteGatewayRoute(id);
            return Mono.just(ResponseEntity.ok().build());
        })).onErrorResume((t) -> {
            return t instanceof NotFoundException;
        }, (t) -> {
            return Mono.just(ResponseEntity.notFound().build());
        });
        **/
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
	
	/**
	 * 将前端传递的JSON字符串转换为路由对象
	 * */
	public GatewayRouteVo assembleGatewayRoute(String json) throws Exception{
		JSONObject obj = JSONObject.parseObject(json);
		GatewayRouteVo route =  obj.getObject("gateway_routes", GatewayRouteVo.class);
		Timestamp t = new Timestamp(System.currentTimeMillis());
		if(route.getId()==null) {
			route.setCreateTime(t);
		}
		route.setUpdateTime(t);
		return route;
	}
	
	/**
	 * 将动态路由对象封装为gateway路由对象
	 * */
	public RouteDefinition assembleRouteDefinition(GatewayRouteVo vo) throws Exception{
		RouteDefinition definition = new RouteDefinition();
		//设置路由ID
		definition.setId(vo.getRouteId());
		//设置路由URI
		String routeUri = vo.getRouteUri();
		URI uri = null;
		if(routeUri.startsWith("http")) {
			uri = UriComponentsBuilder.fromHttpUrl(routeUri).build().toUri();
		}else {
			uri = URI.create(routeUri);
		}
		definition.setUri(uri);
		//设置路由顺序
		definition.setOrder(vo.getRouteOrder());
		//设置断言
        List<PredicateDefinition> pdList=new ArrayList<PredicateDefinition>();
        String predicates = vo.getPredicates();
        JSONArray arr = JSONArray.parseArray(predicates);
        arr.forEach(item->{
        	JSONObject prei = (JSONObject)item;
        	PredicateDefinition predicate = new PredicateDefinition();
        	predicate.setName(prei.getString("name"));
        	String args = prei.getString("args");
        	JSONObject argsObj = JSONObject.parseObject(args);
        	Map argsMap = new HashMap();
        	argsObj.keySet().forEach(str->{
        		argsMap.put(str, argsObj.get(str));
        	});
        	predicate.setArgs(argsMap);
        	pdList.add(predicate);
        });
        definition.setPredicates(pdList);
        //设置路由filter
        String filterStr = vo.getFilters();
        if(filterStr!=null) {
        	List<FilterDefinition> filters = new ArrayList<FilterDefinition>();
            JSONArray filterArr = JSONArray.parseArray(filterStr);
            filterArr.forEach( item->{
            	JSONObject fil = (JSONObject)item;
            	FilterDefinition filter = new FilterDefinition();
            	filter.setName(fil.getString("name"));
            	String args = fil.getString("args");
            	JSONObject argsObj = JSONObject.parseObject(args);
            	Map argsMap = new HashMap();
            	argsObj.keySet().forEach(str->{
            		argsMap.put(str, argsObj.get(str));
            	});
                filter.setArgs(argsMap);
                filters.add(filter);
            });
            definition.setFilters(filters);
        }
		return definition;
	}
	
	/**
	 * 路由刷新
	 * */
	private void notifyChanged() throws Exception{
		this.publisher.publishEvent(new RefreshRoutesEvent(this));
	}
}
