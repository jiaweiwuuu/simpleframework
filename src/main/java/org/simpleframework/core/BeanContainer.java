package org.simpleframework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.core.annotation.Component;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.core.annotation.Repository;
import org.simpleframework.core.annotation.Service;
import org.simpleframework.core.util.ClassUtil;
import org.simpleframework.core.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {
    // store class object
    private final Map<Class<?>,Object> beanMap = new  ConcurrentHashMap<>();

    private boolean loaded = false;
    //load bean annotation

    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION = Arrays.asList(Component.class, Controller.class, Repository.class, Service.class, Aspect.class);



    public boolean isLoaded(){
        return loaded;
    }






    public static BeanContainer getInstance(){
        return ContainHolder.HOLDER.instance;
    }
    private enum ContainHolder{
        HOLDER;
        private BeanContainer instance;
        ContainHolder(){
            instance = new BeanContainer();
        }
    }


    //load all beans
    public synchronized void loadBeans(String packageName){
        if(isLoaded()){
            log.warn("beans are loaded");
            return;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if(ValidationUtil.isEmpty(classSet)){
            log.warn("extract nothing from package {}",packageName);
            return;
        }

        for(Class<?> cls:classSet){
            for(Class<? extends Annotation> annotaion : BEAN_ANNOTATION ){
                if(cls.isAnnotationPresent(annotaion)){
                    beanMap.put(cls,ClassUtil.newInstance(cls,true));
                }
            }
        }
        loaded = true;
    }

    public int size(){
        return beanMap.size();
    }

    public Object addBean(Class<?> cls,Object bean){
        return beanMap.put(cls,bean);
    }

    public Object removeBean(Class<?> cls){
        return beanMap.remove(cls);
    }

    public Object getBean(Class<?> cls){
        return beanMap.get(cls);
    }

    public Set<Class<?>> getClasses(){
        return beanMap.keySet();
    }

    public Set<Object> getBeans(){
        return new HashSet<>(beanMap.values());
    }

    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> cls){
        Set<Class<?>> keySet = getClasses();
        if(ValidationUtil.isEmpty(keySet)){
            log.warn("nothing in beanMap");
            return null;
        }

        Set<Class<?>> classSet = new HashSet<>();

        for(Class<?> clzz : keySet){
            if(clzz.isAnnotationPresent(cls)){
                classSet.add(clzz);
            }
        }
        return classSet.size()>0 ? classSet:null;
    }

    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass){

        Set<Class<?>> keySet = getClasses();
        if(ValidationUtil.isEmpty(keySet)){
            log.warn("nothing in beanMap");
            return null;
        }

        Set<Class<?>> classSet = new HashSet<>();

        for(Class<?> clzz : keySet){
            if(interfaceOrClass.isAssignableFrom(clzz) && !clzz.equals(interfaceOrClass)){
                classSet.add(clzz);
            }
        }
        return classSet.size()>0 ? classSet:null;
    }






}
