package com.touch.controller.frontend;

import com.touch.entity.dto.MainPageInfoDTO;
import com.touch.entity.dto.Result;
import com.touch.service.combine.HeadLineShopCategoryCombineService;
import com.touch.service.combine.imple.HeadLineShopCategoryCombineServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageController {
    private HeadLineShopCategoryCombineService service = new HeadLineShopCategoryCombineServiceImpl();
    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return service.getMainPageInfo();
    }

}
