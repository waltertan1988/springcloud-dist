package org.walter.base.security.authenticate.filter;

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
import org.walter.base.security.WebSecurityConfig;
import org.walter.base.security.authenticate.exception.ValidateCodeException;

/**
   * 短信验证码校验过滤器
 * @author ThinkPad
 *
 */
@Component
public class SmsValidationCodeFilter extends OncePerRequestFilter {

	public final static String SESSION_KEY_SMS_VALIDATION_CODE = SmsValidationCodeFilter.class.getName() + "." + WebSecurityConfig.CUSTOM_SECURITY_SMA_VALIDATION_CODE_KEY;
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String loginUrl = request.getContextPath() + WebSecurityConfig.SMS_VALIDATION_CODE_LOGIN_URL;
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
		String requestSmsValidationCode = request.getParameter(WebSecurityConfig.CUSTOM_SECURITY_SMA_VALIDATION_CODE_KEY);
		String sessionSmsValidationCode = (String)request.getSession().getAttribute(SESSION_KEY_SMS_VALIDATION_CODE);
		
		if(StringUtils.isEmpty(requestSmsValidationCode) || StringUtils.isEmpty(sessionSmsValidationCode)) {
			throw new ValidateCodeException("验证码不存在");
		}
		
		if(!requestSmsValidationCode.equalsIgnoreCase(sessionSmsValidationCode)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		
		request.getSession().removeAttribute(SESSION_KEY_SMS_VALIDATION_CODE);
	}
}
