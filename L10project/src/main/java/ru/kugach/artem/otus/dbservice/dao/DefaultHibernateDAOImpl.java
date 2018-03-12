package ru.kugach.artem.otus.dbservice.dao;

import org.hibernate.Session;
import ru.kugach.artem.otus.base.datasets.DataSet;

import java.io.Serializable;
import java.sql.Connection;

public class DefaultHibernateDAOImpl implements DAO,Serializable{

    private Session session;



    public DefaultHibernateDAOImpl() {

    }

    @Override
    public void setConnection(Connection connection) {

    }

    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public <T extends DataSet> void save(T object) {
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clz) {
        return session.load(clz, id);
    }
}
