package com.walter.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="SYS_USER_ROLE", uniqueConstraints={@UniqueConstraint(columnNames={"USERNAME", "ROLE_CODE"})})
public class JpaSysUserRole extends AbstractAuditable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="USERNAME", length=255, nullable=false)
	private String username;
	
	@Column(name="ROLE_CODE", length=255, nullable=false)
	private String roleCode;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}
