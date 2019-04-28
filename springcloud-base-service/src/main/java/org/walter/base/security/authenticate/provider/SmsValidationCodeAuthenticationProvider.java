package org.walter.base.security.authenticate.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.walter.base.security.authenticate.token.SmsValidationCodeAuthenticationToken;

@Component
public class SmsValidationCodeAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		SmsValidationCodeAuthenticationToken authenticationToken = (SmsValidationCodeAuthenticationToken) authentication;
		String mobile = (String)authenticationToken.getPrincipal();
		UserDetails user = userDetailsService.loadUserByUsername(mobile);
		
		if(user == null) {
			throw new InternalAuthenticationServiceException(String.format("无法获取手机号为%s的用户信息", mobile));
		}
		
		return createSuccessAuthentication(user, authentication, user);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsValidationCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	protected Authentication createSuccessAuthentication(Object principal,
			Authentication authentication, UserDetails user) {
		SmsValidationCodeAuthenticationToken result = new SmsValidationCodeAuthenticationToken(
				principal, user.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}
}
