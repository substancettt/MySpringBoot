package com.wellshang.util.notifierdemo;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterceptorAdvice implements MethodInterceptor {
    private Logger logger = null;
    private IInterceptorHandler handler;

    public void setHandler(IInterceptorHandler handler) {
        handler.init(logger);
        this.handler = handler;
    }

    public void init(Logger logger) {
        if (null == logger) {
            this.logger = LoggerFactory.getLogger(InterceptorAdvice.class);
        } else {
            this.logger = logger;
        }
    }
    
    @Override
    public Object invoke(MethodInvocation method) throws Throwable {
        logger.info("call handler.before before calling the method");
        handler.before();
        Object obj = null;
        try {
            logger.info("call the method");
            obj = (boolean) method.proceed();
            return obj;
        }
        finally {
            logger.info("call handler.after after called the method");
            handler.after(obj);
        }
    }

}
