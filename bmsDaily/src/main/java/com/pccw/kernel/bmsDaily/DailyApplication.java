package com.pccw.kernel.bmsDaily;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 禁用spring security,暂时不连接数据源
 * */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,DataSourceAutoConfiguration.class })
@EnableFeignClients
public class DailyApplication {
	private static Logger log_ = LoggerFactory.getLogger(DailyApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DailyApplication.class, args);
		log_.info("daily service started");
	}
}
