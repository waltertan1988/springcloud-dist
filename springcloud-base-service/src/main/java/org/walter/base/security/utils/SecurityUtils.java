package org.walter.base.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtils {
	
	public static User getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if(principal instanceof User) {
			return (User)principal;
		}else {
			return new User(principal.toString(), "", authentication.getAuthorities());
		}
	}
	
	public static User getUser() {
		return getUser(SecurityContextHolder.getContext().getAuthentication());
	}
}
