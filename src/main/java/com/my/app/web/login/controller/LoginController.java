package com.my.app.web.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	@PostMapping(value = "/login")
	public String login() {
		return "redirect:/home";
	}
	
	
}
