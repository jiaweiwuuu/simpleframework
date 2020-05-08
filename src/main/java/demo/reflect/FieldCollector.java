package demo.reflect;

import java.awt.desktop.SystemSleepEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class FieldCollector {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class cls = Class.forName("demo.reflect.ReflectTarget");

        Field[] fields = cls.getFields();

        for(Field field: fields){
            System.out.println(field);
        }

        fields = cls.getDeclaredFields();

        for(Field field:fields){
            System.out.println(field);
        }

        Field field = cls.getField("name");
        System.out.println(field);

        ReflectTarget obj = (ReflectTarget) cls.getConstructor().newInstance();

        field.set(obj,"reflect1");

        System.out.println(obj.name);

        field = cls.getDeclaredField("targetInfo");
        field.setAccessible(true);
        System.out.println(field);

        field.set(obj,123456);

        System.out.println(obj);


    }
}
