package org.simpleframework.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.core.util.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Getter

public class AspectListExecutor implements MethodInterceptor {
    private Class<?> targetClass;
    private List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList){
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sortAsectInfoList(aspectInfoList);
    }

    private List<AspectInfo> sortAsectInfoList(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo aspectInfo, AspectInfo t1) {
                return aspectInfo.getOrderIndex()-t1.getOrderIndex();
            }
        });
        return aspectInfoList;
    }


    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        if(ValidationUtil.isEmpty(sortedAspectInfoList)){
            return returnValue;
        }

        invokeBeforeAdvices(method,args);
        try{
            returnValue = methodProxy.invokeSuper(proxy,args);

            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        }
        catch (Exception e){
            invokeAftherThrowing(method,args,e);
        }
        return returnValue;
    }

    private void invokeAftherThrowing(Method method, Object[] args, Exception e) throws Throwable {
        for(int i=sortedAspectInfoList.size()-1; i>=0; i--){
            sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass,method,args,e);
        }
    }

    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for(int i=sortedAspectInfoList.size()-1; i>=0; i--){
            result = sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass,method,args,returnValue);
        }
        return result;
    }

    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for(AspectInfo aspectInfo : sortedAspectInfoList){
            aspectInfo.getAspectObject().before(targetClass,method,args);
        }
    }


}
