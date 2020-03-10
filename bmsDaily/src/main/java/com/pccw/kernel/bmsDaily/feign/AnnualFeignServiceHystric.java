package com.pccw.kernel.bmsDaily.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AnnualFeignServiceHystric implements AnnualFeignService{
	
	private Logger log_ = LoggerFactory.getLogger(AnnualFeignServiceHystric.class);

	@Override
	public String calAnnualAmount(String daily) {
		log_.info("daily param is :{} request fail ,Hystric auto",daily);
		return "daily param is:"+daily+"request fail ,Hystric auto";
	}

	@Override
	public String testAnnualGet(String name,String code) {
		log_.info("daily param is {},code is {}",name,code);
		return "daily param name is "+name+",code is "+code+",request fail ,Hystric auto";
	}

}
