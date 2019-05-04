package com.walter.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walter.auth.entity.JpaAclRole;

public interface AclRoleRepository extends JpaRepository<JpaAclRole, Long>{

	public List<JpaAclRole> findByParentRoleCodeNotNull();
}
