package com.my.app.workflow.domain;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Job implements Callable<Job> {
	
	private static final Logger LOG = LogManager.getLogger();
	
	private String id;
	
	private String name;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Job call() throws Exception {
		LOG.info("{} is started.", name);
		Thread.sleep(3 * 1000);
		LOG.info("{} is end.", name);
		return this;
	}

}
