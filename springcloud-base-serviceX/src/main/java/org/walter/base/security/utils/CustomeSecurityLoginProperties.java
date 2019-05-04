package org.walter.base.security.utils;

import lombok.Getter;
import lombok.Setter;

public class CustomeSecurityLoginProperties {

	/**
	 * RememberMe token的有效时间
	 */
	@Getter	@Setter	private Integer rememberMeTokenValiditySeconds = 600;
}
