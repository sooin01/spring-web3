package com.my.web.home.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.web.home.service.HomeService;

import io.swagger.annotations.ApiOperation;

@RestController
public class HomeController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private HomeService homeService;
	
	@ApiOperation(value = "index")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Map<String, Object> index() {
		logger.info("Home controller!");
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("id", "test");
		map.put("name", "test");
		map.put("age", 20);
		
		homeService.index();
		
		return map;
	}
	
}
