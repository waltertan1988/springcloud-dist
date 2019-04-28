package org.walter.base.security.authenticate;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.walter.base.entity.JpaSysUser;
import org.walter.base.entity.JpaSysUserRole;
import org.walter.base.repository.SysUserRepository;
import org.walter.base.repository.SysUserRoleRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private SysUserRepository sysUserRepository;
	@Autowired
	private SysUserRoleRepository sysUserRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
		
		JpaSysUser sysUser = sysUserRepository.findByUsernameOrMobile(usernameOrMobile);
		if(sysUser == null){
			throw new UsernameNotFoundException(String.format("用户名或手机号%s不存在", usernameOrMobile));
		}
		
		Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();
		for(JpaSysUserRole jpaSysUserRole : sysUserRoleRepository.findByUsername(sysUser.getUsername())) {
			authoritySet.add(new SimpleGrantedAuthority(jpaSysUserRole.getRoleCode()));
		}
//		AuthorityUtils
		UserDetails userDetails = new User(sysUser.getUsername(), sysUser.getPassword(), sysUser.isEnabled(), !sysUser.isExpired(), !sysUser.isPasswordExpired(), !sysUser.isLocked(), authoritySet);
		
		return userDetails;
	}
}
