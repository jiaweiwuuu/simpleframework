package com.touch.service.solo.impl;

import com.touch.entity.bo.HeadLine;
import com.touch.entity.dto.Result;
import com.touch.service.solo.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotation.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@Service
@Slf4j
public class HeadLineServiceImpl implements HeadLineService {
    @Override
    public Result<Boolean> addHeadLine(HeadLine headLine) {
        log.info("add Headline, lineName[{}], lineImg[{}],lineLink[{}],priority[{}]",headLine.getLineName(),headLine.getLineImg(),headLine.getLineLink(),headLine.getPriority());
        Result<Boolean> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(true);
        return result;
    }

    @Override
    public Result<Boolean> removeHeadLine(int headLineId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> queryHeadLineById(int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> queryHeadline(HeadLine headLineCondition, int pageIndex, int pageSize) {
        List<HeadLine> headLineList = new ArrayList<>();
        HeadLine headLine1 = new HeadLine();
        headLine1.setLineId(1L);
        headLine1.setLineName("test1");
        headLine1.setLineLink("www.baidu.com");
        headLine1.setLineImg("http://localhost:8080/simpleframework/static/limengjia.png");

        HeadLine headLine2 = new HeadLine();
        headLine2.setLineId(1L);
        headLine2.setLineName("test2");
        headLine2.setLineLink("www.baidu.com");
        headLine2.setLineImg("http://localhost:8080/simpleframework/static/limengjia.png");

        headLineList.add(headLine1);
        headLineList.add(headLine2);
        Result<List<HeadLine>> result = new Result<>();
        result.setData(headLineList);
        result.setCode(200);
        return result;
    }
}
