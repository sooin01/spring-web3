package com.my.app.api.user.domain;

import java.util.List;

public class UserWrapper {

	private User user;

	private List<User> users;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
