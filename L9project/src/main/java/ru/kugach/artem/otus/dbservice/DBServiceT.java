package ru.kugach.artem.otus.dbservice;

import ru.kugach.artem.otus.base.DataSet;
import ru.kugach.artem.otus.executor.TExecutor;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class DBServiceT extends DBServiceConnection {

    private void execDDL(String query) throws SQLException {
        TExecutor executor = new TExecutor(getConnection());
        getConnection().setAutoCommit(false);
        try{
            try{
                executor.execUpdate(query);
            } catch (java.sql.SQLSyntaxErrorException e){
                System.out.println(query+"; "+e.getMessage());
            }
            System.out.println("Exec DDL: "+query+";");
            System.out.println("Commit;");
            getConnection().commit();
        } catch (SQLException e){
            getConnection().rollback();
        }
    }


    private <T extends DataSet> void createTable(T object) throws SQLException {
        execDDL(SqlQueryHelper.createTableDDL(object.getClass(),getDatabaseProductName()));
        execDDL(SqlQueryHelper.createSequenceDDL(object.getClass(),getDatabaseProductName()));
        execDDL(SqlQueryHelper.createTriggerDDL(object.getClass(),getDatabaseProductName()));
    }

    private <T extends DataSet> void dropTable(T object) throws SQLException {
        execDDL(SqlQueryHelper.dropTableDDL(object.getClass()));
        execDDL(SqlQueryHelper.dropSequenceDDL(object.getClass()));
    }

    public <T extends DataSet> void save(T object) throws  SQLException {
        dropTable(object);
        createTable(object);
        TExecutor executor = new TExecutor(getConnection());
        getConnection().setAutoCommit(false);
        try{
            executor.execUpdate(SqlQueryHelper.createInsertQuery(object.getClass()),statement -> {
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
            System.out.println("Save: "+object.toString()+";");
            System.out.println("Commit;");
            getConnection().commit();
        } catch (SQLException e){
            getConnection().rollback();
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clz) throws SQLException {
        TExecutor executor = new TExecutor(getConnection());
        return executor.execSimpleSelect(SqlQueryHelper.createSelectQuery(clz),id, result ->{
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
}
