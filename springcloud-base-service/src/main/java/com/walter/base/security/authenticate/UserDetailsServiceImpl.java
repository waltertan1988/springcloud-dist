package com.walter.base.security.authenticate;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.walter.auth.entity.JpaAclUser;
import com.walter.auth.entity.JpaAclUserRole;
import com.walter.auth.repository.AclUserRepository;
import com.walter.auth.repository.AclUserRoleRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AclUserRepository aclUserRepository;
	@Autowired
	private AclUserRoleRepository aclUserRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
		
		JpaAclUser sysUser = aclUserRepository.findByUsernameOrMobile(usernameOrMobile);
		if(sysUser == null){
			throw new UsernameNotFoundException(String.format("用户名或手机号%s不存在", usernameOrMobile));
		}
		
		Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();
		for(JpaAclUserRole jpaSysUserRole : aclUserRoleRepository.findByUsername(sysUser.getUsername())) {
			authoritySet.add(new SimpleGrantedAuthority(jpaSysUserRole.getRoleCode()));
		}
		
//		AuthorityUtils
		CustomUser customUser = new CustomUser(sysUser.getUsername(), sysUser.getPassword(), sysUser.isEnabled(), !sysUser.isExpired(), !sysUser.isPasswordExpired(), !sysUser.isLocked(), authoritySet);
		customUser.setUserRealName(sysUser.getUserRealName());
		customUser.setGender(sysUser.getGender());
		customUser.setMobile(sysUser.getMobile());
		
		return customUser;
	}
}
