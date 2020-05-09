package com.touch.controller.superadmin;

import com.touch.entity.bo.HeadLine;
import com.touch.entity.bo.ShopCategory;
import com.touch.entity.dto.Result;
import com.touch.service.solo.ShopCategoryService;
import com.touch.service.solo.impl.ShopCategoryServiceImpl;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Controller
public class ShopCategoryOperationController {
    @Autowired
    private ShopCategoryService service = new ShopCategoryServiceImpl();

    public Result<Boolean> addShopCategory(HttpServletRequest req, HttpServletResponse resp) {
        return service.addShopCategory(new ShopCategory());
    }

    public Result<Boolean> removeShopCategory(HttpServletRequest req, HttpServletResponse resp) {
        return service.removeShopCategory(1);
    }


    public Result<Boolean> modifyShopCategory(HttpServletRequest req, HttpServletResponse resp) {
        return service.modifyShopCategory(new ShopCategory());
    }


    public Result<HeadLine> queryShopCategoryById(HttpServletRequest req, HttpServletResponse resp) {
        return service.queryShopCategoryById(1);
    }


    public Result<List<ShopCategory>> queryShopCategory(HttpServletRequest req, HttpServletResponse resp) {
        return service.queryShopCategory(new ShopCategory(),1,5);
    }
}
