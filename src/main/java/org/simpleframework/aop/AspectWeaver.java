package org.simpleframework.aop;

import demo.annotation.AnnotationDemo;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.annotation.ProxyCreater;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.core.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;

public class AspectWeaver {
    private BeanContainer beanContainer;
    public AspectWeaver(){
        beanContainer = BeanContainer.getInstance();
    }
    public void doAop(){
        // get all @Aspect
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        // concat AspectInfoList
        if(ValidationUtil.isEmpty(aspectSet)){
            return;
        }

        List<AspectInfo> aspectInfoList =  packAspectInfoList(aspectSet);

        // iterate classes in the container

        Set<Class<?>> classSet = beanContainer.getClasses();
        for(Class<?> targetClass:classSet){
            if(targetClass.isAnnotationPresent(Aspect.class)){
                continue;
            }
            // roughly filter
            List<AspectInfo> rouchMatchedAspectList =  collectRoughMatchedAspectListForSpecificClass(aspectInfoList,targetClass);

            wrapIfNessary(rouchMatchedAspectList,targetClass);
        }

        // aspect weaver


    }

    private void wrapIfNessary(List<AspectInfo> rouchMatchedAspectList, Class<?> targetClass) {
        if(ValidationUtil.isEmpty(rouchMatchedAspectList)){
            return;
        }
        AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass,rouchMatchedAspectList);
        Object proxyBean = ProxyCreater.createProxy(targetClass,aspectListExecutor);
        beanContainer.addBean(targetClass,proxyBean);
    }

    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> targetClass) {
        List<AspectInfo> rouchMatchedAspectList = new ArrayList<>();
        for(AspectInfo aspectInfo : aspectInfoList){
            if(aspectInfo.getPointcutLocator().roughMatches(targetClass)){
                rouchMatchedAspectList.add(aspectInfo);
            }
        }
        return rouchMatchedAspectList;
    }

    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for(Class<?> aspectClass : aspectSet){
            if(!verifyAspect(aspectClass)){
                throw new RuntimeException("@Aspect and @Order must be added to the Aspect class, Aspect class must extend from DefaultAspect ");
            }
            Order orderTag = aspectClass.getAnnotation(Order.class);
            Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
            DefaultAspect defaultAspect =(DefaultAspect) beanContainer.getBean(aspectClass);

            PointcutLocator pointcutLocator = new PointcutLocator(aspectTag.pointcut());
            AspectInfo aspectInfo = new AspectInfo(orderTag.value(),defaultAspect,pointcutLocator);
            aspectInfoList.add(aspectInfo);
        }
        return aspectInfoList;
    }

    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) &&
                aspectClass.isAnnotationPresent(Order.class)&&
                DefaultAspect.class.isAssignableFrom(aspectClass);
    }
}
