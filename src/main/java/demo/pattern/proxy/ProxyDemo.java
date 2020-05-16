package demo.pattern.proxy;

import demo.pattern.proxy.cglib.AlipayMethodInterceptor;
import demo.pattern.proxy.cglib.CglibUtil;
import demo.pattern.proxy.impl.AlipayToC;
import demo.pattern.proxy.impl.CommonPayment;
import demo.pattern.proxy.impl.ToCPaymentImpl;
import demo.pattern.proxy.jdkproxy.AlipayInvocationHandler;
import demo.pattern.proxy.jdkproxy.JdkDynamicProxyUtil;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationHandler;

public class ProxyDemo {
    public static void main(String[] args) {
//        ToCPayment toCPayment =  new AlipayToC(new ToCPaymentImpl());
//        toCPayment.pay();

//        ToCPayment toCPayment =  new ToCPaymentImpl();
//        InvocationHandler invocationHandler = new AlipayInvocationHandler(toCPayment);
//        ToCPayment toCProxy =  JdkDynamicProxyUtil.newProxyInstance(toCPayment,invocationHandler);
//        toCProxy.pay();

        CommonPayment commonPayment = new CommonPayment();
        MethodInterceptor methodInterceptor = new AlipayMethodInterceptor();
        CommonPayment commonPaymentProxy = CglibUtil.createProxy(commonPayment,methodInterceptor);
        commonPaymentProxy.pay();
    }
}
