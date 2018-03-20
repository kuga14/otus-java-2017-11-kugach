package ru.kugach.artem.otus.dbservice.dao;

import org.hibernate.Session;
import ru.kugach.artem.otus.base.datasets.DataSet;

import java.sql.Connection;
import java.sql.SQLException;

public interface DAO {

    //For JDBCImpl
    void setConnection(Connection connection);

    String getTableName(Class<?> clz);

    <T extends DataSet> void save(T object);

    <T extends DataSet> T load(long id, Class<T> clz);


}
