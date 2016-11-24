package com.wellshang.data.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolTest implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolTest.class);

    private static final int COREPOOLSIZE = 4;
    private static final int MAXINUMPOOLSIZE = 8;
    private static final long KEEPALIVETIME = 5;
    private static final TimeUnit UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORKQUEUE = new ArrayBlockingQueue<Runnable>(10);

    @SuppressWarnings("unused")
    private final int ID;
    
    public ThreadPoolTest(int id) {
        ID = id;
    }
    
    public void run() {
        try {
//            LOG.debug("Thread [" + ID + "] start.");
            Thread.sleep(1000);
//            LOG.debug("Thread[" + ID + "] end.");
        } catch (InterruptedException e) {
            LOG.error("error", e);
        }
    }

    public static void main(String[] args) {
        LOG.info("Thread pool start.");
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(COREPOOLSIZE, MAXINUMPOOLSIZE, KEEPALIVETIME, UNIT,
                WORKQUEUE);
        for (int i = 1; i < 2000; i++) {
            while (WORKQUEUE.size() >= 10) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOG.error("error", e);
                }
            }
            threadPool.submit(new Thread(new ThreadPoolTest(i)));
        }
        threadPool.shutdown();
        LOG.info("Thread pool shutdown.");
        while(!threadPool.isTerminated()) {};
        LOG.info("Thread pool end");
    }
}