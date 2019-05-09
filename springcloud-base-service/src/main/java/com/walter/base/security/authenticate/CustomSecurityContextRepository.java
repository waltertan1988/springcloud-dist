package com.walter.base.security.authenticate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import com.walter.base.security.service.SecurityContextCacheService;
import com.walter.base.util.JwtUtil;

import io.jsonwebtoken.Claims;

@Component
public class CustomSecurityContextRepository implements SecurityContextRepository {

	@Value("${custom.security.login.jwt.header}")
	private String JWT_REQUEST_HEADER;
	
	@Value("${custom.security.login.jwt.secret}")
	private String JWT_SECRET;
	
	@Autowired
	private SecurityContextCacheService securityContextCacheService;
	
	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		String username = getUsername(requestResponseHolder.getRequest());
		if(StringUtils.isNotEmpty(username)) {
			SecurityContext context = securityContextCacheService.get(username);
			if(null != context) {
				return context;
			}
		}
		
		return generateNewContext();
	}

	protected SecurityContext generateNewContext() {
		return SecurityContextHolder.createEmptyContext();
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		if(context.getAuthentication()!=null) {
			securityContextCacheService.put(context);
		}
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		String username = getUsername(request);
		if(StringUtils.isNotEmpty(username)) {
			SecurityContext context = securityContextCacheService.get(username);
			return null != context;
		}
		return false;
	}
	
	protected String getUsername(HttpServletRequest request) {
		String jwt = request.getHeader(JWT_REQUEST_HEADER);
		if(StringUtils.isEmpty(jwt)) {
			jwt = request.getParameter(JWT_REQUEST_HEADER);
		}
		
		Claims jwtClaims = JwtUtil.getClaims(jwt, JWT_SECRET);
		if(null != jwtClaims) {
			return jwtClaims.getSubject();
		}
		return null;
	}
}
