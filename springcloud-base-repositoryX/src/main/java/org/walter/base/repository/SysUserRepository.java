package org.walter.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.walter.base.entity.JpaSysUser;

public interface SysUserRepository extends JpaRepository<JpaSysUser, Long>{

	@Query("FROM JpaSysUser o WHERE o.username=:usernameOrMobile OR o.mobile=:usernameOrMobile")
	public JpaSysUser findByUsernameOrMobile(String usernameOrMobile);
	
	public List<JpaSysUser> findByUserRealName(String userRealName);
}
