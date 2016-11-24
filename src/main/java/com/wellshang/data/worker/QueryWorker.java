package com.wellshang.data.worker;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.wellshang.data.entity.Stock;
import com.wellshang.data.service.IMessageService;
import com.wellshang.data.service.IStockService;

@Component("queryWorker")
@Scope("prototype")
public class QueryWorker implements Callable<Stock> {

    private static final String baseSinaUrl = "http://hq.sinajs.cn/list=";
    private static final Logger LOG = LoggerFactory.getLogger(QueryWorker.class);

    private String stockCode;
    private String workderType;
    private boolean hasResult;

    @Autowired
    @Qualifier("msgService")
    private IMessageService msgService;

    @Autowired
    @Qualifier("stockService")
    private IStockService stockService;

    public JmsTemplate jmsTemplate;

    public QueryWorker(String stockCode, String workderType, JmsTemplate jmsTemplate) {
        this.stockCode = stockCode;
        this.workderType = workderType;
        this.jmsTemplate = jmsTemplate;
    }

    public String getWorkderType() {
        return workderType;
    }

    public void setHasResult(boolean hasResult) {
        this.hasResult = hasResult;
    }

    private Stock handleSinaData() throws Exception {
        Stock stock = new Stock(this.stockCode);
        String url;
        if (stockCode.startsWith("60")) {
            url = baseSinaUrl + "sh" + stockCode;
        } else if (stockCode.startsWith("00")) {
            url = baseSinaUrl + "sz" + stockCode;
        } else if (stockCode.startsWith("300")) {
            url = baseSinaUrl + "sz" + stockCode;
        } else {
            LOG.error("Worker[{}]: Invalid stockCode!", stockCode);
            stock.setStockcode("-1");
            stock.setStockname("N/a");
            return stock;
        }

        HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory();
        httpFactory.setConnectTimeout(1000);
        httpFactory.setReadTimeout(1000);
        RestTemplate restTemplate = new RestTemplate(httpFactory);
        String response = restTemplate.getForObject(url, String.class);
        String[] results = response.split("\"");
        if (results.length != 3) {
            LOG.error("Worker[{}]: Invalid stockCode!", stockCode);
            stock.setStockcode("-1");
            stock.setStockname("N/a");
            return stock;
        }
        if (results[1].isEmpty()) {
            LOG.warn("Worker[{}]: Nullify stockCode, response:{}", stockCode, response);
            stock.setStockname("Nullified");
            return stock;
        }

        String[] fileds = results[1].split(",");
        stock.setStockname(fileds[0]);
        stock.setOpeningPrice(Float.parseFloat(fileds[1]));
        stock.setClosingPrice(Float.parseFloat(fileds[2]));
        stock.setVolume(Integer.parseInt(fileds[8]));
        stock.setTurnover(Float.parseFloat(fileds[9]));

        LOG.info("Worker[{}]: stockname:{}.", stockCode, stock.getStockname());
        return stock;
    }

    @Override
    public Stock call() throws Exception {
        Stock stock = handleSinaData();
        if (stock != null) {
            try {
                LOG.info("Send [{}] to {}.", stock.toString(), workderType);
                jmsTemplate.convertAndSend(workderType, stock);
            } catch (JmsException e) {
                LOG.error("Failed.", e);
            }
            hasResult = false;
            while (!hasResult) {
                Thread.sleep(1);
            }
        } else {
        }
        return stock;
    }
}
