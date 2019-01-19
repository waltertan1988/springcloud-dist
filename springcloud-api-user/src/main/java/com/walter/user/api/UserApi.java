package com.walter.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.walter.base.entity.JpaSysUser;

public interface UserApi {
	@GetMapping("/{username}")
	public JpaSysUser getUser(@PathVariable("username") String username);
}
