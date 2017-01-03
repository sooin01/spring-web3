package com.my.app.web.file.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResultDto {

	@JsonProperty(value = "file_name")
	private String fileName;
	
	@JsonProperty(value = "file_size")
	private Long fileSize;
	
}
