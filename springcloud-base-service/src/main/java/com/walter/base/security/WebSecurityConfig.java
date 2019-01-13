package com.walter.base.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.walter.base.security.authorize.CustomFilterInvocationSecurityMetadataSource;
import com.walter.base.security.authorize.CustomRegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${custom.security.httpBasic.enable}")
	private boolean isCustomSecurityHttpBasicEnable;

	@Autowired
	private UserDetailsService userDetailsService;

	// 用户认证
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	// 请求授权
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if(isCustomSecurityHttpBasicEnable) {
			enableSecurity(http);
		}else {
			http.httpBasic().disable();
		}
	}

	protected void enableSecurity(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					// 自定义授权管理
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
						// 设置自己的securityMetadataSource
						fsi.setSecurityMetadataSource(securityMetadataSource());
						
						// 自定义AccessDecisionManager并添加RoleVoter
						List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
						decisionVoters.add(roleVoter());
						AccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters);
						fsi.setAccessDecisionManager(accessDecisionManager);
						
						return fsi;
					}
				})
			.anyRequest()
				.denyAll()
		.and()
			.sessionManagement().sessionFixation().changeSessionId()
								.maximumSessions(1)
								.maxSessionsPreventsLogin(false)
								.sessionRegistry(sessionRegistry())
//								.expiredUrl("/login")
								.and().invalidSessionUrl("/login")
		.and()
			.formLogin()
		.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
		.and()
			.httpBasic();
	}

	/**
	 * 动态管理权限需要用此SecurityMetadataSource
	 * @return
	 */
	@Bean
	public CustomFilterInvocationSecurityMetadataSource securityMetadataSource() {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		RequestMatcher requestMatcher1 = new CustomRegexRequestMatcher("/admin/.*", null);
		requestMap.put(requestMatcher1, SecurityConfig.createList("ROLE_ADMIN"));

		return new CustomFilterInvocationSecurityMetadataSource(requestMap);
	}

	/**
	 * 按角色进行授权决策
	 * @return
	 */
	@Bean
	public RoleVoter roleVoter() {
		RoleVoter voter = new RoleVoter();
		voter.setRolePrefix("");
		return voter;
	}
	
	/**
	 * 用于管理全局已登录的会话
	 * @return
	 */
	@Bean
    public SessionRegistry sessionRegistry(){
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
}
