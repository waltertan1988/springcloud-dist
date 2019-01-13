package com.walter.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_MENU")
public class JpaSysMenu extends AbstractAuditable {

	private static final long serialVersionUID = 1L;

	@Column(name = "MENU_CODE", length = 255, nullable = false, unique = true)
	private String menuCode;

	@Column(name = "MENU_NAME", length = 255, nullable = false)
	private String menuName;

	@Column(name = "IS_GROUP", nullable = false)
	private boolean isGroup;

	@Column(name = "PARENT_CODE", length = 255, nullable = false)
	private String parentCode;

	@Column(name = "URI", length = 255, nullable = true)
	private String uri;

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
