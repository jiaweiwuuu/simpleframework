package org.simpleframework.inject;

import javafx.beans.binding.ObjectExpression;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.core.util.ClassUtil;
import org.simpleframework.core.util.ValidationUtil;
import org.simpleframework.inject.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Set;

@Slf4j
public class DependencyInjector {
    private BeanContainer beanContainer;

    public DependencyInjector(){
        beanContainer = BeanContainer.getInstance();
    }

    public void doIOC(){
        if (ValidationUtil.isEmpty(beanContainer.getClasses())){
            log.warn("empty classet in beanContainer");
        }
        for(Class<?> cls : beanContainer.getClasses()){
            Field[] fields = cls.getDeclaredFields();
            if(ValidationUtil.isEmpty(fields)){
                log.warn("empty fields of class {}",cls.getName());
                continue;
            }
            for(Field field : fields){
                 if(field.isAnnotationPresent(Autowired.class)){
                     Autowired autowired = field.getAnnotation(Autowired.class);
                     String autowiredValue = autowired.value();
                     Class<?> fieldClass =  field.getType();

                     Object fieldValue = getFieldInstance(fieldClass,autowiredValue);
                     if(fieldValue == null){
                         throw new RuntimeException("unable to get field instance from class "+ fieldClass.getName() + autowiredValue);
                     }else{
                         Object targetBean =  beanContainer.getBean(cls);
                         ClassUtil.setField(field,targetBean,fieldValue,true);
                     }
                 }
            }
        }
    }

    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        Object object =  beanContainer.getBean(fieldClass);
        if(object != null){
            return object;
        }

        Class<?> implementedClass =getImplementedClass(fieldClass,autowiredValue);
        if(implementedClass != null){
            return beanContainer.getBean(implementedClass);
        }else{
            return null;
        }

    }

    private Class<?> getImplementedClass(Class<?> fieldClass,String autowiredValue) {
        Set<Class<?>> classSet =  beanContainer.getClassesBySuper(fieldClass);

        if(!ValidationUtil.isEmpty(classSet)){
            if(ValidationUtil.isEmpty(autowiredValue)){
                if(classSet.size() == 1){
                    return classSet.iterator().next();
                }
                else{
                    throw new RuntimeException("more than one implement classes for "+ fieldClass.getName()+ "please set @Autowired's value to pick one");
                }
            }
            else{
                for(Class<?> clazz : classSet){
                    if(autowiredValue.equals(clazz.getSimpleName())){
                        return clazz;
                    }
                }
            }
        }
        return null;

    }
}
