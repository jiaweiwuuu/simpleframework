package demo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Ref;

public class ConstructorColletor {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class cls = Class.forName("demo.reflect.ReflectTarget");

        Constructor[] constructors = cls.getConstructors();
        for(Constructor c : constructors){
            System.out.println(c);
        }

        constructors = cls.getDeclaredConstructors();
        for(Constructor c : constructors){
            System.out.println(c);
        }


        Constructor constructor = cls.getConstructor(String.class,int.class);
        System.out.println(constructor);

        constructor = cls.getDeclaredConstructor(int.class);
        System.out.println(constructor);

        constructor.setAccessible(true);
        ReflectTarget reflectTarget = (ReflectTarget) constructor.newInstance(1);




    }
}
