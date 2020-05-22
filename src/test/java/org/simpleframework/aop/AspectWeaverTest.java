package org.simpleframework.aop;

import com.touch.controller.superadmin.HeadLineOperationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.DependencyInjector;

public class AspectWeaverTest {
    @Test
    @DisplayName("weaver test: doAOP")
    public void doAOPTest(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.touch");
        new AspectWeaver().doAop();
        new DependencyInjector().doIOC();

        HeadLineOperationController  headLineOperationController =(HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);
        headLineOperationController.addHeadLine(null,null);
    }
}
