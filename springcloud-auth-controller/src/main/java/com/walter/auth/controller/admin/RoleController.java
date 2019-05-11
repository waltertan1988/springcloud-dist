package com.walter.auth.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.walter.base.controller.admin.BaseAdminController;

import com.walter.base.entity.JpaAclRole;
import com.walter.base.repository.AclRoleRepository;

@RestController
public class RoleController extends BaseAdminController{
	
	@Autowired
	private AclRoleRepository aclRoleRepository;
	
	@GetMapping("/role/list")
	public List<JpaAclRole> roleList() {
		return aclRoleRepository.findAll();
	}
}
