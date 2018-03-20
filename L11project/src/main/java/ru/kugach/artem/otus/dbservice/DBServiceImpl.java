package ru.kugach.artem.otus.dbservice;

import org.hibernate.Session;
import ru.kugach.artem.otus.base.DBService;
import ru.kugach.artem.otus.base.datasets.DataSet;
import ru.kugach.artem.otus.base.datasets.StudentDataSet;
import ru.kugach.artem.otus.cache.CacheEngineImpl;
import ru.kugach.artem.otus.cache.MyElement;
import ru.kugach.artem.otus.dbservice.dao.DAO;
import ru.kugach.artem.otus.dbservice.dao.DefaultDAOImpl;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DBServiceImpl implements DBService {

    private static final Map<Class, Class> dataSetToDAO;
    CacheEngineImpl cache;

    static{
        Map<Class<?>,Class<?>> map = new HashMap<>();
        map.put(StudentDataSet.class,DefaultDAOImpl.class);
        dataSetToDAO = Collections.unmodifiableMap(map);
    }

    private final Connection connection;

    public DBServiceImpl(CacheEngineImpl cacheEngine){
        connection = ConnectionHelper.getConnection();
        cache = cacheEngine;
    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + connection.getMetaData().getURL() + "\n" +
                    "DB name: " + connection.getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + connection.getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + connection.getMetaData().getDriverName()+ "\n" +
                    "Driver version: " +connection.getMetaData().getDriverVersion();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public Session getSession() {
        return null;
    }

    @Override
    public void close() throws Exception {
        connection.close();
        cache.dispose();
        System.out.println("Connection closed.");
    }

    private Connection getConnection() {
        return connection;
    }

    @Override
    public <T extends DataSet, S extends DAO> void save(T object){
        S dao = (S) ReflectionHelper.instantiate((Class<? extends DAO>)dataSetToDAO.get(object.getClass()));
        dao.setConnection(connection);
        dao.save(object);
        cache.put(new MyElement<>(
                object.getId()+dao.getTableName(object.getClass()),
                object)
        );
    }

    public <T extends DataSet, S extends DAO> T load(long id, Class<T> clz){
        S dao = (S) ReflectionHelper.instantiate(dataSetToDAO.get(clz));
        MyElement<String, T> element = cache.get(id+dao.getTableName(clz));
        T object = element == null ?  null : element.getValue();
        if(object!= null) {
            return object;
        }
        dao.setConnection(connection);
        object = dao.load(id, clz);
        return object;
    }
}
