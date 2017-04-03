package com.my.app.workflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.app.workflow.core.JobEngine;
import com.my.app.workflow.domain.Job;

@RestController
public class WorkflowController {
	
	@Autowired
	private JobEngine JobEngine;

	@RequestMapping(value = "/networks/service/instantiate", method = RequestMethod.POST)
	public void networkServiceInstantiate(@RequestBody Job job) {
		JobEngine.add(job);
	}
	
}
