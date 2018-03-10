package ru.kugach.artem.otus.dbservice;

import ru.kugach.artem.otus.base.DataSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SqlQueryHelper {

    private static final Map<String, String> javaToOracleDataTypeMapping;

    static{
        Map<String,String> map = new HashMap<>();
        map.put("Boolean","NUMBER(1,0)");
        map.put("Byte","NUMBER(3,0)");
        map.put("Short","NUMBER(5,0)");
        map.put("Integer","NUMBER(10,0)");
        map.put("Long","NUMBER(19,0)");
        map.put("Float","FLOAT(10)");
        map.put("Double","FLOAT(19)");
        map.put("Character","VARCHAR(4000)");
        map.put("boolean","NUMBER(1,0)");
        map.put("byte","NUMBER(3,0)");
        map.put("short","NUMBER(5,0)");
        map.put("int","NUMBER(10,0)");
        map.put("long","NUMBER(19,0)");
        map.put("float","FLOAT(10)");
        map.put("double","FLOAT(19)");
        map.put("char","VARCHAR(4000)");
        map.put("String","VARCHAR(4000)");
        javaToOracleDataTypeMapping = Collections.unmodifiableMap(map);
    }


    public static String dropSequenceDDL(Class<? extends DataSet> clz) {
        return "drop sequence "+getTableName(clz)+"_seq";
    }

    public static String dropTableDDL(Class<? extends DataSet> clz) {
        return "drop table "+getQuotedTableName(clz);
    }

    public static String createSequenceDDL(Class<?> clz,String databaseProductName){
        switch (databaseProductName) {
            case "Oracle":
                return "CREATE SEQUENCE " + getTableName(clz) + "_seq " +
                        "START WITH 1 " +
                        "INCREMENT BY 1 " +
                        "NOMAXVALUE";
            default:
                return null;
        }
    }

    public static String createTriggerDDL(Class<?> clz,String databaseProductName){
        switch (databaseProductName){
            case "Oracle" : return  "create or replace trigger " + getTableName(clz)+"_id_trg "+
                                    "before insert on " + getTableName(clz)+
                                    " for each row "+
                                    "begin "+
                                    "if :new.id is null then "+
                                    "select "+getTableName(clz)+"_seq.nextval into :new.id from dual;"+
                                    "end if;"+
                                    "end;";
            default: return null;
        }
    }

    public static String createTableDDL(Class<?> clz, String databaseProductName){
        if (clz == null) {
            return null;
        }
        StringBuffer str = new StringBuffer();
        str.append("CREATE TABLE ");
        str.append(getTableName(clz));
        str.append("(");
        str.append("id NUMBER(19,0)");
        for(String columnName : ReflectionHelper.getFieldNames(clz)){
            str.append(',');
            toColumnDefinition(columnName, ReflectionHelper.getFieldType(clz,columnName), str, databaseProductName);
        }
        str.append(")");
        return str.toString();
    }



    private static String toColumnDefinition(String key, Class<?> clz, StringBuffer str,String databaseProductName) {
        str.append("\""+key+"\"");
        str.append(' ');
        str.append(getDBDataType(clz,databaseProductName));
        return str.toString();
    }

    public static String getQuotedTableName(Class<?> clz){
        return "\""+clz.getSimpleName().toUpperCase()+"\"";
    }

    public static String getTableName(Class<?> clz){
        return clz.getSimpleName().toUpperCase();
    }

    public static String createSelectQuery(Class<?> clz){
        StringBuffer str = new StringBuffer();
        str.append("SELECT ");
        str.append("id");
        for(String columnName : ReflectionHelper.getFieldNames(clz)){
            str.append(',');
            str.append("\""+columnName+"\"");
        }
        str.append(" FROM ");
        str.append(getQuotedTableName(clz));
        str.append(" WHERE ");
        str.append("id=?");
        return str.toString();
    }

    public static String createInsertQuery(Class<?> clz){
        StringBuffer sb=new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(getQuotedTableName(clz));
        boolean first = true;
        sb.append('(');
        int size=0;
        for(String columnName : ReflectionHelper.getFieldNames(clz)){
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            sb.append("\""+columnName+"\"");
            size++;
        }
        sb.append(')');
        sb.append("VALUES");
        first = true;
        sb.append('(');
        for(int i=0;i<size;i++){
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            sb.append('?');
        }
        sb.append(')');
        return sb.toString();
    }

    public static String getDBDataType(Class<?> clz, String databaseProductName) {
        switch(databaseProductName){ //getDatabaseProductName()
            case "Oracle" : return getOracleDataType(clz);
            default: return null;
        }

    }

    public static String getOracleDataType(Class<?> clz) {
        String className = clz.getSimpleName();
        return javaToOracleDataTypeMapping.getOrDefault(className,"VARCHAR(4000)");
    }

}
