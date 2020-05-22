package org.simpleframework.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.processor.RequestProcessor;
@Slf4j
public class PreRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        //1 设置请求编码 UTF-8
        requestProcessorChain.getRequest().setCharacterEncoding("UTF-8");
        //2 请求末尾/剔除
        String requestPath = requestProcessorChain.getRequestPath();
        if(requestPath.length() >1 && requestPath.endsWith("/")){
            requestProcessorChain.setRequestPath(requestPath.substring(0,requestPath.length()-1));
        }
        log.info("preprocess request {} {}",requestProcessorChain.getRequestMethod(), requestProcessorChain.getRequestPath());
        return true;
    }
}
