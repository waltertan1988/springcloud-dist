package com.walter.base.security;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

import com.walter.base.security.authenticate.filter.CaptchaValidationCodeFilter;
import com.walter.base.security.authorize.CustomFilterInvocationSecurityMetadataSource;
import com.walter.base.security.authorize.CustomHttp403ForbiddenEntryPoint;
import com.walter.base.security.props.CustomeSecurityProperties;
import com.walter.base.security.service.RoleHierarchyService;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CustomeSecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomeSecurityProperties customeSecurityProperties;
	@Autowired
	private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
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
	@Autowired
	private CaptchaValidationCodeFilter captchaValidationCodeFilter;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private RoleHierarchyService roleHierarchyService;

	@Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(
            new WebExpressionVoter(),
            //添加层次角色投票者
            new RoleHierarchyVoter(roleHierarchy()),
            new AuthenticatedVoter());
        return new AffirmativeBased(decisionVoters);
    }
	
	@Bean
	public RoleHierarchy roleHierarchy(){
		// 层次角色
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(roleHierarchyService.getRoleHierarchyString());
        return roleHierarchy;
    }
	
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
			// 自定义图片验证码认证过滤器
			.addFilterBefore(captchaValidationCodeFilter, UsernamePasswordAuthenticationFilter.class)
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
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
						// 自定义SecurityMetadataSource
						customFilterInvocationSecurityMetadataSource.setSecurityMetadataSource(fsi.getSecurityMetadataSource());
						customFilterInvocationSecurityMetadataSource.setupSecurityMetadataSource();
						fsi.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
						
						// 自定义AccessDecisionManager并添加RoleVoter
						fsi.setAccessDecisionManager(accessDecisionManager());
						
						return fsi;
					}
				})
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
	/** 图片验证码的缓存key在header中的 name */
	public static final String CUSTOM_SECURITY_FORM_CAPTCHA_CACHE_KEY = "captcha-cache-key";
	/** 手机短信验证码在表单中的name */
	public static final String CUSTOM_SECURITY_SMA_VALIDATION_CODE_KEY = "sms-validation-code";
}
