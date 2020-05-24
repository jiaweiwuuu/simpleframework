package com.touch.controller.superadmin;

import com.touch.entity.bo.HeadLine;
import com.touch.entity.dto.Result;
import com.touch.service.solo.HeadLineService;
import com.touch.service.solo.impl.HeadLineServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;
import org.simpleframework.mvc.annotation.RequestMapping;
import org.simpleframework.mvc.annotation.RequestParam;
import org.simpleframework.mvc.annotation.ResponseBody;
import org.simpleframework.mvc.type.ModelAndView;
import org.simpleframework.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Slf4j
@Controller
@RequestMapping(value = "/headline")
public class HeadLineOperationController {
    @Autowired
    private HeadLineService service;

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView addHeadLine(@RequestParam("lineName") String lineName, @RequestParam("lineLink")String lineLink, @RequestParam("lineImg")String lineImg, @RequestParam("priority")Integer priority) {
        //TODO params verification and params transfer

        HeadLine headLine = new HeadLine();
        headLine.setLineImg(lineImg);
        headLine.setLineLink(lineLink);
        headLine.setLineName(lineName);
        headLine.setPriority(priority);
        Result<Boolean> result = service.addHeadLine(headLine);
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setView("hello.jsp").addViewData("result",result);
        return modelAndView;
    }
@RequestMapping(value = "/remove",method = RequestMethod.GET)
    public void removeHeadLine() {
        //return service.removeHeadLine(1);
        log.info("remove headline");
    }

    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return service.modifyHeadLine(new HeadLine());
    }

    public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp) {
        return service.queryHeadLineById(1);
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    public Result<List<HeadLine>> queryHeadline() {
        return service.queryHeadline(new HeadLine(),1,5);
    }
}
