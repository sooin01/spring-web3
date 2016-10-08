package com.my.web.home.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.web.home.service.HomeService;

@Controller
public class HomeController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private HomeService homeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root() {
		return "redirect:/home";
	}
	
//	@ApiOperation(value = "index")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> index() {
		logger.info("Home controller!");
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("id", "test");
		map.put("name", "test");
		map.put("age", 20);
		
		homeService.index();
		
		return map;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home/home";
	}
	
}
