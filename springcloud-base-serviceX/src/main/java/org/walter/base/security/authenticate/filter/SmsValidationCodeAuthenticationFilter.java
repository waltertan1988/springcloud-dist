package org.walter.base.security.authenticate.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.walter.base.security.WebSecurityConfig;
import org.walter.base.security.authenticate.token.SmsValidationCodeAuthenticationToken;

/**
   * 短信验证码认证过滤器
 * @author ThinkPad
 *
 */
public class SmsValidationCodeAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {

	public static final String CUSTOMER_SECURITY_FORM_MOBILE_KEY = "mobile";

	private String mobileParameter = CUSTOMER_SECURITY_FORM_MOBILE_KEY;
	private boolean postOnly = true;

	public SmsValidationCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher(WebSecurityConfig.SMS_VALIDATION_CODE_LOGIN_URL, "POST"));
	}

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		String mobile = obtainMobile(request);

		if (mobile == null) {
			mobile = "";
		}

		mobile = mobile.trim();

		SmsValidationCodeAuthenticationToken authRequest = new SmsValidationCodeAuthenticationToken(mobile);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(mobileParameter);
	}

	protected void setDetails(HttpServletRequest request,
			SmsValidationCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	public void setUsernameParameter(String mobileParameter) {
		Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
		this.mobileParameter = mobileParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getMobileParameter() {
		return mobileParameter;
	}
}
