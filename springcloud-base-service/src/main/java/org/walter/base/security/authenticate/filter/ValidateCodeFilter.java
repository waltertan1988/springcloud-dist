package org.walter.base.security.authenticate.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.walter.base.security.WebSecurityConfig;
import org.walter.base.security.authenticate.ValidateCodeException;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

	public final static String SESSION_KEY_CAPTCHA = ValidateCodeFilter.class.getName() + ".signcode";
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String loginUrl = request.getContextPath() + WebSecurityConfig.LOGIN_URL;
		if(loginUrl.equals(request.getRequestURI()) && "post".equalsIgnoreCase(request.getMethod())) {
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
		String requestCaptcha = request.getParameter("captcha");
		String sessionCaptcha = (String)request.getSession().getAttribute(SESSION_KEY_CAPTCHA);
		
		if(StringUtils.isEmpty(requestCaptcha) || StringUtils.isEmpty(sessionCaptcha)) {
			throw new ValidateCodeException("验证码不存在");
		}
		
		if(!requestCaptcha.equalsIgnoreCase(sessionCaptcha)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		
		request.getSession().removeAttribute(SESSION_KEY_CAPTCHA);
	}
}
