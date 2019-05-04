package com.walter.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walter.base.entity.JpaAclRoleResource;

public interface AclRoleResourceRepository extends JpaRepository<JpaAclRoleResource, Long>{

	public JpaAclRoleResource getByResourceCodeAndResourceTypeEnumAndRoleCode(String resourceCode, JpaAclRoleResource.ResourceTypeEnum resourceTypeEnum, String roleCode);
}
