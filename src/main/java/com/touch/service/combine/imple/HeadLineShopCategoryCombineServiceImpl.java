package com.touch.service.combine.imple;

import com.touch.entity.bo.HeadLine;
import com.touch.entity.bo.ShopCategory;
import com.touch.entity.dto.MainPageInfoDTO;
import com.touch.entity.dto.Result;
import com.touch.service.combine.HeadLineShopCategoryCombineService;
import com.touch.service.solo.HeadLineService;
import com.touch.service.solo.ShopCategoryService;
import org.simpleframework.core.annotation.Service;
import org.simpleframework.inject.annotation.Autowired;

import java.util.List;
@Service
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        // 1. get headline list
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        Result<List<HeadLine>> headLineResult =  headLineService.queryHeadline(headLineCondition,1,4);
        // 2. get shopcategory list

        ShopCategory shopCategoryCondition = new ShopCategory();
        Result<List<ShopCategory>> shopCategoryResult = shopCategoryService.queryShopCategory(shopCategoryCondition,1,100);

        //3. merge

        Result<MainPageInfoDTO> result =  mergeMainPageInfoResult(headLineResult, shopCategoryResult);
        return  result;
    }
    private Result<MainPageInfoDTO> mergeMainPageInfoResult(Result<List<HeadLine>> headLineResult,Result<List<ShopCategory>> shopCategoryResult){
        return null;
    }
}
