package com.example.entity;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingInterceptor {


    private Logger logger;

    @AroundConstruct
    public void beforeConst(InvocationContext ic) throws Exception   {
        if (ic.getConstructor() != null) {
            logger = Logger.getLogger(ic.getConstructor().getName());
            logger.log(Level.INFO,  "InterceptorEJB: " +
                    ic.getConstructor().getName() + " Constructor entering..");
            try {
                ic.proceed();
            } finally {
                logger.log(Level.INFO, "InterceptorEJB: " + ic.getConstructor().getName() + " Constructor exiting..");
            }

        }
        ic.proceed();
    }


    @AroundInvoke
    private Object logMethod(InvocationContext ic) throws Exception {
        logger = Logger.getLogger(ic.getTarget().getClass().getName());
        logger.log(Level.INFO, "InterceptorEJB: " + ic.getTarget().getClass().getName() + "." + ic.getMethod().getName() + " entering..");
        Object ob =  ic.proceed();
        logger.log(Level.INFO, "InterceptorEJB: " + ic.getTarget().getClass().getName() + "." + ic.getMethod().getName() + " exiting..");
        return ob;
    }
}
