package org.walter.base.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.walter.base.security.authenticate.CustomAuthenticationFailureHandler;
import org.walter.base.security.authenticate.CustomAuthenticationSuccessHandler;
import org.walter.base.security.authenticate.filter.ValidateCodeFilter;
import org.walter.base.security.utils.CustomeSecurityProperties;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CustomeSecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private CustomeSecurityProperties customeSecurityProperties;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	@Autowired
	private ValidateCodeFilter validateCodeFilter;
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}

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
		http
			// 自定义过滤器 - 验证码校验
			.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
			// 自定义表单认证
			.formLogin()
				// 自定义登录页面请求，可以决定返回html登录页面还是json数据
				.loginPage(LOGIN_PAGE_DECISION_URL)
				// 登录处理的URL
				.loginProcessingUrl(LOGIN_URL)
				// 认证成功/失败后的处理器
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailureHandler)
				.and()
			// 配置RememberMe功能
			.rememberMe()
				// 保存RememberMe的token的方式为数据库保存
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(3600)
				.userDetailsService(userDetailsService)
				.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, LOGIN_PAGE_DECISION_URL).permitAll()
			.antMatchers("/admin/**").authenticated()
			.and()
			.csrf().disable();
	}
	
	private final String LOGIN_PAGE_DECISION_URL = "/loginPageDecision";
	public static final String LOGIN_URL = "/login";
	public static final String CUSTOM_SECURITY_FORM_CAPTCHA_KEY = "captcha";
}
