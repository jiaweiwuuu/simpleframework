package com.touch.aspect;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.annotation.Controller;

import java.lang.reflect.Method;
@Aspect(pointcut = "within(org.simpleframework.core.annotation.Component)")
@Order(value = 10)
@Slf4j
public class ServiceInfoRecordAspect extends DefaultAspect {
    @Override
    public void before(Class<?> targetClass, Method method, Object args) throws Throwable {
        log.info("start service info aspect, class = [{}]",targetClass.getName());
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object args, Object returnVaue) throws Throwable {
        log.info("return service info aspect, class = [{}]",targetClass.getName());
        return returnVaue;
    }

    @Override
    public void afterThrowing(Class<?> targetClass, Method method, Object args, Throwable e) throws Throwable {
        log.info("failed to execute method,exception is [{}]",e.getMessage());
    }
}
