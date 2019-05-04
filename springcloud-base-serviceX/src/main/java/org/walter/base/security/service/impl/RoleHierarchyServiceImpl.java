package org.walter.base.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.walter.base.entity.JpaSysRole;
import org.walter.base.repository.SysRoleRepository;
import org.walter.base.security.service.RoleHierarchyService;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

	@Autowired
	private SysRoleRepository sysRoleRepository;
	
	@Override
	public String getRoleHierarchyString() {
		StringBuffer roleHierarchy = new StringBuffer();
		for(JpaSysRole jpaSysRole : sysRoleRepository.findByParentRoleCodeNotNull()) {
			roleHierarchy.append(jpaSysRole.getRoleCode())
			.append(">")
			.append(jpaSysRole.getParentRoleCode())
			.append("\n");
		}
		return roleHierarchy.toString();
	}
}
