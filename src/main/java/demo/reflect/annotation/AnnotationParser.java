package demo.reflect.annotation;

import java.awt.desktop.SystemSleepEvent;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationParser {
    // parse class annotation
    public static void parseTypeAnnotation() throws ClassNotFoundException {
        Class cls = Class.forName("demo.reflect.annotation.Course");
        Annotation[] annotations = cls.getAnnotations();

        for(Annotation annotation: annotations){
            CourseInfoAnnotation courseInfoAnnotation = (CourseInfoAnnotation)annotation;
            System.out.println(courseInfoAnnotation.courseName());
        }
    }
    public static void parseFieldAnnotation() throws ClassNotFoundException{
        Class cls = Class.forName("demo.reflect.annotation.Course");
        Field[] fields = cls.getDeclaredFields();
        for(Field field:fields){
            if(field.isAnnotationPresent(PersonInfoAnnotation.class)){
                PersonInfoAnnotation personInfoAnnotation = field.getAnnotation(PersonInfoAnnotation.class);
                System.out.println(personInfoAnnotation.name());
            }
        }
    }

    public static void parseMethodAnnotation() throws ClassNotFoundException{
        Class cls = Class.forName("demo.reflect.annotation.Course");
        Method[] methods = cls.getDeclaredMethods();
        for(Method method : methods){
            if(method.isAnnotationPresent(CourseInfoAnnotation.class)){
                CourseInfoAnnotation courseInfoAnnotation = method.getAnnotation(CourseInfoAnnotation.class);
                System.out.println(courseInfoAnnotation.courseName());
            }
        }

    }

    public static void main(String[] args) throws ClassNotFoundException {
        //parseTypeAnnotation();
        parseFieldAnnotation();
        //parseMethodAnnotation();
    }
}
