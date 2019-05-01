package org.walter.base.security.service;

public interface RoleService {

	String REFRESH_ROLE_MESSAGE_BODY = "REFRESH_ROLE_MESSAGE_BODY";
	
	public void sendRefreshRoleMessage();
}
