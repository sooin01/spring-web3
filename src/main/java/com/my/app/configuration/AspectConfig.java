package com.my.app.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AspectConfig {
	
	private static final Logger log = LogManager.getLogger();
	
	@Around("execution(* com.my.app..*Service.*(..))")
	public void serviceAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("AOP serviceAround before!");
		
		joinPoint.proceed();
		
		log.info("AOP serviceAround after!");
	}
	
}
