package org.simpleframework.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerMethod {
    private Class<?> controllerClass;

    private Method invokeMethod;

    private Map<String,Class<?>> methodParameters;

}
