package com.wellshang.data.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.JmsResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.wellshang.data.dao.IMongoDAO;
import com.wellshang.data.entity.Stock;
import com.wellshang.data.service.IStockService;

@Service("stockService")
@Scope("prototype")
public class StockServiceImpl implements IStockService {

    private static final Logger LOG = LoggerFactory.getLogger(StockServiceImpl.class);

    private IMongoDAO<Stock> dao;

    @Autowired
    @Qualifier("genericDao")
    public void setDao(IMongoDAO<Stock> dao) {
        this.dao = dao;
        this.dao.setEntityClass(Stock.class);
    }

    @Override
    public void test() {
        dao.test();
    }

    @Override
    @JmsListener(destination = "getStocksByName")
    public List<Stock> getStocksByName(String stockname) {
        return dao.load(Criteria.where("stockname").regex(stockname), -1, -1);
    }

    @Override
    @JmsListener(destination = "addStock")
    public JmsResponse<Message<Stock>> addStock(Stock stock) {
        LOG.info("stockService received message: {}", stock.toString());
        dao.insert(stock);
        Message<Stock> response = MessageBuilder
                .withPayload(stock)
                .build();
        return JmsResponse.forQueue(response, "status");
    }

    @Override
    @JmsListener(destination = "findStock")
    @Cacheable(value = "stockcache")
    public Stock findStock(String stockcode, String stockname) {
        LOG.info("findStock - stockcode: {}, stockname:{}.", stockcode, stockname);
        Stock stock = dao.find("stockcode", stockcode);
        if (stock == null) {
            stock = dao.find("stockname", stockname);
        }
        return stock;
    }

    @Override
    @JmsListener(destination = "deleteStock")
    public void deleteStock(String stockcode, String stockname) {
        dao.delete(new Stock(stockcode, stockname));
    }

    @Override
    @JmsListener(destination = "addOrUpdateStock")
    public void addOrUpdateStock(Stock stock) {
        Query query = new Query(Criteria.where("stockcode").is(stock.getStockcode()));
        Update update = new Update();
        Stock oldStock = dao.find("stockcode", stock.getStockcode());
        if (oldStock == null) {
            update.setOnInsert("_id", new ObjectId());
        } else {
            update.setOnInsert("_id", stock.getId());
        }
        update.setOnInsert("stockcode", stock.getStockcode());
        update.setOnInsert("stockname", stock.getStockname());
        update.setOnInsert("openingPrice", stock.getOpeningPrice());
        update.setOnInsert("closingPrice", stock.getClosingPrice());
        update.setOnInsert("volume", stock.getVolume());
        update.setOnInsert("turnover", stock.getTurnover());
        dao.upsert(query, update);
        LOG.info("addOrUpdateStock - Stock: " + stock.toString() + ".");
    }

}
