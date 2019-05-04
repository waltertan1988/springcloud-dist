package org.walter.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.walter.base.entity.JpaSysUserRole;

public interface SysUserRoleRepository extends JpaRepository<JpaSysUserRole, Long>{

	public List<JpaSysUserRole> findByUsername(String username);
}
