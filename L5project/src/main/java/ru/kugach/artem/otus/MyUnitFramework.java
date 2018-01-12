package ru.kugach.artem.otus;

import ru.kugach.artem.otus.annotations.After;
import ru.kugach.artem.otus.annotations.Before;
import ru.kugach.artem.otus.annotations.Test;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyUnitFramework {

    public void runClasses(Class<?>... classes){
        List<Method> testMethods;
        Method beforeMethod;
        Method afterMethod;
        Object obj;
        for(Class<?> clazz : classes){
            testMethods = new ArrayList<Method>();
            testMethods = ReflectionHelper.findAnnotatedMethods( clazz, Test.class);
            beforeMethod = ReflectionHelper.findAnnotatedMethod( clazz, Before.class);
            afterMethod = ReflectionHelper.findAnnotatedMethod( clazz, After.class);
            if(!testMethods.isEmpty()){
                System.out.println("* Process class " + clazz.getName());
                for(Method method : testMethods){
                    obj = ReflectionHelper.instantiate(clazz);
                    ReflectionHelper.callMethod(obj,beforeMethod.getName());
                    System.out.println("** Run "+method.getName());
                    ReflectionHelper.callMethod(obj,method.getName());
                    ReflectionHelper.callMethod(obj,afterMethod.getName());
                }
                obj = null;
            }
        }
    }

    public void runPackage(String pkg){
        System.out.println("Process package " + pkg);
        List<Class<?>> classes = getClassesForPackage(pkg);
        Class<?>[] classesArray = new Class<?>[classes.size()];
        classes.toArray(classesArray);
        runClasses(classesArray);
    }

    private static List<Class<?>> processDirectory(File directory, String packageName) {
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];
            String className = null;
            if (fileName.endsWith(".class")) {
                className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
            }
            if (className != null) {
                classes.add(loadClass(className));
            }
        }
        return classes;
    }

    private static List<Class<?>> getClassesForPackage(String packageName) {
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        String relPath = packageName.replace('.', '/');
        URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
        if (resource == null) {
            throw new RuntimeException("Unexpected problem: No resource for " + relPath);
        }
        classes.addAll(processDirectory(new File(resource.getPath()), packageName));
        return classes;
    }

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
        }
    }
}
