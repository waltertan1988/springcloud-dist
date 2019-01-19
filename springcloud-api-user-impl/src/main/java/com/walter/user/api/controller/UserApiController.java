package com.walter.user.api.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.walter.base.entity.JpaSysUser;
import org.walter.base.repository.SysUserRepository;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.walter.res.api.feign.ResApiFeign;
import com.walter.user.api.UserApi;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserApiController extends BaseUserApiController implements UserApi{
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private ResApiFeign resApiFeign;
	
	@Autowired
	private SysUserRepository sysUserRepository;

	@HystrixCommand(fallbackMethod="getUserFallback")
	@Override
	public JpaSysUser getUser(@PathVariable("username")String username) {
		JpaSysUser user = sysUserRepository.findByUsername(username);
		resApiFeign.listMenu(username);
		
		return user;
	}
	
	public JpaSysUser getUserFallback(String username, Throwable throwable){
		log.error("Hystrix fallback method called...", throwable);
		
		JpaSysUser user = new JpaSysUser();
		user.setCreatedBy(String.valueOf(this.port));
		user.setCreatedDate(LocalDateTime.now());
		user.setLastModifiedDate(LocalDateTime.now());
		
		return user;
	}
}
