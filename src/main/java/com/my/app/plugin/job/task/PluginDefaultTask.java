package com.my.app.plugin.job.task;

import com.my.app.api.common.annotation.Task;

@Task
public class PluginDefaultTask {

	public String getTaskName() {
		return "Default Task.";
	}

}
