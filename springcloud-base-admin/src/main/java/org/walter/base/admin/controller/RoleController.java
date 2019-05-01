package org.walter.base.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.walter.base.openapi.OpenApi;
import org.walter.base.security.service.RoleService;

@Controller
public class RoleController extends BaseAdminController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/sysAdm/role")
	public String rolePage() {
		return super.getRelativePath("/sysAdm/role");
	}
	
	@GetMapping("/sysAdm/role/refresh")
	@OpenApi
	public Boolean roleRefresh() {
		
		roleService.sendRefreshRoleMessage();
		
		return true;
	}
}
