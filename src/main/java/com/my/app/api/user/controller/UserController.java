package com.my.app.api.user.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.my.app.api.user.domain.User;
import com.my.app.api.user.domain.UserWrapper;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

	private static final Logger LOG = LogManager.getLogger();

	@ApiOperation(value = "/users")
	@GetMapping("/users")
	public UserWrapper getUsers() {
		String userId = UUID.randomUUID().toString();
		User user = new User();
		user.setUserId(userId);
		user.setUserName("Tester");
		user.add(linkTo(methodOn(UserController.class).getUser(userId)).withSelfRel());

		UserWrapper userWrapper = new UserWrapper();
		userWrapper.setUsers(Arrays.asList(user));
		return userWrapper;
	}

	@ApiOperation(value = "/user/{userId}")
	@GetMapping("/user/{userId}")
	public UserWrapper getUser(@PathVariable String userId) {
		LOG.info("userId: {}", userId);
		User user = new User();
		user.setUserId(userId);
		user.setUserName("Tester");

		UserWrapper userWrapper = new UserWrapper();
		userWrapper.setUser(user);
		return userWrapper;
	}

}
