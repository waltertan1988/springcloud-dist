package com.walter.base.security.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationCacheService {

	/**
	 * 保存Authentication到缓存
	 * @param authentication
	 * @return
	 */
	public Authentication put(Authentication authentication);

	/**
	 * 根据username从缓存获取Authentication
	 * @param authentication
	 * @return
	 */
	public Authentication get(String username);
}
