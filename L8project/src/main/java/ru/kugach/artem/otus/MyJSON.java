package ru.kugach.artem.otus;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyJSON {

    public String toJson(Object src) {
        try {
            src = ReflectionHelper.process(src);
            Class clazz = src.getClass();
            if (src == null) {
                return "null";
            } else if (src instanceof String) {
                return "\"" + ((String) src) + "\"";
            } else if (ReflectionHelper.isWrapper(clazz) || clazz.isPrimitive()) {
                return src.toString();
            } else if (src instanceof List) {
                return toJSONString((List) src);
            } else if (src instanceof Map) {
                return toJSONString((Map) src);
            } else {
                return "null";
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String toJSONString(Map map) {
        if (map == null) {
            return "null";
        } else {
            StringBuffer sb = new StringBuffer();
            boolean first = true;
            Iterator iter = map.entrySet().iterator();
            sb.append('{');

            while (iter.hasNext()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(',');
                }

                Map.Entry entry = (Map.Entry) iter.next();
                toJSONString(entry.getKey(), entry.getValue(), sb);
            }

            sb.append('}');
            return sb.toString();
        }
    }

    private String toJSONString(Object key, Object value, StringBuffer sb) {
        if (key == null) {
            sb.append("null");
        }else {
            sb.append(qouteKey(key));
        }
        sb.append(':');
        sb.append(toJson(value));
        return sb.toString();
    }

    private String qouteKey(Object key){
        if (key instanceof Map || key instanceof String) {
            return toJson(key);
        } else {
            return  "\""+toJson(key)+"\"";
        }

    }

    private String toJSONString(List list) {
        if (list == null) {
            return "null";
        } else {
            boolean first = true;
            StringBuffer sb = new StringBuffer();
            Iterator iter = list.iterator();
            sb.append('[');

            while(iter.hasNext()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(',');
                }

                Object value = iter.next();
                if (value == null) {
                    sb.append("null");
                } else {
                    sb.append(toJson(value));
                }
            }

            sb.append(']');
            return sb.toString();
        }
    }
}
