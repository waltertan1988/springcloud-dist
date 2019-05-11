package com.walter.base.security.authorize.messaging.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walter.base.entity.JpaAclRoleResource;

import lombok.Getter;
import lombok.Setter;

public class RefreshRoleResourceMessage {
	
	@Getter @Setter private OperationType operationType;
	
	@Getter @Setter private String resourceCode;
	
	@Getter @Setter private JpaAclRoleResource.ResourceTypeEnum resourceType;
	
	@Getter @Setter private String roleCode;
	
	public RefreshRoleResourceMessage() {}
	
	public RefreshRoleResourceMessage(OperationType operationType, String resourceCode,
			JpaAclRoleResource.ResourceTypeEnum resourceType, String roleCode) {
		super();
		this.operationType = operationType;
		this.resourceCode = resourceCode;
		this.resourceType = resourceType;
		this.roleCode = roleCode;
	}

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	public enum OperationType {
		DELETE,
		INSERT
	}
}
