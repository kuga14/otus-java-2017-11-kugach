package ru.kugach.artem.otus;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectionHelper {

    static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    static private Class<?>[] toClasses(Object[] args) {
        List<Class<?>> classes = Arrays.stream(args)
                .map(Object::getClass)
                .collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }

    static boolean isWrapper(Class cls){
        if(cls.equals(Boolean.class))
            return true;
        if(cls.equals(Byte.class))
            return true;
        if(cls.equals(Short.class))
            return true;
        if(cls.equals(Integer.class))
            return true;
        if(cls.equals(Long.class))
            return true;
        if(cls.equals(Float.class))
            return true;
        if(cls.equals(Double.class))
            return true;
        if(cls.equals(Character.class))
            return true;
        return false;
    }

    /*
    static Object process(Object src) throws IllegalAccessException {
        Class clazz  = src.getClass();
        if(clazz.isPrimitive() || isWrapper(clazz) || clazz.equals(String.class) || Map.class.isAssignableFrom(clazz)){
            return src;
        } else{
            return processObject(src);
        }
    }*/

    static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    static Class getFieldType(Class<?> clz,String fieldName){
        try {
            return clz.getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    static List<String> getFieldNames(Class<?> clz){
        List<String> fieldNames = new ArrayList<>();
        for(Field field : clz.getDeclaredFields()){
            field.setAccessible(true);
            fieldNames.add(field.getName().toLowerCase());
        }
        return fieldNames;
    }

    static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name);
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    /*
    static Table<String, Class, Object> processObject(Object src) throws IllegalAccessException {
        //Table<String,Class,Object> res = HashBasedTable.create();
        List<Integer> rowsTable= Lists.newArrayList(1,2);
        Class clazz  = src.getClass();
        if(clazz != null){
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                field.setAccessible(true);
                if ((field.getModifiers() & Modifier.TRANSIENT) == 0){
                    res.put(field.getName(), field.getType(),(field.get(src)==null)? null : process(field.get(src)));//Guava.Table
                }
            }
        }
        return res;
    }*/
}

