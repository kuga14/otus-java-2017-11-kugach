package ru.kugach.artem.otus;

import org.json.simple.JSONValue;

public class MyJSON {

    public String toJSON(Object src){
        try {
            return JSONValue.toJSONString(ReflectionHelper.process(src));
        }catch (IllegalAccessException e){
            e.printStackTrace();
            return null;
        }
    }
}
