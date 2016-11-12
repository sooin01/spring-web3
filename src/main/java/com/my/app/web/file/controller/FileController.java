package com.my.app.web.file.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileController {

	@GetMapping(value = "upload")
	public String upload() {
		return "file/upload";
	}
	
	@PostMapping(value = "upload")
	public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile file) {
		log.debug("Content-Type: {}", file.getContentType());
		log.debug("Name: {}", file.getName());
		log.debug("Original filename: {}", file.getOriginalFilename());
		log.debug("Size: {}", file.getSize());
		return ResponseEntity.ok().build();
	}
	
}
