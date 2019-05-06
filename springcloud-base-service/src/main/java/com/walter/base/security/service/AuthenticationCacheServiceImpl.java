package com.walter.base.security.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.walter.base.cache.RedisCacheConfig;

@Service
public class AuthenticationCacheServiceImpl implements AuthenticationCacheService {

	@Override
	@CachePut(cacheNames=RedisCacheConfig.USER_AUTHENTICATION_CACHE, key="#authentication.name")
	public Authentication put(Authentication authentication) {
		return authentication;
	}

	@Override
	@Cacheable(cacheNames=RedisCacheConfig.USER_AUTHENTICATION_CACHE, key="#username")
	public Authentication get(String username) {
		return null;
	}
}
