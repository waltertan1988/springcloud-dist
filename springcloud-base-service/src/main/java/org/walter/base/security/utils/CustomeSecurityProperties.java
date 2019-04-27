package org.walter.base.security.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "custom.security")
public class CustomeSecurityProperties {

	@Getter @Setter private Boolean httpBasicEnable = false;
	
	@Getter @Setter private CustomeSecurityLoginProperties login = new CustomeSecurityLoginProperties();
}
