package com.walter.admin;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.walter.base.entity.JpaSysUser;
import org.walter.base.openapi.OpenApi;
import org.walter.base.security.authenticate.UserRoleChangedEvent;
import org.walter.base.security.authorize.ResourceRoleChangedEvent;

import com.walter.service.vo.ResourceVo;
import com.walter.user.api.feign.UserFeignApi;

@Controller
public class UserController extends BaseAdminController {
	
	@Autowired
	private UserFeignApi userFeignApi;
	
	@Autowired
	private ApplicationContext applicationContext;

	@GetMapping("/getUser")
	@OpenApi
	public JpaSysUser getUser(@RequestParam String username) {
		JpaSysUser user = userFeignApi.getUser(username);
		
//		Set<String> roleCodes = new HashSet<String>();
//		roleCodes.add("ROLE_ADMIN");
//		roleCodes.add("ROLE_LOGIN_USER");
//		this.updateUserRole("asnpgit", roleCodes);
		
		log.info("");
		
		return user;
	}
	
	/**
	 * 更新资源
	 * @param vo
	 * @return
	 */
	@PostMapping("/updateResource")
	@ResponseBody
	public boolean updateResource(ResourceVo vo){
		
		// 更新securityMetadataSource中的资源权限
		ResourceRoleChangedEvent event = new ResourceRoleChangedEvent(this, vo.getUrlPattern(), vo.getHttpMethod(), vo.getRoles());
		applicationContext.publishEvent(event);
		
		//TODO 更新数据库中的资源权限
		
		return true;
	}
	
	/**
	 * 更新指定用户所拥有的角色
	 * @param username 用户名
	 * @param roleCodes 更新后的角色编码全集
	 * @return
	 */
	@PostMapping("/updateUserRole/{username}")
	@ResponseBody
	public boolean updateUserRole(@PathVariable("username") String username, Set<String> roleCodes) {
		
		// 更新用户在UserDetails中的权限
		UserRoleChangedEvent event = new UserRoleChangedEvent(this, username, roleCodes);
		applicationContext.publishEvent(event);
		
		//TODO 更新数据库中的用户权限
		
		return true;
	}
}
