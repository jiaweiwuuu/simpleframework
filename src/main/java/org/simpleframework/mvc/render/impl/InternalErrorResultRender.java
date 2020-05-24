package org.simpleframework.mvc.render.impl;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;
@Slf4j
public class InternalErrorResultRender implements ResultRender {
    private String errorMsg;
    public InternalErrorResultRender(String errorMsg){
        this.errorMsg = errorMsg;
    }
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        log.info("error: {}",errorMsg);
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,errorMsg);
    }
}
