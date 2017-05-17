package com.my.app.api.user.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.my.app.api.user.domain.User;
import com.my.app.api.user.domain.UserWrapper;
import com.my.app.api.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

	private static final Logger LOG2 = LogManager.getLogger();

	@Autowired
	private UserService userService;

	@ApiOperation(value = "/users")
	@GetMapping("/users")
	@ResponseStatus(HttpStatus.OK)
	public UserWrapper getUsers() {
		List<User> users = userService.getUsers();
		for (User user : users) {
			user.add(linkTo(methodOn(UserController.class).getUser(user.getUserId())).withSelfRel());
		}
		UserWrapper userWrapper = new UserWrapper();
		userWrapper.setUsers(users);
		return userWrapper;
	}

	@ApiOperation(value = "/users/{userId}")
	@GetMapping("/users/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public UserWrapper getUser(@PathVariable String userId) {
		LOG2.info("userId: {}", userId);
		User user = new User();
		user.setUserId(userId);
		user.setUserName("Tester");

		UserWrapper userWrapper = new UserWrapper();
		userWrapper.setUser(user);
		return userWrapper;
	}

	@PostMapping(value = "/users")
	@ResponseStatus(HttpStatus.CREATED)
	public void addUser(@RequestBody UserWrapper userWrapper) {

	}

}
