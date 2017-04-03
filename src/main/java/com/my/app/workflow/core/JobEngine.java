package com.my.app.workflow.core;

import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.my.app.workflow.domain.Job;

@Component
public class JobEngine implements Runnable {

	private static final Logger LOG = LogManager.getLogger();

	private LinkedBlockingQueue<FutureTask<Job>> queue = new LinkedBlockingQueue<>();

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@PostConstruct
	public void init() {
		new Thread(this).start();
	}

	public FutureTask<Job> add(Job job) {
		FutureTask<Job> task = null;
		
		try {
			task = new FutureTask<>(job);
			queue.put(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return task;
	}

	@Override
	public void run() {
		while (true) {
			try {
				LOG.info("JobEngine waiting.. {}/{}/{}/{}",
						taskExecutor.getActiveCount(),
						taskExecutor.getCorePoolSize(),
						taskExecutor.getPoolSize(),
						taskExecutor.getMaxPoolSize());
				FutureTask<Job> task = queue.take();
				taskExecutor.execute(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
