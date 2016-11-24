package com.wellshang.data.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.wellshang.data.config.TaskExecutorConfig;
import com.wellshang.data.service.IThreadPoolService;

@Service("threadPoolService")
public class ThreadPoolServiceImpl implements IThreadPoolService, Runnable {

	private Object lock;
	private static final Logger LOG = LoggerFactory
			.getLogger(ThreadPoolServiceImpl.class);

	@Autowired
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private TaskExecutorConfig configs;

	@Override
	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	@Override
	public void run() {
		try {
			while (true) {
				monitorThreadPool();
				Thread.sleep(configs.getMonitoringPeriod() * 1000);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	private void monitorThreadPool() {
		LOG.debug("ThreadPool: {} of {} taskExecutor(s) are(is) running...",
				taskExecutor.getActiveCount(), taskExecutor.getCorePoolSize());

		if (0 == taskExecutor.getActiveCount()) {
			synchronized (lock) {
				lock.notify();
			}
		}
	}

	@Override
	public void register(Object obj) {
		this.lock = obj;
	}

}
