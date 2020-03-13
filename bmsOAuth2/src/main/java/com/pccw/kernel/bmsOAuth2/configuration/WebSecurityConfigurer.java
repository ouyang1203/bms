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

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter{
	@Autowired
	private Oauth2UserDetailsService oauth2UserDetailsService;

	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable();
	    http.requestMatchers().antMatchers("/oauth/**")
	    .and()
	    .authorizeRequests()
	    .antMatchers("/oauth/**").authenticated();
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
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(oauth2UserDetailsService);
	}
}
