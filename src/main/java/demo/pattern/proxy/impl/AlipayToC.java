package demo.pattern.proxy.impl;

import demo.pattern.proxy.ToCPayment;

public class AlipayToC implements ToCPayment {
    ToCPayment toCPayment;
    public AlipayToC(ToCPayment toCPayment){
        this.toCPayment = toCPayment;
    }
    @Override
    public void pay() {
        beforePay();
        toCPayment.pay();
        afterPay();
    }

    private void beforePay(){
        System.out.println("get money from bank");
    }

    private void afterPay(){
        System.out.println("pay imooc");
    }
}
