package com.pccw.kernel.bmsAnnual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableFeignClients
public class AnnualApplication {
	private static Logger log_ = LoggerFactory.getLogger(AnnualApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AnnualApplication.class, args);
		log_.info("annual service started");
	}
}
