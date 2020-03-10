package com.pccw.kernel.bmsDaily.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pccw.kernel.bmsDaily.feign.AnnualFeignService;

@RestController
@RequestMapping(value="/daily")
public class DailyController {

	private static Logger log_ = LoggerFactory.getLogger(DailyController.class);
	
	@Autowired
	private AnnualFeignService annualFeignService;
	
	@RequestMapping("/home")
	@ResponseBody
	public String daily() {
		log_.info("daily visit");
		return "daily_service_home";
	}
	
	/**
	 * 日常金额校验
	 * @param 
	 * {
	 * 		"dailyAmount":"8","dailyInfo":{"budget_account_code":"PL0201","dept_code":"33100210"}
	 * }
	 * */
	@PostMapping(value="/dailyAmountCheck")
	public String dailyAmountCheck(@RequestBody String dailyParam) {
		log_.info("dailyParam is {}",dailyParam);
		JSONObject daily = JSON.parseObject(dailyParam);
		BigDecimal dailyAmount = new BigDecimal(daily.getString("dailyAmount"));
		String dailyInfo = daily.getString("dailyInfo");
		String annualAmount = annualFeignService.calAnnualAmount(dailyInfo);
		log_.info("dailyInfo is {},annual service return is {},dailyAmount is {}",dailyInfo,annualAmount,dailyAmount);
		return "dailyInfo is "+dailyInfo+",annual service return is "+annualAmount+",dailyAmount is"+dailyAmount;
	}
	
	@PostMapping(value="/calDailyAmount")
	public String calDailyAmount(@RequestBody String annualparam) {
		log_.info("annualparam is {}",annualparam);
		return "8";
	}
	@GetMapping(value="/testGet")
	public String testGet(@RequestParam String name) {
		return annualFeignService.testAnnualGet(name);
	}
	
}
