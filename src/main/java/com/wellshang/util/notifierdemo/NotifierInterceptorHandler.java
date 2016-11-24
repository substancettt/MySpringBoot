package com.wellshang.util.notifierdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotifierInterceptorHandler implements IInterceptorHandler {

    private Logger logger = null;
    
    @Override
    public void init(Logger logger) {
        if (null == logger) {
            this.logger = LoggerFactory.getLogger(NotifierInterceptorHandler.class);
        } else {
            this.logger = logger;
        }
    }

    @Override
    public void before() {
        logger.info("about to call the method");
    }

    @Override
    public void after(Object obj) {
        logger.info("after call the method");
        boolean result = (boolean) obj;
        logger.info("result is " + result);
        if (result) {
            logger.info("success");
        } else {
            logger.info("error, about send notification...");
        }
    }

}
