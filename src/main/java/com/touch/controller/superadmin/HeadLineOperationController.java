package com.touch.controller.superadmin;

import com.touch.entity.bo.HeadLine;
import com.touch.entity.dto.Result;
import com.touch.service.solo.HeadLineService;
import com.touch.service.solo.impl.HeadLineServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HeadLineOperationController {
    private HeadLineService service = new HeadLineServiceImpl();


    public Result<Boolean> addHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        //TODO params verification and params transfer
        return service.addHeadLine(new HeadLine());

    }

    public Result<Boolean> removeHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return service.removeHeadLine(1);
    }

    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return service.modifyHeadLine(new HeadLine());
    }

    public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp) {
        return service.queryHeadLineById(1);
    }

    public Result<List<HeadLine>> queryHeadline(HttpServletRequest req, HttpServletResponse resp) {
        return service.queryHeadline(new HeadLine(),1,5);
    }
}
