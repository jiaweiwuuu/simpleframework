package org.simpleframework.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.core.util.ConverterUtil;
import org.simpleframework.core.util.ValidationUtil;
import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.annotation.RequestMapping;
import org.simpleframework.mvc.annotation.RequestParam;
import org.simpleframework.mvc.annotation.ResponseBody;
import org.simpleframework.mvc.processor.RequestProcessor;
import org.simpleframework.mvc.render.ResultRender;
import org.simpleframework.mvc.render.impl.JsonResultRender;
import org.simpleframework.mvc.render.impl.ResourceNotFoundResultRender;
import org.simpleframework.mvc.render.impl.ViewResultRender;
import org.simpleframework.mvc.type.ControllerMethod;
import org.simpleframework.mvc.type.RequestPathInfo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public class ControllerRequestProcessor implements RequestProcessor {
    private BeanContainer beanContainer;
    // 请求和controller方法的映射集合
    private Map<RequestPathInfo, ControllerMethod> pathControllerMethodMap = new ConcurrentHashMap<>();

    public ControllerRequestProcessor(){
        this.beanContainer = BeanContainer.getInstance();
        Set<Class<?>> requestMappingSet =  beanContainer.getClassesByAnnotation(RequestMapping.class);
        initPathControllerMethodMap(requestMappingSet);
    }

    private void initPathControllerMethodMap(Set<Class<?>> requestMappingSet) {
        if(ValidationUtil.isEmpty(requestMappingSet)){
            return;
        }

        //遍历所有被@requestmapping标记的类，获取类上面该注解的属性值作为一级路径
        for(Class<?> requestMappingClass : requestMappingSet){
            RequestMapping requestMapping = requestMappingClass.getAnnotation(RequestMapping.class);
            String basePath = requestMapping.value();
            if(!basePath.startsWith("/")){
                basePath = "/" + basePath;
            }
            //遍历类里面所有被@RequestMapping标记的方法，获取方法上面该注解的属性值，作为二级注解
            Method[] methods = requestMappingClass.getDeclaredMethods();
            if(ValidationUtil.isEmpty(methods)){
                continue;
            }
            for(Method method : methods){
                if(method.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping methodRequest = method.getAnnotation(RequestMapping.class);
                    String methodPath = methodRequest.value();
                    if(!methodPath.startsWith("/")){
                        methodPath = "/" + methodPath;
                    }
                    String url = basePath+methodPath;
                    //解析方法里被@RequestParam标记的参数
                    //获取该注解的属性值作为参数名
                    //获取被标记的参数的数据类型，建立参数名和参数类型的映射

                    Map<String, Class<?>> methodParams = new HashMap<>();
                    Parameter[] parameters = method.getParameters();

                    for(Parameter parameter : parameters){
                        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                        if(requestParam == null){
                            throw new RuntimeException("The parameter must have @RequestParam");
                        }
                        methodParams.put(requestParam.value(),parameter.getType());


                    }
                    //将获取到的信息封装成RequestPathInfo instance和controllerMethod instance 放置在映射表里

                    String httpMethod = String.valueOf(methodRequest.method());
                    RequestPathInfo requestPathInfo = new RequestPathInfo(httpMethod,url);
                    if(this.pathControllerMethodMap.containsKey(requestPathInfo)){
                        log.warn("duplicate url: {} registration, current class {} method {} will override the former one", requestPathInfo.getHttpMethod(),requestMappingClass.getName(),method.getName());
                    }
                    ControllerMethod controllerMethod = new ControllerMethod(requestMappingClass, method,methodParams);
                    this.pathControllerMethodMap.put(requestPathInfo,controllerMethod);
                }
            }
        }




    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {

        //1. 解析HttpServletRequest 的请求方法，请求路径，获取对应的ControllerMethod实例
        String method = requestProcessorChain.getRequestMethod();
        String path = requestProcessorChain.getRequestPath();
        ControllerMethod controllerMethod = this.pathControllerMethodMap.get(new RequestPathInfo(method,path));
        if(controllerMethod == null){
            requestProcessorChain.setResultRender(new ResourceNotFoundResultRender(method,path));
            return false;
        }

        //2. 解析请求参数，并传递给获取到的ControllerMethod实例去执行
        Object result = invokeControllerMethod(controllerMethod,requestProcessorChain.getRequest());


        //3. 根据处理结果，选择对应的render渲染

        setResultRender(result,controllerMethod,requestProcessorChain);
        return true;
    }

    private void setResultRender(Object result, ControllerMethod controllerMethod, RequestProcessorChain requestProcessorChain) {
        if(result == null){
            return;
        }
        ResultRender resultRender;

        boolean isJson = controllerMethod.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if(isJson){
            resultRender = new JsonResultRender(result);
        }else{
            resultRender = new ViewResultRender(result);
        }
        requestProcessorChain.setResultRender(resultRender);
    }

    private Object invokeControllerMethod(ControllerMethod controllerMethod, HttpServletRequest request) {
        //1. 从请求里获取GET或者POST的参数名及其对应的值
        Map<String, String> requestParamMap = new HashMap<>();
        Map<String ,String[]> parameterMap = request.getParameterMap();

        for(Map.Entry<String,String[]> parameter :parameterMap.entrySet()){
            if(!ValidationUtil.isEmpty(parameter.getValue())){
                requestParamMap.put(parameter.getKey(),parameter.getValue()[0]);
            }
        }
        //2. 根据获取到的请求参数名及其对应的值，以及controllerMethod里面的参数和类型映射关系，去实例化方法对应的参数
        List<Object> methodParams = new ArrayList<>();
        Map<String,Class<?>> methodParamMap = controllerMethod.getMethodParameters();
        for(String paramName : methodParamMap.keySet()){
           Class<?> type =  methodParamMap.get(paramName);
           String requestValue = requestParamMap.get(paramName);
           Object value;
           if(requestValue == null){
               value = ConverterUtil.primitiveNull(type);
           }
           else{
               value = ConverterUtil.convert(type,requestValue);
           }
           methodParams.add(value);
        }
        //3. 执行controller里面对应的方法并返回结果
        Object controller =  beanContainer.getBean(controllerMethod.getControllerClass());
        Method invokeMethod = controllerMethod.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        try{
            if(methodParams.size() == 0){
                result = invokeMethod.invoke(controller);
            }
            else{
                result = invokeMethod.invoke(controller,methodParams.toArray());
            }
        }
        catch (InvocationTargetException e){
            throw new RuntimeException( e.getTargetException()  );
        }
        catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
        return result;
    }
}
