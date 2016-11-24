package com.wellshang.data.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "taskExecutor")
public class TaskExecutorConfig {

	private static final Logger LOG = LoggerFactory
			.getLogger(TaskExecutorConfig.class);

	private int corePoolSize;
	private int maxPoolSize;
	private int queueCapacity;
	private int keepAliveSeconds;
	private int monitoringPeriod;

	public void setCorePoolSize(String corePoolSize) {
		this.corePoolSize = Integer.parseInt(corePoolSize);
	}

	public void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = Integer.parseInt(maxPoolSize);
	}

	public void setQueueCapacity(String queueCapacity) {
		this.queueCapacity = Integer.parseInt(queueCapacity);
	}

	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	public void setMonitoringPeriod(int monitoringPeriod) {
		this.monitoringPeriod = monitoringPeriod;
	}

	public int getMonitoringPeriod() {
		return monitoringPeriod;
	}

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		LOG.info(
				"taskExecutor initialized corePoolSize:{}, maxPoolSize:{},  queueCapacity:{}.",
				corePoolSize, maxPoolSize, queueCapacity);

		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(corePoolSize);
		pool.setMaxPoolSize(maxPoolSize);
		pool.setQueueCapacity(queueCapacity);
		pool.setKeepAliveSeconds(keepAliveSeconds);
		pool.setWaitForTasksToCompleteOnShutdown(true);

		return pool;
	}
}
