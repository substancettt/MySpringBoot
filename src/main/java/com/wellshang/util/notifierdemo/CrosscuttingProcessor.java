package com.wellshang.util.notifierdemo;

import org.slf4j.Logger;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;

public class CrosscuttingProcessor {
    @Autowired
    RegexpMethodPointcutAdvisor advisor;
    
    @Autowired
    InterceptorAdvice advice;
    
    
    
    public void setAdvisor(RegexpMethodPointcutAdvisor advisor) {
        this.advisor = advisor;
    }



    public void setAdvice(InterceptorAdvice advice) {
        this.advice = advice;
    }



    public void init(String pattern, Logger logger) {
        advisor.setPattern(pattern);
        advice.init(logger);
        advisor.setAdvice(advice);
    }
}
