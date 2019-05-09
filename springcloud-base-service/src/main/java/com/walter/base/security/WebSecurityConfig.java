package com.walter.base.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.web.context.SecurityContextRepository;

import com.walter.base.security.authenticate.CustomAuthenticationFailureHandler;
import com.walter.base.security.authenticate.CustomAuthenticationSuccessHandler;
import com.walter.base.security.props.CustomeSecurityProperties;

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
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	@Autowired
	@Qualifier("customSecurityContextRepository")
	private SecurityContextRepository securityContextRepository;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (customeSecurityProperties.getHttpBasicEnable()) {
			enableSecurity(http);
		} else {
			http.httpBasic().disable();
		}
	}

	protected void enableSecurity(HttpSecurity http) throws Exception {
		http
			.securityContext()
				.securityContextRepository(securityContextRepository)
				.and()
			// 自定义表单认证
			.formLogin()
				// 登录处理的URL
				.loginProcessingUrl(FORM_LOGIN_PROCESSING_URL)
				// 认证成功/失败后的处理器
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailureHandler)
				.and()
			// 配置权限
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, FORM_LOGIN_PROCESSING_URL).permitAll()
//				.antMatchers("/admin/**").authenticated()
				.anyRequest().authenticated()
				.and()
			.csrf()
				.disable();
	}

	/** 表单认证提交的目标URL */
	public static final String FORM_LOGIN_PROCESSING_URL = "/login";
	/** 手机短信验证码认证提交的目标URL */
	public static final String SMS_VALIDATION_CODE_LOGIN_URL = "/sms/login";

	/** 图片验证码在表单中的name */
	public static final String CUSTOM_SECURITY_FORM_CAPTCHA_KEY = "captcha";
	/** 手机短信验证码在表单中的name */
	public static final String CUSTOM_SECURITY_SMA_VALIDATION_CODE_KEY = "sms-validation-code";
}
