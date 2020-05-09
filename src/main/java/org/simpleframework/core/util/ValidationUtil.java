package org.simpleframework.core.util;

import java.util.Collection;

public class ValidationUtil {

    public static boolean isEmpty(Collection<?> obj){
        return obj==null || obj.isEmpty();
    }
    public static boolean isEmpty(String obj){
        return obj == null || obj.equals("");
    }
    public static boolean isEmpty(Object[] objects){
        return objects == null || objects.length == 0;
    }
}
