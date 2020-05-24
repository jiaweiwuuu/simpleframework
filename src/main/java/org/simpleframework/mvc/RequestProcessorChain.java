package org.simpleframework.mvc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.mvc.processor.RequestProcessor;
import org.simpleframework.mvc.render.ResultRender;
import org.simpleframework.mvc.render.impl.DefaultResultRender;
import org.simpleframework.mvc.render.impl.InternalErrorResultRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;


@Data
@Slf4j
public class RequestProcessorChain {
    private Iterator<RequestProcessor> requestProcessorIterator;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String requestMethod;
    private String requestPath;
    private int responseCode;
    private ResultRender resultRender;

    public RequestProcessorChain(Iterator<RequestProcessor> iterator, HttpServletRequest req, HttpServletResponse resp) {
        this.requestProcessorIterator = iterator;
        this.request = req;
        this.response = resp;
        this.requestMethod = req.getMethod();
        this.requestPath = req.getPathInfo();
        this.responseCode = HttpServletResponse.SC_OK;
    }

    public void doRequestProcessorChain() {
        //1 通过迭代器遍历注册的请求处理器实现类列表
        //2 直到某个请求处理器执行后返回false为止
        try{
            while(requestProcessorIterator.hasNext()){
                if(!requestProcessorIterator.next().process(this)){
                    break;
                }
            }
        }
        catch (Exception e){
            resultRender = new InternalErrorResultRender(e.getMessage());
            log.error("doRequestProcessorChain error:",e);
        }



        //3 期间如果出现异常，交由内部异常渲染器处理
    }

    public void deRender() {
        if(resultRender == null){
            resultRender = new DefaultResultRender();
        }

        try {
            resultRender.render(this);
        } catch (Exception e) {
            log.error("doRender error:",e);
            throw new RuntimeException(e);
        }
    }
}
