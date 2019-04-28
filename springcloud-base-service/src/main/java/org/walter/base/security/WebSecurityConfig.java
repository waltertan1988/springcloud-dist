package org.walter.base.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.walter.base.security.authenticate.filter.CaptchaValidationCodeFilter;
import org.walter.base.security.authenticate.filter.SmsValidationCodeAuthenticationFilter;
import org.walter.base.security.authenticate.filter.SmsValidationCodeFilter;
import org.walter.base.security.authenticate.provider.SmsValidationCodeAuthenticationProvider;
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
	private CaptchaValidationCodeFilter captchaValidationCodeFilter;
	@Autowired
	private SmsValidationCodeFilter smsValidationCodeFilter;
	@Autowired
	private SmsValidationCodeAuthenticationProvider smsValidationCodeAuthenticationProvider;
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}
	
	protected SmsValidationCodeAuthenticationFilter buildSmsValidationCodeAuthenticationFilter(HttpSecurity http) throws Exception {
		SmsValidationCodeAuthenticationFilter filter = new SmsValidationCodeAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
		return filter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

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
			// 自定义图片验证码认证过滤器
			.addFilterBefore(captchaValidationCodeFilter, UsernamePasswordAuthenticationFilter.class)
			// 自定义短信验证码校验过滤器、认证过滤器、认证提供者
			.addFilterBefore(smsValidationCodeFilter, UsernamePasswordAuthenticationFilter.class)
			.authenticationProvider(smsValidationCodeAuthenticationProvider)
			.addFilterAfter(buildSmsValidationCodeAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class)
			// 自定义表单认证
			.formLogin()
				// 自定义登录页面请求，可以决定返回html登录页面还是json数据
				.loginPage(LOGIN_PAGE_DECISION_URL)
				// 登录处理的URL
				.loginProcessingUrl(FORM_LOGIN_URL)
				// 认证成功/失败后的处理器
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailureHandler)
				.and()
			// 配置RememberMe功能
			.rememberMe()
				// 保存RememberMe的token的方式为数据库保存
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(customeSecurityProperties.getLogin().getRememberMeTokenValiditySeconds())
				.userDetailsService(userDetailsService)
				.and()
			// 配置权限
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, LOGIN_PAGE_DECISION_URL).permitAll()
				.antMatchers("/admin/**").authenticated()
				.and()
			.csrf()
				.disable();
	}
	
	private final String LOGIN_PAGE_DECISION_URL = "/loginPageDecision";
	public static final String FORM_LOGIN_URL = "/login";
	public static final String SMS_VALIDATION_CODE_LOGIN_URL = "/sms/login";
	public static final String CUSTOM_SECURITY_FORM_CAPTCHA_KEY = "captcha";
	public static final String CUSTOM_SECURITY_SMA_VALIDATION_CODE_KEY = "sms-validation-code";
}
