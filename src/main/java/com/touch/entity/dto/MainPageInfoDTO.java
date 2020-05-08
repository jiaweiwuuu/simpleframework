package com.touch.entity.dto;

import com.touch.entity.bo.HeadLine;
import com.touch.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;
@Data
public class MainPageInfoDTO {
    private List<HeadLine> headLineList;
    private List<ShopCategory> shopCategoryList;

}
