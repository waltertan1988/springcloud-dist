package org.walter.base.security.authorize;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.walter.base.entity.JpaSysAction;
import org.walter.base.entity.JpaSysMenu;
import org.walter.base.entity.JpaSysRoleResource;
import org.walter.base.entity.enumeration.SysResourceTypeEnum;
import org.walter.base.repository.SysActionRepository;
import org.walter.base.repository.SysMenuRepository;
import org.walter.base.repository.SysRoleResourceRepository;

public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private Map<String, String> URL_ROLE_MAPPING = new HashMap<>();
	
	private SecurityMetadataSource securityMetadataSource;
	
	private SysMenuRepository sysMenuRepository;
	
	private SysActionRepository sysActionRepository;
	
	private SysRoleResourceRepository sysRoleResourceRepository;
	
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	public CustomFilterInvocationSecurityMetadataSource(SecurityMetadataSource securityMetadataSource, SysMenuRepository sysMenuRepository, SysActionRepository sysActionRepository, SysRoleResourceRepository sysRoleResourceRepository) {
		super();
		this.securityMetadataSource = securityMetadataSource;
		this.sysMenuRepository = sysMenuRepository;
		this.sysActionRepository = sysActionRepository;
		this.sysRoleResourceRepository = sysRoleResourceRepository;
		setupSecurityMetadataSource();
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		FilterInvocation fi = (FilterInvocation) object;
		String url = fi.getRequestUrl();
		
        for(Map.Entry<String,String> entry : URL_ROLE_MAPPING.entrySet()){
            if(antPathMatcher.match(entry.getKey(),url)){
                return new HashSet<ConfigAttribute>(SecurityConfig.createList(entry.getValue()));
            }
        }

        // 返回代码定义的默认配置
        return securityMetadataSource.getAttributes(object);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		List<ConfigAttribute> configAttributeList = SecurityConfig.createList(URL_ROLE_MAPPING.values().toArray(new String[]{}));
		
		if(!configAttributeList.isEmpty()) {
			return new HashSet<ConfigAttribute>(configAttributeList);
		}else {
			return securityMetadataSource.getAllConfigAttributes();
		}
	}
	
	protected void setupSecurityMetadataSource() {
		Map<String, String> resourceUriMap = new HashMap<>();
		for(JpaSysMenu jpaSysMenu : sysMenuRepository.findByIsGroup(false)) {
			resourceUriMap.put(jpaSysMenu.getMenuCode(), jpaSysMenu.getUri());
		}
		for(JpaSysAction jpaSysAction : sysActionRepository.findByIsGroup(false)) {
			resourceUriMap.put(jpaSysAction.getActionCode(), jpaSysAction.getUri());
		}
		for(JpaSysRoleResource jpaSysRoleResource : sysRoleResourceRepository.findAll()) {
			String resourceUri;
			if(SysResourceTypeEnum.MENU.equals(jpaSysRoleResource.getResourceType())) {
				resourceUri = resourceUriMap.get(jpaSysRoleResource.getResourceCode());
				URL_ROLE_MAPPING.put(resourceUri, jpaSysRoleResource.getRoleCode());
			}else if(SysResourceTypeEnum.ACTION.equals(jpaSysRoleResource.getResourceType())) {
				resourceUri = resourceUriMap.get(jpaSysRoleResource.getResourceCode());
				URL_ROLE_MAPPING.put(resourceUri, jpaSysRoleResource.getRoleCode());
			}
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}
