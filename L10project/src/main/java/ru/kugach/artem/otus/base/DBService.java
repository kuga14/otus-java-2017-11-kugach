package ru.kugach.artem.otus.base;

import org.hibernate.Session;
import ru.kugach.artem.otus.base.datasets.DataSet;
import ru.kugach.artem.otus.dbservice.dao.DAO;

public interface DBService extends AutoCloseable {

    String getMetaData();

    //for HibernateImpl
    Session getSession();

    <T extends DataSet, S extends DAO> void save(T object);

    <T extends DataSet, S extends DAO> T load(long id, Class<T> clz);

}
