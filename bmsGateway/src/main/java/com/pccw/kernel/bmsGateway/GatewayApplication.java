package com.pccw.kernel.bmsGateway;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@MapperScan(basePackages="com.pccw.kernel.bmsGateway.mapper")
public class GatewayApplication {
	
	private static Logger log_ = LoggerFactory.getLogger(GatewayApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
		log_.info("Gateway server started");
	}
}
