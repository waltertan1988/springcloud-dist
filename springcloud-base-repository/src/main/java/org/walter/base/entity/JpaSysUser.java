package org.walter.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_USER")
public class JpaSysUser extends AbstractAuditable {

	private static final long serialVersionUID = 1L;

	@Column(name = "USERNAME", length = 255, unique = true, nullable = false)
	private String username;

	@Column(length = 255, name = "USER_REAL_NAME")
	private String userRealName;

	@Column(length = 255, nullable = false, name = "PASSWORD")
	private String password;

	@Column(length = 1, nullable = false, name = "GENDER")
	private String gender;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
