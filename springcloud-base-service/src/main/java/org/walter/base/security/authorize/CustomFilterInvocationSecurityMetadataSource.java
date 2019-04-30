package org.walter.base.security.authorize;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.walter.base.entity.JpaSysAction;
import org.walter.base.entity.JpaSysMenu;
import org.walter.base.entity.JpaSysRoleResource;
import org.walter.base.repository.SysActionRepository;
import org.walter.base.repository.SysMenuRepository;
import org.walter.base.repository.SysRoleResourceRepository;
import org.walter.base.security.authorize.messaging.binding.RefreshRoleResourceSink;
import org.walter.base.security.authorize.messaging.message.RefreshRoleResourceMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
   * 自定义SecurityMetadataSource，可用于为资源动态授权
 * @author ThinkPad
 */
@EnableBinding({RefreshRoleResourceSink.class})
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private Map<String, String> URL_ROLE_MAPPING = new HashMap<>();
	
	private SecurityMetadataSource securityMetadataSource;
	
	@Autowired
	private SysMenuRepository sysMenuRepository;
	@Autowired
	private SysActionRepository sysActionRepository;
	@Autowired
	private SysRoleResourceRepository sysRoleResourceRepository;
	
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
		Map<String, String> resourceUriMap = new HashMap<>();
		for(JpaSysMenu jpaSysMenu : sysMenuRepository.findByIsGroup(false)) {
			resourceUriMap.put(jpaSysMenu.getMenuCode(), jpaSysMenu.getUri());
		}
		for(JpaSysAction jpaSysAction : sysActionRepository.findByIsGroup(false)) {
			resourceUriMap.put(jpaSysAction.getActionCode(), jpaSysAction.getUri());
		}
		for(JpaSysRoleResource jpaSysRoleResource : sysRoleResourceRepository.findAll()) {
			String resourceUri;
			if(JpaSysRoleResource.ResourceTypeEnum.MENU.getCode().equals(jpaSysRoleResource.getResourceTypeEnum().getCode())) {
				resourceUri = resourceUriMap.get(jpaSysRoleResource.getResourceCode());
				URL_ROLE_MAPPING.put(resourceUri, jpaSysRoleResource.getRoleCode());
			}else if(JpaSysRoleResource.ResourceTypeEnum.ACTION.getCode().equals(jpaSysRoleResource.getResourceTypeEnum().getCode())) {
				resourceUri = resourceUriMap.get(jpaSysRoleResource.getResourceCode());
				URL_ROLE_MAPPING.put(resourceUri, jpaSysRoleResource.getRoleCode());
			}
		}
	}
	
	/**
	 * 根据接收的消息类型，刷新资源角色配置
	 * @param jsonStrMsg
	 * @throws IOException
	 */
	@StreamListener(RefreshRoleResourceSink.INPUT)
	@Transactional
	public void consumerMessage(String jsonStrMsg) throws IOException {
		RefreshRoleResourceMessage refreshRoleResourceMessage = new ObjectMapper().readValue(jsonStrMsg, RefreshRoleResourceMessage.class);
		String resourceCode = refreshRoleResourceMessage.getResourceCode();
		JpaSysRoleResource.ResourceTypeEnum resourceType = refreshRoleResourceMessage.getResourceType();
		String roleCode = refreshRoleResourceMessage.getRoleCode();
		
		if(RefreshRoleResourceMessage.OperationType.DELETE.equals(refreshRoleResourceMessage.getOperationType())) {
			JpaSysRoleResource jpaSysRoleResource = sysRoleResourceRepository.getByResourceCodeAndResourceTypeEnumAndRoleCode(resourceCode, resourceType, roleCode);
			if(null != jpaSysRoleResource) {
				sysRoleResourceRepository.delete(jpaSysRoleResource);
			}
		}else if(RefreshRoleResourceMessage.OperationType.INSERT.equals(refreshRoleResourceMessage.getOperationType())) {
			JpaSysRoleResource jpaSysRoleResource = new JpaSysRoleResource();
			jpaSysRoleResource.setResourceCode(resourceCode);
			jpaSysRoleResource.setResourceTypeEnum(resourceType);
			jpaSysRoleResource.setRoleCode(roleCode);
			sysRoleResourceRepository.save(jpaSysRoleResource);
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
