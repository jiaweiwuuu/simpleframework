package demo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodCollector {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class cls = Class.forName("demo.reflect.ReflectTarget");


        Method[] methods = cls.getMethods();
        for(Method method:methods){
            System.out.println(method);
        }

        methods = cls.getDeclaredMethods();
        for(Method method:methods){
            System.out.println(method);
        }


        Method method = cls.getMethod("show1", String.class);

        ReflectTarget reflectTarget = (ReflectTarget) cls.getConstructor().newInstance();

        method.invoke(reflectTarget,"show1 test");
    }
}
