package com.wellshang.util.notifierdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Loader {
    private static final Logger logger = LoggerFactory.getLogger(Loader.class);
    
    private String pattern;
    private CrosscuttingProcessor notifier;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public CrosscuttingProcessor getNotifier() {
        return notifier;
    }

    public void setNotifier(CrosscuttingProcessor notifier) {
        this.notifier = notifier;
    }

    public boolean loadData(String data) {
        if ("OK".equalsIgnoreCase(data)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-mail.xml");
        Loader processor = (Loader) ctx.getBean("loader");
        processor.getNotifier().init(processor.getPattern(), logger);
        logger.info("loadData(\"BAD\")");
        processor.loadData("BAD");
        logger.info("loadData(\"OK\")");
        processor.loadData("OK");
    }
}
