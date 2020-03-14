package com.pccw.kernel.bmsOAuth2;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 获取token范例
 * password模式：localhost:3355/oauth/token?client_id=password_client&client_secret=server&grant_type=password&username=admin1&password=123456
 * refresh_token:localhost:3355/oauth/token?client_id=password_client&client_secret=server&grant_type=refresh_token&refresh_token=cbd9b246-d7f1-46b9-af30-144e21663527
 * client_credentials:localhost:3355/oauth/token?client_id=client_credentials&client_secret=server&grant_type=client_credentials
 * authorization_code:
 * 		1、获取code:http://localhost:3355/oauth/authorize?response_type=code&client_id=authorization_code&redirect_uri=http://www.baidu.com
 * 		2、获取token:http://localhost:3355/oauth/token?client_id=authorization_code&client_secret=server&grant_type=authorization_code&redirect_uri=http://www.baidu.com&code=W0k3pd
 * */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAuthorizationServer
public class OAuth2Application {
	
	private static Logger log_ = LoggerFactory.getLogger(OAuth2Application.class);
	
	@Autowired
	private Environment env;
	
	/**
	 * 配置spring datasource
	 * */
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
		dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setInitialSize(2);
		dataSource.setMaxActive(20);
		dataSource.setMinIdle(0);
		dataSource.setMaxWait(60000);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setTestOnBorrow(false);
		dataSource.setTestWhileIdle(true);
		dataSource.setPoolPreparedStatements(false);
		return dataSource;
	}
	
	/**
	 * 添加事务管理
	 * */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
	
	public static void main(String[] args) {
		SpringApplication.run(OAuth2Application.class, args);
		log_.info("OAuth2 server started");
	}
}
