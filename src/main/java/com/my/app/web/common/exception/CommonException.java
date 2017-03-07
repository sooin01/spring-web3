package com.my.app.web.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonException {
	
	private static final Logger log = LoggerFactory.getLogger(CommonException.class);

	@ExceptionHandler
	public void exception(Exception exception) {
		log.error("Exception handler => {}", exception.getClass().getName(), exception);
	}
	
}
