package com.walter.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walter.base.entity.JpaAclUserRole;

public interface AclUserRoleRepository extends JpaRepository<JpaAclUserRole, Long>{

	public List<JpaAclUserRole> findByUsername(String username);
}
