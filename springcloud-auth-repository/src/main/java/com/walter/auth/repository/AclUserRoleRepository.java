package com.walter.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walter.auth.entity.JpaAclUserRole;

public interface AclUserRoleRepository extends JpaRepository<JpaAclUserRole, Long>{

	public List<JpaAclUserRole> findByUsername(String username);
}
