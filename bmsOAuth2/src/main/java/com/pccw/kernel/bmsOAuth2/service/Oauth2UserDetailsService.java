package com.pccw.kernel.bmsOAuth2.service;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Oauth2UserDetailsService implements UserDetailsService{
	
	private Logger log_ = LoggerFactory.getLogger(Oauth2UserDetailsService.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log_.info("username is {}",username);
		 // TODO 这个地方可以通过username从数据库获取正确的用户信息，包括密码和权限等。
       return mockUser(username);
	}
	
	private User mockUser(String name) {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("admin"));
		if("test1".equals(name)) {
			//用于测试不同用户权限
			authorities.add(new SimpleGrantedAuthority("other"));
		}
		//查询用户中心的用户密码(实际使用时需要查询,开发过程中写死)
//		String pwd = userService.findUserPwdByAccount(name);
		User user = new User(name,passwordEncoder.encode("123456"),authorities);
		return user;
	}

}
