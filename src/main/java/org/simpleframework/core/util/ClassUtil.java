package org.simpleframework.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
@Slf4j
public class ClassUtil {

    public static final String FILE_PROTOCOL = "file";

    public static Set<Class<?>> extractPackageClass(String packageName){
        ClassLoader classLoader = getClassLoader();
        packageName.replace(".","/");
        URL url = classLoader.getResource(packageName.replace(".","/"));
        if(url == null){
            log.warn("unable to retrieve anything from {}",packageName);
        }

        Set<Class<?>> classSet = null;

        if(url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)){
            classSet = new HashSet<Class<?>>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet,packageDirectory,packageName);
        }
        return classSet;
    }

    public static Class<?> loadClass(String className){
        try{
            return Class.forName(className);
        }catch (ClassNotFoundException e){
            log.error("load class error:",e);
            throw new RuntimeException(e);
        }

    }
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }
    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packageName){
        if(!fileSource.isDirectory()){
            return;
        }
        
        File[]files =  fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.isDirectory()){
                    return true;
                }
                else{
                    String absoluteFilePath = file.getAbsolutePath();
                    if(absoluteFilePath.endsWith(".class")){
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }

            private void addToClassSet(String absoluteFilePath) {
                absoluteFilePath = absoluteFilePath.replace(File.separator,".");
                String className = absoluteFilePath.substring(absoluteFilePath.indexOf(packageName));
                className = className.substring(0,className.lastIndexOf("."));

                Class targetClass = loadClass(className);
                emptyClassSet.add(targetClass);
            }
        });


        if(files != null){
            for(File f:files){
                extractClassFile(emptyClassSet,f,packageName);
            }
        }
    }


    public static void main(String[] args) {
        extractPackageClass("com.touch.entity");
    }
}
