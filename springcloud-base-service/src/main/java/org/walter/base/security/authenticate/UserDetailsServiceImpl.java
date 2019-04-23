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
import org.springframework.stereotype.Service;
import org.walter.base.entity.JpaSysUser;
import org.walter.base.entity.JpaSysUserRole;
import org.walter.base.repository.SysUserRepository;
import org.walter.base.repository.SysUserRoleRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private SysUserRepository sysUserRepository;
	@Autowired
	private SysUserRoleRepository sysUserRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		JpaSysUser sysUser = sysUserRepository.findByUsername(username);
		if(sysUser == null){
			throw new UsernameNotFoundException(String.format("用户名%s不存在", username));
		}
		
		Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();
		for(JpaSysUserRole jpaSysUserRole : sysUserRoleRepository.findByUsername(username)) {
			authoritySet.add(new SimpleGrantedAuthority(jpaSysUserRole.getRoleCode()));
		}
		UserDetails userDetails = new User(username, sysUser.getPassword(), authoritySet);
		
		return userDetails;
	}
}
