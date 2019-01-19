package org.walter.base.security.authenticate;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	private static final long serialVersionUID = 1L;
	
	private Set<GrantedAuthority> authorities;

	public CustomUser(String username, String password, Set<GrantedAuthority> authorities) {
		super(username, password, new HashSet<GrantedAuthority>());
		this.authorities = authorities;
	}

	@Override
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
