package com.walter.base.security.service.impl;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import com.walter.base.cache.RedisCacheConfig;
import com.walter.base.security.service.SecurityContextCacheService;

@Service
public class SecurityContextCacheServiceImpl implements SecurityContextCacheService {

	@Override
	@CachePut(cacheNames=RedisCacheConfig.USERNAME_SECURITY_CONTEXT_CACHE, key="#securityContext.authentication.name")
	public SecurityContext put(SecurityContext securityContext) {
		return securityContext;
	}

	@Override
	@Cacheable(cacheNames=RedisCacheConfig.USERNAME_SECURITY_CONTEXT_CACHE, key="#username")
	public SecurityContext get(String username) {
		return null;
	}
}
