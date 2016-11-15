package com.my.app.web.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CommonException {

	@ExceptionHandler
	public void exception(Exception exception) {
		log.error("Exception handler => {}", exception.getClass().getName(), exception);
	}
	
}
