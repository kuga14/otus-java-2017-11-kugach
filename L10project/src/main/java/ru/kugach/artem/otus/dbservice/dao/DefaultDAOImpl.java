package ru.kugach.artem.otus.dbservice.dao;

import org.hibernate.Session;
import ru.kugach.artem.otus.base.datasets.DataSet;
import ru.kugach.artem.otus.dbservice.ReflectionHelper;
import ru.kugach.artem.otus.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class DefaultDAOImpl implements DAO {

    private Connection connection;

    public DefaultDAOImpl(){

    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void setSession(Session session) {

    }

    @Override
    public <T extends DataSet> void save(T object){
        Executor executor = new Executor(connection);
        try{
            connection.setAutoCommit(false);
            executor.execUpdate(createInsertQuery(object.getClass()), statement -> {
                int index=1;
                for(String fieldName : ReflectionHelper.getFieldNames(object.getClass())){
                    Class fieldType =  ReflectionHelper.getFieldType(object.getClass(),fieldName);
                    if(fieldType.equals(Byte.class)){
                        Byte obj = (Byte) ReflectionHelper.getFieldValue(object,fieldName);
                        if(obj == null){
                            statement.setNull(index, Types.VARBINARY);
                        } else {
                            statement.setByte(index,obj);
                        }
                    } else if(fieldType.equals(Boolean.class)){
                        Boolean obj = (Boolean) ReflectionHelper.getFieldValue(object,fieldName);
                        if(obj == null){
                            statement.setNull(index, Types.BOOLEAN);
                        } else {
                            statement.setBoolean(index,obj);
                        }
                    } else if(fieldType.equals(Short.class)){
                        Short obj = (Short) ReflectionHelper.getFieldValue(object,fieldName);
                        if(obj == null){
                            statement.setNull(index, Types.SMALLINT);
                        } else {
                            statement.setShort(index,obj);
                        }
                    } else if(fieldType.equals(Integer.class)){
                        Integer obj = (Integer) ReflectionHelper.getFieldValue(object,fieldName) ;
                        if(obj == null){
                            statement.setNull(index, Types.INTEGER);
                        } else{
                            statement.setInt(index,obj);
                        }
                    } else if(fieldType.equals(Long.class)){
                        Long obj = (Long) ReflectionHelper.getFieldValue(object,fieldName);
                        if(obj == null){
                            statement.setNull(index, Types.BIGINT);
                        } else {
                            statement.setLong(index,obj);
                        }
                    } else if(fieldType.equals(Float.class)){
                        Float obj = (Float) ReflectionHelper.getFieldValue(object,fieldName);
                        if(obj == null){
                            statement.setNull(index, Types.FLOAT);
                        } else {
                            statement.setFloat(index,obj);
                        }
                    } else if(fieldType.equals(Double.class)){
                        Double obj = (Double) ReflectionHelper.getFieldValue(object,fieldName);
                        if(obj == null){
                            statement.setNull(index, Types.DOUBLE);
                        } else {
                            statement.setDouble(index,obj);
                        }
                    } else if(fieldType.equals(Character.class) || fieldType.equals(String.class)){
                        statement.setString(index,(String) ReflectionHelper.getFieldValue(object,fieldName));
                    } else {
                        statement.setObject(index, ReflectionHelper.getFieldValue(object,fieldName));
                    }
                    index++;
                }
                statement.execute();
            });
            System.out.println("Save: "+object.toString()+";");
            System.out.println("Commit;");
            connection.commit();
        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clz){
        Executor executor = new Executor(connection);
        try {
            return executor.execSimpleSelect(createSelectQuery(clz),id, result ->{
                if(result.next()){
                    T obj = ReflectionHelper.instantiate(clz);
                    obj.setId(result.getLong("ID"));
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
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getQuotedTableName(Class<?> clz){
        return "\""+getTableName(clz)+"\"";
    }

    public static String getTableName(Class<?> clz){
        return clz.getSimpleName().replaceAll("DataSet","").trim().toUpperCase();
    }

    public static String createSelectQuery(Class<?> clz){
        StringBuffer str = new StringBuffer();
        str.append("SELECT ");
        str.append("\"id\"");
        for(String columnName : ReflectionHelper.getFieldNames(clz)){
            str.append(',');
            str.append("\""+columnName+"\"");
        }
        str.append(" FROM ");
        str.append(getQuotedTableName(clz));
        str.append(" WHERE ");
        str.append("\"id\"=?");
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
}
