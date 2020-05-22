package com.touch.aspect;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.annotation.Controller;

import java.lang.reflect.Method;

@Aspect(pointcut = "within(org.simpleframework.core.annotation.Component)")
@Order(value = 0)
@Slf4j
public class ServiceTimeCalculatorAspect extends DefaultAspect {
    private long timestameCache;
    public void before(Class<?> targetClass, Method method, Object args) throws Throwable{
        log.info("start time recording, class = [{}]",targetClass.getName());
        timestameCache = System.currentTimeMillis();
    }

    public Object afterReturning(Class<?> targetClass,Method method,Object args,Object returnVaue) throws Throwable{
        long endTime = System.currentTimeMillis();
        long costTime = endTime - timestameCache;
        log.info("time cost : {}, class = [{}]",costTime, targetClass.getName());
        return returnVaue;
    }
}
