package demo.pattern.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AlipayInvocationHandler implements InvocationHandler {
    private Object targetObject;
    public AlipayInvocationHandler(Object targetObject){
        this.targetObject = targetObject;
    }
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        beforePay();
        Object result= method.invoke(targetObject, objects);
        afterPay();
        return result;
    }

    private void beforePay(){
        System.out.println("get money from bank");
    }

    private void afterPay(){
        System.out.println("pay imooc");
    }
}
