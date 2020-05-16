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


        Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new HashMap<>();

        if(ValidationUtil.isEmpty(aspectSet)){
            return;
        }
        for(Class<?> aspectClass : aspectSet){
            if(verifyAspect(aspectClass)){
                categorizeAspect(categorizedMap, aspectClass);
            }
            else {
                throw new RuntimeException("@Aspect and @ order have not been added to the Aspect class, or Aspect class does not extend from DefaultAspect or the value in Aspect Tag equals @Aspect");
            }
        }

        if(ValidationUtil.isEmpty(categorizedMap)){return;}

        for(Class<? extends Annotation > category:categorizedMap.keySet()){
            weaveByCategory(category,categorizedMap.get(category));
        }
    }

    private void weaveByCategory(Class<? extends Annotation> category, List<AspectInfo> aspectInfoList) {
        Set<Class<?>> classSet = beanContainer.getClassesByAnnotation(category);
        if(ValidationUtil.isEmpty(classSet)){
            return;
        }
        for(Class<?> targetClass : classSet){
            AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass,aspectInfoList);
            Object proxyBean = ProxyCreater.createProxy(targetClass,aspectListExecutor);

            beanContainer.addBean(targetClass,proxyBean);
        }
    }

    private void categorizeAspect(Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClass) {
        Order orderTag = aspectClass.getAnnotation(Order.class);
        Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
        DefaultAspect aspect =(DefaultAspect) beanContainer.getBean(aspectClass);
        AspectInfo aspectInfo = new AspectInfo(orderTag.value(),aspect);
        if(categorizedMap.containsKey(aspectTag.value())){
            List<AspectInfo> aspectInfoList = new ArrayList<>();
            aspectInfoList.add(aspectInfo);
            categorizedMap.put(aspectTag.value(),aspectInfoList);
        }
        else{
            List<AspectInfo> aspectInfoList = categorizedMap.get(aspectTag);
            aspectInfoList.add(aspectInfo);
        }
    }

    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) &&
                aspectClass.isAnnotationPresent(Order.class)&&
                DefaultAspect.class.isAssignableFrom(aspectClass)&&
                aspectClass.getAnnotation(Aspect.class).value() != Aspect.class;
    }
}
