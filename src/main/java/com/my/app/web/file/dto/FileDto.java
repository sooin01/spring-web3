package com.my.app.web.file.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileDto {

	private String name;
	
	private MultipartFile file;
	
}
