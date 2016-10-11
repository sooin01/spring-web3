package com.my.web.home.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.web.home.dto.HomeReq;
import com.my.web.home.service.HomeService;

@Controller
public class HomeController {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private Validator validator;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root() {
		return "redirect:/home";
	}
	
//	@ApiOperation(value = "index")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> index() {
		log.info("Home controller!");
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("id", "test");
		map.put("name", "test");
		map.put("age", 20);
		
		homeService.index();
		
		return map;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(@Valid HomeReq homeReq, BindingResult bindingResult) {
		// @Valid 사용안할 시
//		validator.validate(homeReq, bindingResult);
		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			log.info("[{}] {}", fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return "home/home";
	}
	
}
