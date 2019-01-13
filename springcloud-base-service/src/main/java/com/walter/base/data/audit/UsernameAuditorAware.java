package com.walter.base.data.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class UsernameAuditorAware implements AuditorAware<String> {
	
	public static final String DEFAULT_USER_NAME = "*ADMIN";
	
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	      return Optional.of(DEFAULT_USER_NAME);
	    }

	    String username = ((User) authentication.getPrincipal()).getUsername();
	    return Optional.ofNullable(username);
	}
}
