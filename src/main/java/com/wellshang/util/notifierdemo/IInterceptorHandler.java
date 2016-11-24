package com.wellshang.util.notifierdemo;

import org.slf4j.Logger;

public interface IInterceptorHandler {
    public void init(Logger logger);
    public void before();
    public void after(Object obj);
}
