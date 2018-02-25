package ru.kugach.artem.otus;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ReflectionHelper {

    private static boolean isWrapper(Class cls){
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

    private static List processArray(Class arrayType, Object array) throws IllegalAccessException {
        int length =  java.lang.reflect.Array.getLength(array);
        List<Object> jsonArray = new ArrayList<>();
        if(arrayType.isArray())  {
            for(int j = 0; j < length; j++)
            {
                Object array2 = java.lang.reflect.Array.get(array, j);
                jsonArray.add(processArray(arrayType.getComponentType(),array2));
            }
        } else
        {
            for(int j = 0; j < length; j++)
            {
               jsonArray.add(process(java.lang.reflect.Array.get(array, j)));
            }
        }
        return jsonArray;
    }


    public static Object process(Object src) throws IllegalAccessException {
        Class clazz  = src.getClass();
        if(clazz.isPrimitive() || isWrapper(clazz) || clazz.equals(String.class) || Map.class.isAssignableFrom(clazz)){
            return src;
        } else if(clazz.isArray()){
            return processArray(clazz.getComponentType(), src);
        } else if(Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) src;
            return processArray(Object.class, collection.toArray());
        } else{
            return processObject(src);
        }
    }

    public static Map processObject(Object src) throws IllegalAccessException {
        Map<String,Object> jsonObject = new HashMap<>();
        Class clazz  = src.getClass();
        if(clazz != null){
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                field.setAccessible(true);
                if ((field.getModifiers() & Modifier.TRANSIENT) == 0){
                    jsonObject.put(field.getName(), process(field.get(src)));
                }
            }
        }
        return jsonObject;
    }
}
