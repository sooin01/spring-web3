package com.my.app.api.user.domain;

import com.my.app.api.common.domain.BaseDomain;

public class User extends BaseDomain {

	private String userId;

	private String userName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
