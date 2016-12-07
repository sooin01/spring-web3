package com.my.app.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.app.web.common.model.Logging;

@Aspect
public class AspectConfig {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private Logging logging;
	
	@Around("execution(* com.my.app..*Service.*(..))")
	public void serviceAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("AOP serviceAround before!");
		
		log.debug("Before: {}, {}", logging.getId(), logging.getName());
		
		joinPoint.proceed();
		
		log.debug("After: {}, {}", logging.getId(), logging.getName());
		
		log.info("AOP serviceAround after!");
	}
	
}
