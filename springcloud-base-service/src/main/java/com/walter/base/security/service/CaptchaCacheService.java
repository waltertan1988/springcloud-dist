package com.walter.base.security.service;

public interface CaptchaCacheService {

	String put(String key, String captchaValue);
	
	String get(String key);

	void evict(String key);
}
