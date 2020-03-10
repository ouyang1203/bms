package com.pccw.kernel.bmsGateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.pccw.kernel.bmsGateway.service.GatewayRouteService;
import com.pccw.kernel.bmsGateway.vo.GatewayRouteVo;

@RestController
public class GatewayController {
	@Autowired
	private GatewayRouteService gatewayRouteService;
	
	@RequestMapping(value="/test")
	public String test() {
		return "gateway";
	}
	/**
	 * 查询所有生效的路由http://localhost:5555/actuator/gateway/routes
	 * */
	@RequestMapping(value="/findAll")
	public String findAll() {
		JSONObject obj = new JSONObject();
		Integer status = 0 ; 
		String statusText = "";
		try {
			List<GatewayRouteVo> gates = gatewayRouteService.findAll();
			obj.put("List", gates);
		} catch (Exception e) {
			status = 500;
			statusText = e.getMessage();
			e.printStackTrace();
		}
		obj.put("status", status);
		obj.put("statusText", statusText);
		return obj.toString();
	}
	/**
	 * 手动加载数据库中路由
	 *  * 请求范例: http://localhost:5555/refreshRoute
	 * 请求方法:GET
	 * */
	@GetMapping(value="/refreshRoute")
	public String refreshRoute() {
		JSONObject obj = new JSONObject();
		Integer status = 0 ; 
		String statusText = "";
		try {
			gatewayRouteService.refreshRoute();
		} catch (Exception e) {
			status = 500;
			statusText = e.getMessage();
			e.printStackTrace();
		}
		obj.put("status", status);
		obj.put("statusText", statusText);
		return obj.toString();
	}
	
	/**
	 * 添加路由
	 * 请求范例: http://localhost:5555/addRoute
	 * 请求参数:
	 * {
			"gateway_routes":{"routeId":"test","routeUri":"lb://TEST","routeOrder":0,"predicates":[{"name":"Path","args":{"pattern":"/test/test/**"}}],"filters":[{"name":"StripPrefix","args":{"_genkey_0":"1"}}],"route_enable":"Y","created_by":"222","updated_by":"222"}
		}
	 * 请求方法:POST
	 * */
	@PostMapping(value="/addRoute")
	public  String addRoute(@RequestBody String json) {
		JSONObject obj = new JSONObject();
		Integer status = 0 ; 
		String statusText = "";
		try {
			gatewayRouteService.addGatewayRoute(json);
		} catch (Exception e) {
			status = 500;
			statusText = e.getMessage();
			e.printStackTrace();
		}
		obj.put("status", status);
		obj.put("statusText", statusText);
		return obj.toString();
	}
	/**
	 * 删除路由
	 * 请求范例: http://localhost:5555/delRoute
	 * 请求参数:
	 * {
			"id":"5","route_id":"test"
		}
	 * 请求方法:POST
	 * */
	@PostMapping(value="/delRoute")
	public  String delRoute(@RequestBody String json) {
		JSONObject obj = new JSONObject();
		Integer status = 0 ; 
		String statusText = "";
		try {
			gatewayRouteService.deleteGatewayRoute(json);
		} catch (Exception e) {
			status = 500;
			statusText = e.getMessage();
			e.printStackTrace();
		}
		obj.put("status", status);
		obj.put("statusText", statusText);
		return obj.toString();
	}
	/**
	 * 更新路由
	 * 请求范例: http://localhost:5555/updateRoute
	 * 请求参数:
	 * {
			"gateway_routes":{"id":"6","routeId":"test_v2","routeUri":"lb://TEST2","routeOrder":0,"predicates":[{"name":"Path","args":{"pattern":"/test/test/**"}}],"filters":[{"name":"StripPrefix","args":{"_genkey_0":"1"}}],"route_enable":"Y","created_by":"222","updated_by":"222"}
		}
	 * 请求方法:POST
	 * */
	@PostMapping(value="/updateRoute")
	public  String updateRoute(@RequestBody String json) {
		JSONObject obj = new JSONObject();
		Integer status = 0 ; 
		String statusText = "";
		try {
			gatewayRouteService.updateGatewayRoute(json);
		} catch (Exception e) {
			status = 500;
			statusText = e.getMessage();
			e.printStackTrace();
		}
		obj.put("status", status);
		obj.put("statusText", statusText);
		return obj.toString();
	}
}
