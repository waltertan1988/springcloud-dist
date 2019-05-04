package com.walter.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ACL_ROLE")
public class JpaAclRole extends AbstractAuditable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="ROLE_CODE", length=255, unique=true, nullable=false)
	private String roleCode;
	
	@Column(name="ROLE_NAME", length=255)
	private String roleName;
	
	@Column(name="PARENT_ROLE_CODE", length=255)
	private String parentRoleCode;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getParentRoleCode() {
		return parentRoleCode;
	}

	public void setParentRoleCode(String parentRoleCode) {
		this.parentRoleCode = parentRoleCode;
	}
}
