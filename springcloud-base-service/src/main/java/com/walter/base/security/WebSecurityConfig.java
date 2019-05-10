package com.walter.base.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

import com.walter.base.security.authorize.CustomHttp403ForbiddenEntryPoint;
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
	@Qualifier("customAuthenticationSuccessHandler")
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	@Qualifier("customAuthenticationFailureHandler")
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	@Qualifier("customSecurityContextRepository")
	private SecurityContextRepository securityContextRepository;
	@Autowired
	@Qualifier("customLogoutSuccessHandler")
	private LogoutSuccessHandler logoutSuccessHandler;

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
				// 使用Redis代替默认的HttpSession来保存SecurityContext
				.securityContextRepository(securityContextRepository)
				.and()
			// 自定义表单认证
			.formLogin()
				// 登录处理的URL
				.loginProcessingUrl(FORM_LOGIN_PROCESSING_URL)
				// 认证成功/失败后的处理器
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.and()
			.logout()
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
			// 配置权限
			.authorizeRequests()
				.antMatchers(FORM_LOGIN_PROCESSING_URL, FORM_LOGOUT_PROCESSING_URL).permitAll()
				.antMatchers("/admin/**").authenticated()
//				.anyRequest().authenticated()
				.and()
			.exceptionHandling()
				// 自定义无权限时的处理行为
				.authenticationEntryPoint(new CustomHttp403ForbiddenEntryPoint())
				.and()
			.csrf()
				.disable();
	}

	/** 表单认证提交的目标URL */
	public static final String FORM_LOGIN_PROCESSING_URL = "/login";
	/** 退出登录的URL */
	public static final String FORM_LOGOUT_PROCESSING_URL = "/logout";
	/** 手机短信验证码认证提交的目标URL */
	public static final String SMS_VALIDATION_CODE_LOGIN_URL = "/sms/login";

	/** 图片验证码在表单中的name */
	public static final String CUSTOM_SECURITY_FORM_CAPTCHA_KEY = "captcha";
	/** 手机短信验证码在表单中的name */
	public static final String CUSTOM_SECURITY_SMA_VALIDATION_CODE_KEY = "sms-validation-code";
}
