package com.walter.base.security.authenticate;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationEvent;

public class UserRoleChangedEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private Set<String> roleCodes = new HashSet<String>();

	public UserRoleChangedEvent(Object source, String username, Set<String> roleCodes) {
		super(source);
		this.username = username;
		this.roleCodes.addAll(roleCodes);
	}

	public String getUsername() {
		return username;
	}

	public Set<String> getRoleCodes() {
		return roleCodes;
	}

}
