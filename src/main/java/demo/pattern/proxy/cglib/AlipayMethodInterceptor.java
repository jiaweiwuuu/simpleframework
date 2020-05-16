package demo.pattern.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AlipayMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        beforePay();
        Object result = methodProxy.invokeSuper(o,objects);
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
