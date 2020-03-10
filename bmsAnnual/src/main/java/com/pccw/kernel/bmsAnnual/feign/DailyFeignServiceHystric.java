package com.pccw.kernel.bmsAnnual.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DailyFeignServiceHystric implements DailyFeignService{
	
	private Logger log_ = LoggerFactory.getLogger(DailyFeignServiceHystric.class);

	@Override
	public String calDailyAmount(String annual) {
		log_.info("annual param is {} ,request fail ,Hystric auto");
		return "annual param is "+annual+" ,request fail ,Hystric auto";
	}

}
