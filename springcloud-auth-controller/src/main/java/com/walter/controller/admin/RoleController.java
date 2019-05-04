package com.walter.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walter.auth.entity.JpaAclRole;
import com.walter.auth.repository.AclRoleRepository;

@RestController
@RequestMapping("/admin")
public class RoleController {
	
	@Autowired
	private AclRoleRepository aclRoleRepository;
	
	@GetMapping("/role/list")
	public List<JpaAclRole> roleList() {
		return aclRoleRepository.findAll();
	}
}
