package com.walter.base.security.data.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.walter.base.security.authenticate.CustomUser;

@Component
public class UsernameAuditorAware implements AuditorAware<String> {
	
	public static final String ANONYMOUS_USER_NAME = "*ANONYMOUS";
	
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	      return Optional.of(ANONYMOUS_USER_NAME);
	    }

	    String username = ((CustomUser) authentication.getPrincipal()).getUsername();
	    return Optional.ofNullable(username);
	}
}
