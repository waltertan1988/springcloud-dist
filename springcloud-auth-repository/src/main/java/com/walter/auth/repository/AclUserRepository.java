package com.walter.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.walter.auth.entity.JpaAclUser;

public interface AclUserRepository extends JpaRepository<JpaAclUser, Long>{

	@Query("FROM JpaSysUser o WHERE o.username=:usernameOrMobile OR o.mobile=:usernameOrMobile")
	public JpaAclUser findByUsernameOrMobile(String usernameOrMobile);
	
	public List<JpaAclUser> findByUserRealName(String userRealName);
}
