package com.pccw.kernel.bmsAnnual.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pccw.kernel.bmsAnnual.feign.DailyFeignService;

@RestController
@RequestMapping(value="/annual")
public class AnnualController {
	
	private static Logger log_ = LoggerFactory.getLogger(AnnualController.class);
	
	@Autowired
	private DailyFeignService dailyFeignService;
	
	@RequestMapping(value="/annual")
	public String annual() {
		log_.info("annual visited");
		return "annual";
	}
	
	@RequestMapping(value="/sayHi")
	public String sayHi(@RequestParam(value="name") String name) {
		return "Hi :"+name;
	}
	@PostMapping(value="/calAnnualAmount")
	public String calAnnualAmount(@RequestBody String dailyParam) {
		log_.info("daily servce post params is :{}",dailyParam);
		return "15";
	}
	/**
	 * 年度金额校验
	 * @param 
	 * {
	 * 		"annualAmount":"12","annualInfo":{"budget_account_code":"PL0201","dept_code":"33100210"}
	 * }
	 * */
	@PostMapping(value="/checkAnnualAmount")
	public String checkAnnualAmount(@RequestBody String annualParam) {
		log_.info("annualParam is {}",annualParam);
		JSONObject annual = JSON.parseObject(annualParam);
		BigDecimal annualAmount = new BigDecimal(annual.getString("annualAmount"));
		String annualInfo = annual.getString("annualInfo");
		String dailyAmount = dailyFeignService.calDailyAmount(annualInfo);
		log_.info("annualAmount is {},annualInfo is {},dailyAmount is {} ",annualAmount,annualInfo,dailyAmount);
		return "annualAmount is "+annualAmount+",annualInfo is "+annualInfo+",dailyAmount is "+dailyAmount;
	}
	
	/**
	 * 测试get请求在gateway中日志打印
	 * */
	@GetMapping(value="/testGet")
	public String testGet(@RequestParam String name,@RequestParam String code) {
		log_.info("name is {},code is {}",name,code);
		return "annual say hello to daily user:"+name+",code is "+code;
	}
}
