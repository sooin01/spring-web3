package com.my.app.web.home.controller;

import java.lang.reflect.Field;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.ImmutableList;
import com.my.app.web.home.dto.HomeReq;
import com.my.app.web.home.dto.ResultDto;
import com.my.app.web.home.service.HomeService;

@Controller
public class HomeController {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private Validator validator;
	
	@GetMapping(value = "/")
	public String index() {
		return "home/home";
	}
	
	@GetMapping(value = "/home")
	public String home(@Valid HomeReq homeReq, BindingResult bindingResult, Model model) {
		// @Valid 사용안할 시
//		validator.validate(homeReq, bindingResult);
		if (bindingResult.hasErrors()) {
			for (Field field : homeReq.getClass().getDeclaredFields()) {
				FieldError fieldError = bindingResult.getFieldError(field.getName());
				log.info("[{}] {}", fieldError.getField(), fieldError.getDefaultMessage());
			}
		}
		
		model.addAttribute("list", ImmutableList.of("aaa", "bbb"));
		model.addAttribute("name", "World");
		
		return "home/home";
	}
	
	@PostMapping(value = "/home/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResultDto> homeUpdate(@RequestParam("id") String id) {
		log.debug("id: {}", id);
		
		ResultDto resultDto = new ResultDto();
		resultDto.setCode("0");
		resultDto.setMessage("Success");
		return ResponseEntity.ok(resultDto);
	}
	
}
