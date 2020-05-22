package org.simpleframework.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
@Slf4j
public class StaticResourceRequestProcessor implements RequestProcessor {
    public static final String DEFAULT_TOMCAT_SERVLET = "default";
    public static final String STATIC_RESOURCE_PREFIX = "/static/";
    RequestDispatcher defaultDispatcher;
    public StaticResourceRequestProcessor(ServletContext servletContext){
        this.defaultDispatcher = servletContext.getNamedDispatcher(DEFAULT_TOMCAT_SERVLET);
        if(this.defaultDispatcher == null){
            throw new RuntimeException("There is no default tomcat servlet");
        }
        log.info("The default servlet for static resource is {}",DEFAULT_TOMCAT_SERVLET);
    }
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        //1. 判断是否请求的静态资源 webapp/static
        if(isStaticResource(requestProcessorChain.getRequestPath())){
            //2. 如果静态，请求发给default servlet 处理
            defaultDispatcher.forward(requestProcessorChain.getRequest(),requestProcessorChain.getResponse());
            return false;
        }
        else{
            return true;
        }


    }

    private boolean isStaticResource(String requestPath) {
        return requestPath.startsWith(STATIC_RESOURCE_PREFIX);
    }
}
