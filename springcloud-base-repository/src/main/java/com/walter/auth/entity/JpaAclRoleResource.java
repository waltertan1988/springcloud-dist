package com.walter.auth.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.walter.auth.entity.enumeration.converter.JpaAclRoleResourceResourceTypeEnumConverter;

@Entity
@Table(name = "ACL_ROLE_RESOURCE", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "RESOURCE_CODE", "RESOURCE_TYPE", "ROLE_CODE" }) })
public class JpaAclRoleResource extends AbstractAuditable {

	private static final long serialVersionUID = 1L;

	@Column(name = "RESOURCE_CODE", length = 255, nullable = false)
	private String resourceCode;

	@Column(name = "RESOURCE_TYPE", length = 255, nullable = false)
	@Convert(converter=JpaAclRoleResourceResourceTypeEnumConverter.class)
	private ResourceTypeEnum resourceTypeEnum;

	@Column(name = "ROLE_CODE", length = 255, nullable = false)
	private String roleCode;
	
	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public ResourceTypeEnum getResourceTypeEnum() {
		return resourceTypeEnum;
	}

	public void setResourceTypeEnum(ResourceTypeEnum resourceType) {
		this.resourceTypeEnum = resourceType;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public enum ResourceTypeEnum {
		MENU("MENU"),
		ACTION("ACTION");
		
		private String code;
		
		ResourceTypeEnum(String code){
			this.code = code;
		}
		
		public String getCode() {
			return code;
		}

		@Override
		public String toString() {
			return code;
		}
	}
}
