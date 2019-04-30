package org.walter.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.walter.base.entity.JpaSysRole;

public interface SysRoleRepository extends JpaRepository<JpaSysRole, Long>{

	public List<JpaSysRole> findByParentRoleCodeNotNull();
}
