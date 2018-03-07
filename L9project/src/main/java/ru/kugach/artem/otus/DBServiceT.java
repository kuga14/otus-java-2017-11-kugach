package ru.kugach.artem.otus;

import ru.kugach.artem.otus.base.DataSet;
import ru.kugach.artem.otus.connection.DBServiceConnection;
import ru.kugach.artem.otus.executor.TExecutor;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class DBServiceT extends DBServiceConnection {

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

    private void execDDL(String query) throws SQLException {
        TExecutor executor = new TExecutor(getConnection());
        try{
            executor.execUpdate(query);
        } catch (java.sql.SQLSyntaxErrorException e){
            System.out.println(query+"; "+e.getMessage());
        }
    }


    private String getQuotedTableName(Class<?> clz){
        return "\""+clz.getSimpleName().toUpperCase()+"\"";
    }

    private String getTableName(Class<?> clz){
        return clz.getSimpleName().toUpperCase();
    }

    private <T extends DataSet> void createTable(T object) throws SQLException {
        execDDL(createTableDDL(object.getClass()));
        execDDL(createSequenceDDL(object.getClass()));
        execDDL(createTriggerDDL(object.getClass()));
    }

    private <T extends DataSet> void dropeTable(T object) throws SQLException {
        execDDL(dropTableDDL(object.getClass()));
        execDDL(dropSequenceDDL(object.getClass()));
    }

    private String dropSequenceDDL(Class<? extends DataSet> clz) {
        return "drop sequence "+getTableName(clz)+"_seq";
    }

    private String dropTableDDL(Class<? extends DataSet> clz) {
        return "drop table "+getQuotedTableName(clz);
    }

    private String createSequenceDDL(Class<?> clz){
        return "CREATE SEQUENCE "+getTableName(clz)+"_seq " +
                "START WITH 1 " +
                "INCREMENT BY 1 " +
                "NOMAXVALUE";
    }

    private String createTriggerDDL(Class<?> clz){
        return  "create or replace trigger " + getTableName(clz)+"_id_trg "+
                "before insert on " + getTableName(clz)+
                " for each row "+
                "begin "+
                "if :new.id is null then "+
                "select "+getTableName(clz)+"_seq.nextval into :new.id from dual;"+
                "end if;"+
                "end;";
    }

    private String createTableDDL(Class<?> clz){
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
            toColumnDefinition(columnName, ReflectionHelper.getFieldType(clz,columnName), str);
        }
        str.append(")");
        return str.toString();
    }



    private String toColumnDefinition(String key, Class<?> clz, StringBuffer str) {
        str.append("\""+key+"\"");
        str.append(' ');
        str.append(getDBDataType(clz));
        return str.toString();
    }

    private String getDBDataType(Class<?> clz) {
        switch(getDatabaseProductName()){
            case "Oracle" : return getOracleDataType(clz);
            default: return null;
        }

    }

    private String getOracleDataType(Class<?> clz) {
        String className = clz.getSimpleName();
        return javaToOracleDataTypeMapping.getOrDefault(className,"VARCHAR(4000)");
    }

    public <T extends DataSet> void save(T object) throws  SQLException {
        dropeTable(object);
        createTable(object);
        TExecutor executor = new TExecutor(getConnection());
        executor.execUpdate(createInsertQuery(object.getClass()),statement -> {
                        int index=1;
                        for(String fieldName : ReflectionHelper.getFieldNames(object.getClass())){
                            Class fieldType =  ReflectionHelper.getFieldType(object.getClass(),fieldName);
                            if(fieldType.equals(Byte.class)){
                                Byte obj = (Byte)ReflectionHelper.getFieldValue(object,fieldName);
                                if(obj == null){
                                    statement.setNull(index, Types.VARBINARY);
                                } else {
                                    statement.setByte(index,obj);
                                }
                            } else if(fieldType.equals(Boolean.class)){
                                Boolean obj = (Boolean)ReflectionHelper.getFieldValue(object,fieldName);
                                if(obj == null){
                                    statement.setNull(index, Types.BOOLEAN);
                                } else {
                                    statement.setBoolean(index,obj);
                                }
                            } else if(fieldType.equals(Short.class)){
                                Short obj = (Short)ReflectionHelper.getFieldValue(object,fieldName);
                                if(obj == null){
                                    statement.setNull(index, Types.SMALLINT);
                                } else {
                                    statement.setShort(index,obj);
                                }
                            } else if(fieldType.equals(Integer.class)){
                                Integer obj = (Integer)ReflectionHelper.getFieldValue(object,fieldName) ;
                                if(obj == null){
                                    statement.setNull(index, Types.INTEGER);
                                } else{
                                    statement.setInt(index,obj);
                                }
                            } else if(fieldType.equals(Long.class)){
                                Long obj = (Long)ReflectionHelper.getFieldValue(object,fieldName);
                                if(obj == null){
                                    statement.setNull(index, Types.BIGINT);
                                } else {
                                    statement.setLong(index,obj);
                                }
                            } else if(fieldType.equals(Float.class)){
                                Float obj = (Float)ReflectionHelper.getFieldValue(object,fieldName);
                                if(obj == null){
                                    statement.setNull(index, Types.FLOAT);
                                } else {
                                    statement.setFloat(index,obj);
                                }
                            } else if(fieldType.equals(Double.class)){
                                Double obj = (Double)ReflectionHelper.getFieldValue(object,fieldName);
                                if(obj == null){
                                    statement.setNull(index, Types.DOUBLE);
                                } else {
                                    statement.setDouble(index,obj);
                                }
                            } else if(fieldType.equals(Character.class) || fieldType.equals(String.class)){
                                statement.setString(index,(String)ReflectionHelper.getFieldValue(object,fieldName));
                            } else {
                                statement.setObject(index,ReflectionHelper.getFieldValue(object,fieldName));
                            }
                            index++;
                        }
                        statement.execute();
                    });


    }

    public <T extends DataSet> T load(long id, Class<T> clz) throws SQLException {
        TExecutor executor = new TExecutor(getConnection());
        return executor.execQuery(createSelectQuery(clz,id), result ->{
            if(result.next()){
                T obj = ReflectionHelper.instantiate(clz,result.getLong("ID"));
                for(String fieldName : ReflectionHelper.getFieldNames(clz)){
                    Class fieldType = ReflectionHelper.getFieldType(obj.getClass(),fieldName);
                    if(fieldType.equals(Byte.class) || fieldType.equals(byte.class)){
                        ReflectionHelper.setFieldValue(obj,fieldName,result.getByte(fieldName));
                    } else if(fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)){
                        ReflectionHelper.setFieldValue(obj,fieldName,result.getBoolean(fieldName));
                    } else if(fieldType.equals(Short.class) || fieldType.equals(short.class)){
                        ReflectionHelper.setFieldValue(obj,fieldName,result.getShort(fieldName));
                    } else if(fieldType.equals(Integer.class) || fieldType.equals(int.class)){
                        ReflectionHelper.setFieldValue(obj,fieldName,result.getInt(fieldName));
                    } else if(fieldType.equals(Long.class) || fieldType.equals(long.class)){
                        ReflectionHelper.setFieldValue(obj,fieldName,result.getLong(fieldName));
                    } else if(fieldType.equals(Float.class) || fieldType.equals(float.class)){
                        ReflectionHelper.setFieldValue(obj,fieldName,result.getFloat(fieldName));
                    } else if(fieldType.equals(Double.class) || fieldType.equals(double.class)){
                        ReflectionHelper.setFieldValue(obj,fieldName,result.getDouble(fieldName));
                    } else if(fieldType.equals(Character.class) || fieldType.equals(char.class)){
                        ReflectionHelper.setFieldValue(obj,fieldName,(Character)result.getObject(fieldName));
                    } else if(fieldType.equals(String.class)){
                        ReflectionHelper.setFieldValue(obj,fieldName,result.getString(fieldName));
                    } else {
                        ReflectionHelper.setFieldValue(obj,fieldName,result.getObject(fieldName));
                    }

                }
                return obj;
            } else {
                return null;
            }
        });
    }

    private String createSelectQuery(Class<?> clz,Long id){
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
        str.append("id="+id);
        return str.toString();
    }

    private String createInsertQuery(Class<?> clz){
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
}
