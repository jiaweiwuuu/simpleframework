package org.simpleframework.mvc.render.impl;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.render.ResultRender;
@Slf4j
public class DefaultResultRender implements ResultRender {
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        log.info("in the default");
        requestProcessorChain.getResponse().setStatus(requestProcessorChain.getResponseCode());
    }
}
