package org.walter.base.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.walter.base.security.authorize.messaging.binding.RefreshRoleProcessor;
import org.walter.base.security.service.RoleHierarchyService;
import org.walter.base.security.service.RoleService;

@EnableBinding({RefreshRoleProcessor.class})
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleHierarchy roleHierarchy;
	
	@Autowired
	@Output(RefreshRoleProcessor.OUTPUT)
	private MessageChannel refreshRoleProcessorChannel;
	
	@Autowired
	private RoleHierarchyService roleHierarchyService;
	
	/**
	   * 发送刷新消息
	 */
	@Override
	public void sendRefreshRoleMessage() {
		String msg = RoleService.REFRESH_ROLE_MESSAGE_BODY;
		refreshRoleProcessorChannel.send(MessageBuilder.withPayload(msg).build());
	}
	
	/**
	   * 接收刷新消息
	 */
	@StreamListener(RefreshRoleProcessor.INPUT)
	public void listenRefreshRoleMessage(String msg) {
		if(RoleService.REFRESH_ROLE_MESSAGE_BODY.equals(msg)) {
			String roleHierarchyString = roleHierarchyService.getRoleHierarchyString();
			((RoleHierarchyImpl)roleHierarchy).setHierarchy(roleHierarchyString);
		}
	}

}
