package com.wellshang.data.service;

import java.util.List;

import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;

import com.wellshang.data.entity.Stock;

public interface IStockService {
	public abstract void test();
	public abstract List<Stock> getStocksByName(String stockname);
	public abstract JmsResponse<Message<Stock>> addStock(Stock stock);
	public abstract Stock findStock(String stockcode, String stockname);
	public abstract void deleteStock(String stockcode, String stockname);
	public abstract void addOrUpdateStock(Stock stock);
}