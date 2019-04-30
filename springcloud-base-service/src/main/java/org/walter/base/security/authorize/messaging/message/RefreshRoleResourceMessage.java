package org.walter.base.security.authorize.messaging.message;

import org.walter.base.entity.JpaSysRoleResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

public class RefreshRoleResourceMessage {
	
	@Getter @Setter private OperationType operationType;
	
	@Getter @Setter private String resourceCode;
	
	@Getter @Setter private JpaSysRoleResource.ResourceTypeEnum resourceType;
	
	@Getter @Setter private String roleCode;
	
	public RefreshRoleResourceMessage() {}
	
	public RefreshRoleResourceMessage(OperationType operationType, String resourceCode,
			JpaSysRoleResource.ResourceTypeEnum resourceType, String roleCode) {
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
