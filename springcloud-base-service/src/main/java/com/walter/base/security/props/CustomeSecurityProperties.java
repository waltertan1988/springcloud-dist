package com.walter.base.security.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "custom.security")
public class CustomeSecurityProperties {

	/**
	 * 是否启用Security
	 */
	@Getter @Setter private Boolean httpBasicEnable = false;
	
	@Getter @Setter private CustomeSecurityLoginProperties login = new CustomeSecurityLoginProperties();
}
