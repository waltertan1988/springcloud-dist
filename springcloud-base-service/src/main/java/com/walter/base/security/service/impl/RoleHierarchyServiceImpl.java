package com.walter.base.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walter.base.entity.JpaAclRole;
import com.walter.base.repository.AclRoleRepository;
import com.walter.base.security.service.RoleHierarchyService;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

	@Autowired
	private AclRoleRepository aclRoleRepository;
	
	@Override
	public String getRoleHierarchyString() {
		StringBuffer roleHierarchy = new StringBuffer();
		for(JpaAclRole jpaSysRole : aclRoleRepository.findByParentRoleCodeNotNull()) {
			roleHierarchy.append(jpaSysRole.getRoleCode())
			.append(" > ")
			.append(jpaSysRole.getParentRoleCode())
			.append("\n");
		}
		return roleHierarchy.toString();
	}
}
