package com.walter.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walter.base.entity.JpaAclRole;

public interface AclRoleRepository extends JpaRepository<JpaAclRole, Long>{

	public List<JpaAclRole> findByParentRoleCodeNotNull();
}
