package com.pccw.kernel.bmsAnnual.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "GATEWAY",fallback=DailyFeignServiceHystric.class)
public interface DailyFeignService {
	/**
	 * 查询日常科目申报金额
	 * */
	@PostMapping(value="/daily_service/daily/calDailyAmount")
	public String calDailyAmount(@RequestBody String annual);
}
