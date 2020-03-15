package com.pccw.kernel.bmsEurzka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaServer
public class EurzkaApplication {
	private static Logger log_ = LoggerFactory.getLogger(EurzkaApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(EurzkaApplication.class, args);
		log_.info("Eurzka Server started");
	}
}
