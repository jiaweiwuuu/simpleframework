package org.simpleframework.mvc.processor.impl;

import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

public class JspRequestProcessor implements RequestProcessor {
    private static final String JSP_SERVLET = "jsp";

    private static final String JSP_RESOURCE_PREFIX = "/templates/";

    private RequestDispatcher jspServlet;
    public JspRequestProcessor(ServletContext servletContext) {
        jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);
        if(jspServlet == null){
            throw new RuntimeException("There is no jsp tomcat servlet");
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        if(isJSPResource(requestProcessorChain.getRequestPath())){
            jspServlet.forward(requestProcessorChain.getRequest(),requestProcessorChain.getResponse());
            return false;
        }
        else{
            return true;
        }
    }
    private boolean isJSPResource(String requestPath) {
        return requestPath.startsWith(JSP_RESOURCE_PREFIX);
    }
}
