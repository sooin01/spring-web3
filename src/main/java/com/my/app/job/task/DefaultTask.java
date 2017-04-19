package com.my.app.job.task;

import com.my.app.api.common.annotation.Task;

@Task
public class DefaultTask {

	public String getTaskName() {
		return "Default Task.";
	}

}
