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

	private LinkedBlockingQueue<Job> queue = new LinkedBlockingQueue<>();

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@PostConstruct
	public void init() {
		new Thread(this).start();
	}

	public void add(Job job) {
		try {
			queue.put(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				LOG.info("JobEngine waiting.. {}/{}/{}/{}", taskExecutor.getCorePoolSize(), taskExecutor.getActiveCount(),
						taskExecutor.getMaxPoolSize(), taskExecutor.getPoolSize());
				Job job = queue.take();
				LOG.info("JobEngine start: {}", job.getName());
				taskExecutor.execute(new FutureTask<>(job));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
