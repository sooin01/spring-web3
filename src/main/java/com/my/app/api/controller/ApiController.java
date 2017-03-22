package com.my.app.api.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

	private static final Logger log = LogManager.getLogger();

	@ApiOperation(value = "/index")
	@GetMapping(value = "/index")
	public Map<String, Object> index() {
		log.info("Home controller!");
		log.info("Home controller!");

		Map<String, Object> map = new LinkedHashMap<>();
		map.put("id", "test");
		map.put("name", "test");
		map.put("age", 20);
		map.put("age", 20);
		map.put("age", 20);
		map.put("age", 20);

		return map;
	}

}
