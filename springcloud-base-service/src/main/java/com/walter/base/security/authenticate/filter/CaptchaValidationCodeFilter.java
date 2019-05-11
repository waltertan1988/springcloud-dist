package com.walter.base.security.authenticate.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.walter.base.security.WebSecurityConfig;
import com.walter.base.security.authenticate.exception.ValidateCodeException;
import com.walter.base.security.service.CaptchaCacheService;

/**
   * 图片验证码校验过滤器
 * @author ThinkPad
 */
@Component
public class CaptchaValidationCodeFilter extends OncePerRequestFilter {

	@Autowired
	private CaptchaCacheService captchaService;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String loginUrl = request.getContextPath() + WebSecurityConfig.FORM_LOGIN_PROCESSING_URL;
		if(loginUrl.equals(request.getRequestURI()) && HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
			try {
				validate(request);
			}catch(ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		
		filterChain.doFilter(request, response);
	}

	protected void validate(HttpServletRequest request) {
		String requestCaptcha = request.getParameter(WebSecurityConfig.CUSTOM_SECURITY_FORM_CAPTCHA_KEY);
		String requestCaptchaCacheKey = request.getHeader(WebSecurityConfig.CUSTOM_SECURITY_FORM_CAPTCHA_CACHE_KEY);
		String cachedCaptchaValue = captchaService.get(requestCaptchaCacheKey);
		
		if(StringUtils.isEmpty(requestCaptcha) 
				|| StringUtils.isEmpty(requestCaptchaCacheKey) 
				|| StringUtils.isEmpty(cachedCaptchaValue)) {
			throw new ValidateCodeException("验证码不存在");
		}
		
		if(!requestCaptcha.equalsIgnoreCase(cachedCaptchaValue)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		
		captchaService.evict(requestCaptchaCacheKey);
	}
}
