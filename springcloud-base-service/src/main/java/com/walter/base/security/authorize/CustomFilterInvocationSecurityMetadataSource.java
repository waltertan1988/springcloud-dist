package com.walter.base.security.authorize;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.walter.base.entity.JpaAclAction;
import com.walter.base.entity.JpaAclMenu;
import com.walter.base.entity.JpaAclRoleResource;
import com.walter.base.repository.AclActionRepository;
import com.walter.base.repository.AclMenuRepository;
import com.walter.base.repository.AclRoleResourceRepository;
import com.walter.base.security.authorize.messaging.binding.RefreshRoleResourceProcessor;

/**
   * 自定义SecurityMetadataSource，可用于为资源动态授权
 * @author ThinkPad
 */
@EnableBinding({RefreshRoleResourceProcessor.class})
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	public static final String REFRESH_ROLE_RESOURCE_MESSAGE_BODY = "REFRESH_ROLE_RESOURCE_MESSAGE_BODY";
	
	private Map<String, String> URL_ROLE_MAPPING = new HashMap<>();
	
	private SecurityMetadataSource securityMetadataSource;
	
	@Autowired
	private AclMenuRepository aclMenuRepository;
	@Autowired
	private AclActionRepository aclActionRepository;
	@Autowired
	private AclRoleResourceRepository aclRoleResourceRepository;
	
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	public CustomFilterInvocationSecurityMetadataSource() {
		super();
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
	
	public void setupSecurityMetadataSource() {
		URL_ROLE_MAPPING.clear();
		Map<String, String> resourceUriMap = new HashMap<>();
		for(JpaAclMenu jpaAclMenu : aclMenuRepository.findByIsGroup(false)) {
			resourceUriMap.put(jpaAclMenu.getMenuCode(), jpaAclMenu.getUri());
		}
		for(JpaAclAction jpaAclAction : aclActionRepository.findByIsGroup(false)) {
			resourceUriMap.put(jpaAclAction.getActionCode(), jpaAclAction.getUri());
		}
		for(JpaAclRoleResource jpaAclRoleResource : aclRoleResourceRepository.findAll()) {
			String resourceUri;
			if(JpaAclRoleResource.ResourceTypeEnum.MENU.getCode().equals(jpaAclRoleResource.getResourceTypeEnum().getCode())) {
				resourceUri = resourceUriMap.get(jpaAclRoleResource.getResourceCode());
				URL_ROLE_MAPPING.put(resourceUri, jpaAclRoleResource.getRoleCode());
			}else if(JpaAclRoleResource.ResourceTypeEnum.ACTION.getCode().equals(jpaAclRoleResource.getResourceTypeEnum().getCode())) {
				resourceUri = resourceUriMap.get(jpaAclRoleResource.getResourceCode());
				URL_ROLE_MAPPING.put(resourceUri, jpaAclRoleResource.getRoleCode());
			}
		}
	}
	
	/**
	 * 根据接收的消息类型，刷新资源角色配置
	 * @param msg
	 * @throws IOException
	 */
	@StreamListener(RefreshRoleResourceProcessor.INPUT)
	public void consumerMessage(String msg){
		if(REFRESH_ROLE_RESOURCE_MESSAGE_BODY.equals(msg)) {
			setupSecurityMetadataSource();
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	public void setSecurityMetadataSource(SecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
}
