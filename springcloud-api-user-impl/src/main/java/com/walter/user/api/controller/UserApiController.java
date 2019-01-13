package com.walter.user.api.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.walter.base.entity.JpaSysUser;
import com.walter.base.repository.SysUserRepository;
import com.walter.res.api.ResApi;

@RestController
public class UserApiController extends BaseUserApiController {
	
	@Autowired
	private ResApi resApi;
	
	@Autowired
	private SysUserRepository sysUserRepository;

	@GetMapping("/{username}")
	@HystrixCommand(fallbackMethod="getUserFallback")
	public JpaSysUser getUser(@PathVariable("username") String username) {
		JpaSysUser user = sysUserRepository.findByUsername(username);
		System.out.println("yyyyyyyy");
		
		resApi.listMenu(username);
		
		return user;
	}
	
	public JpaSysUser getUserFallback(String username, Throwable throwable){
		
		JpaSysUser user = new JpaSysUser();
		System.out.println("xxxxxxxxxx");
		
		user.setCreatedDate(LocalDateTime.now());
		user.setLastModifiedDate(LocalDateTime.now());
		
		return user;
	}
}
