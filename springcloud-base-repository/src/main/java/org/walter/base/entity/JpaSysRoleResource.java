package org.walter.base.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.walter.base.entity.enumeration.SysResourceTypeEnum;
import org.walter.base.entity.enumeration.converter.SysResourceTypeEnumConverter;

@Entity
@Table(name = "SYS_ROLE_RESOURCE", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "RESOURCE_CODE", "RESOURCE_TYPE", "ROLE_CODE" }) })
public class JpaSysRoleResource extends AbstractAuditable {

	private static final long serialVersionUID = 1L;

	@Column(name = "RESOURCE_CODE", length = 255, nullable = false)
	private String resourceCode;

	@Column(name = "RESOURCE_TYPE", length = 255, nullable = false)
	@Convert(converter=SysResourceTypeEnumConverter.class)
	private SysResourceTypeEnum resourceType;

	@Column(name = "ROLE_CODE", length = 255, nullable = false)
	private String roleCode;
	
	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public SysResourceTypeEnum getResourceType() {
		return resourceType;
	}

	public void setResourceType(SysResourceTypeEnum resourceType) {
		this.resourceType = resourceType;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}
