package com.my.app.api.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.UUID;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.my.TestContext;
import com.my.app.api.user.domain.User;
import com.my.app.api.user.domain.UserWrapper;

public class UserControllerTest extends TestContext {

	@Test
	public void testGetUsers() throws Exception {
		mockMvc.perform(get("/users")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void testAddUsers() throws Exception {
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setUserName("Test");
		UserWrapper userWrapper = new UserWrapper();
		userWrapper.setUser(user);

		mockMvc.perform(post("/users")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(bean2JsonString(userWrapper)));
	}

}
