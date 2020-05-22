package com.touch.service.solo.impl;

import com.touch.entity.bo.HeadLine;
import com.touch.entity.bo.ShopCategory;
import com.touch.entity.dto.Result;
import com.touch.service.solo.ShopCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotation.Service;

import java.util.List;
@Service
@Slf4j
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Override
    public Result<Boolean> addShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<Boolean> removeShopCategory(int shopCategoryId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<HeadLine> queryShopCategoryById(int ShopCategoryId) {
        return null;
    }

    @Override
    public Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategory, int pageIndex, int pageSize) {
        return null;
    }
}
