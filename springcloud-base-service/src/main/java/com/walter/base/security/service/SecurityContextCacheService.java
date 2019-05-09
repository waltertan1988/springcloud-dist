package com.walter.base.security.service;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityContextCacheService {

	/**
	 * 保存SecurityContext到缓存
	 * @param authentication
	 * @return
	 */
	public SecurityContext put(SecurityContext securityContext);

	/**
	 * 根据username从缓存获取SecurityContext
	 * @param authentication
	 * @return
	 */
	public SecurityContext get(String username);

	/**
	 * 根据username从缓存删除SecurityContext
	 * @param authentication
	 * @return
	 */
	public void evict(String username);
}
