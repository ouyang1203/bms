package com.pccw.kernel.bmsGateway.fallback;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 网关默认熔断实现
 * */
@RestController
public class FallbackController {
	
	@RequestMapping(value="/defaultFallback")
	public Map<String , String> defaultFallback() {
		Map<String , String> map = new HashMap<String , String>();
		map.put("code", "e");
		map.put("message", "服务异常");
		return map;
	}
}
