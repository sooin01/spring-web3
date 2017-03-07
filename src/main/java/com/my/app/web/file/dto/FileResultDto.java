package com.my.app.web.file.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileResultDto {

	@JsonProperty(value = "file_name")
	private String fileName;

	@JsonProperty(value = "file_size")
	private Long fileSize;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

}
