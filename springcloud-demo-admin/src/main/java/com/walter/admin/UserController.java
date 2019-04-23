package com.walter.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.walter.base.entity.JpaSysUser;
import org.walter.base.openapi.OpenApi;

import com.walter.user.api.feign.UserFeignApi;

@Controller
public class UserController extends BaseAdminController {
	
	@Autowired
	private UserFeignApi userFeignApi;
	
	@GetMapping("/getUser")
	@OpenApi
	public JpaSysUser getUser(@RequestParam String username) {
		JpaSysUser user = userFeignApi.getUser(username);
		log.info("");
		
		return user;
	}
}
