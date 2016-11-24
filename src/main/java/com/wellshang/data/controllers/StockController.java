package com.wellshang.data.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.wellshang.data.config.RemoteServerConfig;
import com.wellshang.data.entity.Stock;
import com.wellshang.data.service.IMessageService;
import com.wellshang.data.service.IStockService;
import com.wellshang.data.service.IThreadPoolService;
import com.wellshang.data.worker.QueryWorker;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private static final Logger LOG = LoggerFactory.getLogger(StockController.class);

    @Autowired
    RemoteServerConfig server;

    @Autowired
    @Qualifier("msgService")
    IMessageService msgService;

    @Autowired
    @Qualifier("stockService")
    IStockService stockService;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<String> listener1(@PathVariable("id") String id) {
        final DeferredResult<String> result = new DeferredResult<String>(30000l, "Timeout.");
        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                LOG.error("Timeout for " + id + ".");
            }
        });

        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                LOG.debug("Done for " + id + ".");
            }
        });
        msgService.addStock(id, result);


        
        return result;
    }
}
