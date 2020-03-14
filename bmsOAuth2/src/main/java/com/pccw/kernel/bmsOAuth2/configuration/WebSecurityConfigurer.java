package com.pccw.kernel.bmsOAuth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pccw.kernel.bmsOAuth2.service.Oauth2UserDetailsService;

/**
 * 设置不同加密方式(org.springframework.security.crypto.factory.PasswordEncoderFactories中提供了加密方式)
 * bcrypt,ldap,MD4,MD5,noop,pbkdf2,scrypt,SHA-1,SHA-256,sha256
 * */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Oauth2UserDetailsService oauth2UserDetailsService;

	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
		 http
		 	.authorizeRequests()
		 	//设置不拦截的地址
		 	.antMatchers("/actuator/**","/oauth/**","/oauth/authorize").permitAll()
		 	.anyRequest().authenticated()
		 	.and().formLogin().permitAll()
		 	//关闭跨域访问
		 	.and().csrf().disable();
		 
	 }


	/**
	 * 需要配置这个支持password模式 support password grant type
	 * @return
	 * @throws Exception
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	/**
	 * 修复There is no PasswordEncoder mapped for the id "null"问题
	 * */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/**
	 * 设置用户信息校验实现类
	 * */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(oauth2UserDetailsService);
	}
}
