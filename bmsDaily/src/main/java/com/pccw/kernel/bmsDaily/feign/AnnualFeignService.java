package com.pccw.kernel.bmsDaily.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.pccw.kernel.bmsDaily.interceptor.FeignConfiguration;

@FeignClient(value = "GATEWAY",fallback=AnnualFeignServiceHystric.class,configuration=FeignConfiguration.class)
public interface AnnualFeignService {
	/**
	 * 根据日常参数查询对应科目下达金额
	 * */
	@PostMapping(value="/annual_service/annual/calAnnualAmount")
	public String calAnnualAmount(@RequestBody String daily);
	
	/**
	 * 测试日常调用年度get方法在gateway中日志输出情况
	 * */
	@GetMapping(value="/annual_service/annual/testGet")
	public String testAnnualGet(@RequestParam String name,@RequestParam String code);
	
}
