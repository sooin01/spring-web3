package com.my.app.web.home.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class HomeReq {

	@NotBlank
	private String id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String content;
	
	@NotBlank
	@Pattern(regexp = "Y|N")
	private String deleteYn;
	
}
