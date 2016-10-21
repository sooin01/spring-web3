package com.my.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class WebAspect {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Around("execution(* com.my.web..*Service.*(..))")
	public void serviceAround(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("AOP serviceAround before!");
		
		joinPoint.proceed();
		
		logger.info("AOP serviceAround after!");
	}
	
}
