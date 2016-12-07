package com.my.app.web.common.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.app.web.common.model.Logging;

public class LoggingCheck {
	
	private static final Logger log = LogManager.getLogger();

	public void check(Logging logging) {
		log.debug("Check: {}, {}", logging.getId(), logging.getName());
	}
	
}
