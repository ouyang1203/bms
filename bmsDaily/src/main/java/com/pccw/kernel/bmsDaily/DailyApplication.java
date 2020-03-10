package com.pccw.kernel.bmsDaily;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DailyApplication {
	private static Logger log_ = LoggerFactory.getLogger(DailyApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DailyApplication.class, args);
		log_.info("daily service started");
	}
}
