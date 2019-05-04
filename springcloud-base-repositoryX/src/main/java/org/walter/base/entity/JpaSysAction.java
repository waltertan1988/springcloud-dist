package org.walter.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_ACTION")
public class JpaSysAction extends AbstractAuditable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ACTION_CODE", length = 255, nullable = false, unique = true)
	private String actionCode;

	@Column(name = "ACTION_NAME", length = 255, nullable = false)
	private String actionName;

	@Column(name = "IS_GROUP", nullable = false)
	private boolean isGroup;

	@Column(name = "PARENT_CODE", length = 255, nullable = false)
	private String parentCode;

	@Column(name = "URI", length = 255, nullable = true)
	private String uri;
	
	@Column(name = "ORDER", nullable = false)
	private int order = 0;

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
