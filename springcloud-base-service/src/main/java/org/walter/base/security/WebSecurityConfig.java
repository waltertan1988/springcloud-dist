package org.walter.base.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.walter.base.security.authenticate.CustomAuthenticationSuccessHandler;
import org.walter.base.security.utils.CustomeSecurityProperties;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CustomeSecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomeSecurityProperties customeSecurityProperties;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	// 用户认证
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	// 请求授权
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if(customeSecurityProperties.getHttpBasicEnable()) {
			enableSecurity(http);
		}else {
			http.httpBasic().disable();
		}
	}

	protected void enableSecurity(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(LOGIN_PAGE_DECISION_URL)
			.loginProcessingUrl("/login")
			.successHandler(customAuthenticationSuccessHandler)
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, LOGIN_PAGE_DECISION_URL).permitAll()
			.antMatchers("/admin/**").authenticated()
			.and()
			.csrf().disable();
	}
	
	private final String LOGIN_PAGE_DECISION_URL = "/loginPageDecision";
}
