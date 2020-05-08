package com.touch.service.solo;

import com.touch.entity.bo.HeadLine;
import com.touch.entity.bo.ShopCategory;
import com.touch.entity.dto.Result;

import java.util.List;

public interface ShopCategoryService {
    Result<Boolean> addShopCategory(ShopCategory shopCategory);
    Result<Boolean> removeShopCategory(int shopCategoryId);
    Result<Boolean> modifyShopCategory(ShopCategory shopCategory);
    Result<HeadLine> queryShopCategoryById(int ShopCategoryId);
    Result<List<ShopCategory>>queryShopCategory(ShopCategory shopCategory, int pageIndex, int pageSize);
}
