package org.walter.base.security.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserRoleChangedListener implements ApplicationListener<UserRoleChangedEvent>{
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Override
	public void onApplicationEvent(UserRoleChangedEvent event) {
		UserDetails loginUserDetails = this.findLoginUserDetailsByUsername(event.getUsername());
//		if(loginUserDetails != null) {
//			Collection<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
//			for(String code : event.getRoleCodes()) {
//				roles.add(new SimpleGrantedAuthority(code));
//			}
//			
//			loginUserDetails.getAuthorities().clear();
//			((Collection<GrantedAuthority>) loginUserDetails.getAuthorities()).addAll(roles);
//		}
		
		for(SessionInformation si : sessionRegistry.getAllSessions(loginUserDetails, false)) {
			si.expireNow();
		}
	}
	
	protected UserDetails findLoginUserDetailsByUsername(String username) {
		for(Object o : sessionRegistry.getAllPrincipals()) {
			if(o instanceof UserDetails) {
				UserDetails details = (UserDetails)o;
				if(username.equals(details.getUsername())) {
					return details;
				}
			}
		}
		
		return null;
	}
}
