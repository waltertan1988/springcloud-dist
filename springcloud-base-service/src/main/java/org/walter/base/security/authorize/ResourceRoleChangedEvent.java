package org.walter.base.security.authorize;

import java.util.Set;

import org.springframework.context.ApplicationEvent;

public class ResourceRoleChangedEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;
	
	private String urlPattern;
	
	private String httpMethod;
	
	private Set<String> roleCodes;

	public ResourceRoleChangedEvent(Object source, String urlPattern, String httpMethod, Set<String> roleCodes) {
		super(source);
		this.urlPattern = urlPattern;
		this.httpMethod = httpMethod;
		this.roleCodes = roleCodes;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public Set<String> getRoleCodes() {
		return roleCodes;
	}
}
