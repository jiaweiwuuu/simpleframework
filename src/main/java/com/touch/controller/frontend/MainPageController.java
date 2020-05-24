package com.touch.controller.frontend;

import com.touch.entity.dto.MainPageInfoDTO;
import com.touch.entity.dto.Result;
import com.touch.service.combine.HeadLineShopCategoryCombineService;
import com.touch.service.combine.imple.HeadLineShopCategoryCombineServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;
import org.simpleframework.mvc.annotation.RequestMapping;
import org.simpleframework.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
@Getter
@RequestMapping("/mainpage")
@Slf4j
public class  MainPageController {
    @Autowired(value = "HeadLineShopCategoryCombineServiceImpl")
    private HeadLineShopCategoryCombineService service;
    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return service.getMainPageInfo();
    }
    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public void throwException(){
        log.info("in the throw exception");
        throw new RuntimeException("test");
    }

}
