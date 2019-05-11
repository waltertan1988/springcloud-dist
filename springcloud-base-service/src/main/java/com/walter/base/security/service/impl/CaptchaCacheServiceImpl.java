package com.walter.base.security.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.walter.base.cache.RedisCacheConfig;
import com.walter.base.security.service.CaptchaCacheService;

@Service
public class CaptchaCacheServiceImpl implements CaptchaCacheService {

	@Override
	@CachePut(cacheNames=RedisCacheConfig.LOGIN_CAPTCHA_CACHE, key="#key")
	public String put(String key, String captchaValue) {
		return captchaValue;
	}

	@Override
	@Cacheable(cacheNames=RedisCacheConfig.LOGIN_CAPTCHA_CACHE, key="#key", unless="#result==null")
	public String get(String key) {
		return null;
	}
	
	@Override
	@CacheEvict(cacheNames=RedisCacheConfig.LOGIN_CAPTCHA_CACHE, key="#key")
	public void evict(String key) {
		
	}
}
