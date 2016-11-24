package com.wellshang.data.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.web.context.request.async.DeferredResult;

import com.wellshang.data.entity.Stock;
import com.wellshang.data.service.IMessageService;
import com.wellshang.data.service.IThreadPoolService;
import com.wellshang.data.worker.QueryWorker;

@Service("msgService")
public class MessageServiceImpl implements IMessageService {

    protected static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    public JmsTemplate jmsTemplate;

    @Autowired
    IThreadPoolService threadPoolService;

    private Map<String, QueryWorker> runningWorkers = new ConcurrentHashMap<String, QueryWorker>();
    
    @Scheduled(fixedDelay = 3000000)
    @Override
    public void heartBeat() {
        String msg = "Message Service heart beat: " + LocalDateTime.now().toString();
        LOG.info("{}", msg);
        jmsTemplate.convertAndSend("heart-beat", msg);
    }

    @Override
    public void addStock(String id, DeferredResult<String> result) {
        QueryWorker worker = new QueryWorker(id, "addStock", jmsTemplate);
        runningWorkers.put(id, worker);
        ListenableFutureTask<Stock> task = new ListenableFutureTask<Stock>(worker);
        task.addCallback(new ListenableFutureCallback<Stock>() {
            @Override
            public void onSuccess(Stock stock) {
                if (stock == null) {
                    LOG.warn("N/a");
                    result.setResult("N/a");
                } else {
                    LOG.debug(stock.toString());
                    result.setResult(stock.toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Failed.", t);
                result.setResult("Failed." + t.toString());
            }
        });
        threadPoolService.getTaskExecutor().submit(task);
    }
    
    @JmsListener(destination = "status")
    public void checkResult(Stock stock) {
        LOG.debug(stock.toString());
        QueryWorker work = runningWorkers.get(stock.getStockcode());
        work.setHasResult(true);
    }
}
