//package com.pccw.kernel.bmsEurzka.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * springcloud2.0中Spring Security默认开启了CSRF攻击防御,需要屏蔽这个
// * */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	 @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.authorizeRequests().anyRequest().authenticated().and().httpBasic().and().csrf().disable();
//     }
//}
