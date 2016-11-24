package com.wellshang.data.service;

import org.springframework.web.context.request.async.DeferredResult;

import com.wellshang.data.worker.QueryWorker;

public interface IMessageService {
	public abstract void heartBeat();
	public abstract void addStock(String id, DeferredResult<String> result);
}
