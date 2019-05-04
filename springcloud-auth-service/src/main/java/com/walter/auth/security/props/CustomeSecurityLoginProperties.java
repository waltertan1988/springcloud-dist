package com.walter.auth.security.props;

import lombok.Getter;
import lombok.Setter;

public class CustomeSecurityLoginProperties {

	/**
	 * RememberMe token的有效时间
	 */
	@Getter	@Setter	private Integer rememberMeTokenValiditySeconds = 600;
}
