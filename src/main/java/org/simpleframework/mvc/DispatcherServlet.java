package org.simpleframework.mvc;

import com.touch.controller.frontend.MainPageController;
import com.touch.controller.superadmin.HeadLineOperationController;
import org.simpleframework.aop.AspectWeaver;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.DependencyInjector;
import org.simpleframework.mvc.processor.RequestProcessor;
import org.simpleframework.mvc.processor.impl.ControllerRequestProcessor;
import org.simpleframework.mvc.processor.impl.JspRequestProcessor;
import org.simpleframework.mvc.processor.impl.PreRequestProcessor;
import org.simpleframework.mvc.processor.impl.StaticResourceRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {
    private List<RequestProcessor> PROCESSOR = new ArrayList<>();
    @Override
    public void init(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.touch");
        new AspectWeaver().doAop();
        new DependencyInjector().doIOC();



        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JspRequestProcessor(getServletContext()));
        PROCESSOR.add(new ControllerRequestProcessor());

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSOR.iterator(),req,resp);

        requestProcessorChain.doRequestProcessorChain();

        requestProcessorChain.deRender();

    }
}
