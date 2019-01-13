package com.walter.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.walter.base.entity.JpaSysUser;

@FeignClient(name="user", path="/api/user")
public interface UserApi {

	@GetMapping("/{username}")
	public JpaSysUser getUser(@PathVariable("username") String username);
}
