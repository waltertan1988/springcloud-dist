package com.walter.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walter.auth.entity.JpaAclRoleResource;

public interface AclRoleResourceRepository extends JpaRepository<JpaAclRoleResource, Long>{

	public JpaAclRoleResource getByResourceCodeAndResourceTypeEnumAndRoleCode(String resourceCode, JpaAclRoleResource.ResourceTypeEnum resourceTypeEnum, String roleCode);
}
