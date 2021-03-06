package org.walter.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.walter.base.entity.JpaSysRoleResource;

public interface SysRoleResourceRepository extends JpaRepository<JpaSysRoleResource, Long>{

	public JpaSysRoleResource getByResourceCodeAndResourceTypeEnumAndRoleCode(String resourceCode, JpaSysRoleResource.ResourceTypeEnum resourceTypeEnum, String roleCode);
}
