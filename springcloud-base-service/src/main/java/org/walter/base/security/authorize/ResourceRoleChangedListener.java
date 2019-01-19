package org.walter.base.security.authorize;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class ResourceRoleChangedListener implements ApplicationListener<ResourceRoleChangedEvent>{
	
	@Autowired
	private CustomFilterInvocationSecurityMetadataSource securityMetadataSource;
	
	@Override
	public void onApplicationEvent(ResourceRoleChangedEvent event) {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = securityMetadataSource.getRequestMap();
		RequestMatcher requestMatcher = new CustomRegexRequestMatcher(event.getUrlPattern(), event.getHttpMethod());
		Collection<ConfigAttribute> attributes = requestMap.get(requestMatcher);
		if(null != attributes){
			attributes.clear();
			attributes.addAll(SecurityConfig.createList(event.getRoleCodes().toArray(new String[]{})));
		}
	}
	
}
