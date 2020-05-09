package org.simpleframework.inject;

import com.touch.controller.frontend.MainPageController;
import com.touch.service.combine.HeadLineShopCategoryCombineService;
import com.touch.service.combine.imple.HeadLineShopCategoryCombineServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.core.BeanContainer;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectorTest {

    @DisplayName("ioc test")
    @Test
    void doIOCTest() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.touch");
        Assertions.assertEquals(true,beanContainer.isLoaded());
        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assertions.assertEquals(true,mainPageController instanceof MainPageController);
        HeadLineShopCategoryCombineService headLineShopCategoryCombineService = mainPageController.getService();
        Assertions.assertTrue(headLineShopCategoryCombineService != beanContainer.getBean(HeadLineShopCategoryCombineServiceImpl.class));

        new DependencyInjector().doIOC();

        Assertions.assertTrue(mainPageController.getService() == beanContainer.getBean(HeadLineShopCategoryCombineServiceImpl.class));
    }
}