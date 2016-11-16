package com.my.app.web.file.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileDto {

	@NotBlank
	private String name;
	
	private MultipartFile file;
	
}
