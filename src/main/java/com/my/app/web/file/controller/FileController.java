package com.my.app.web.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.my.app.web.file.dto.FileDto;
import com.my.app.web.file.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;

	@GetMapping(value = "/upload")
	public String upload() {
		return "file/upload";
	}
	
	@PostMapping(value = "/upload")
	public ResponseEntity<Void> upload(FileDto fileDto) {
		fileService.file(fileDto);
		return ResponseEntity.ok().build();
	}
	
}
