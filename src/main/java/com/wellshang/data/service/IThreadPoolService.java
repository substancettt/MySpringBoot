package com.wellshang.data.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public interface IThreadPoolService extends Runnable {
	public abstract ThreadPoolTaskExecutor getTaskExecutor();
	public abstract void register(Object obj);
}