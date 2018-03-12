package ru.kugach.artem.otus.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.kugach.artem.otus.base.DBService;
import ru.kugach.artem.otus.base.datasets.*;
import ru.kugach.artem.otus.dbservice.dao.DAO;
import ru.kugach.artem.otus.dbservice.dao.DefaultHibernateDAOImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;

public class DBServiceHibernateImpl implements DBService {

    private static final Map<Class, Class> dataSetToDAO;

    static{
        Map<Class<?>,Class<?>> map = new HashMap<>();
        map.put(StudentDataSet.class,DefaultHibernateDAOImpl.class);
        map.put(StudentDataSetWithAddress.class,DefaultHibernateDAOImpl.class);
        map.put(StudentDataSetWithAddressPhones.class,DefaultHibernateDAOImpl.class);
        map.put(AddressDataSet.class,DefaultHibernateDAOImpl.class);
        map.put(PhoneDataSet.class,DefaultHibernateDAOImpl.class);
        dataSetToDAO = Collections.unmodifiableMap(map);
    }

    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.WARNING);

        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(StudentDataSet.class);
        configuration.addAnnotatedClass(StudentDataSetWithAddress.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(StudentDataSetWithAddressPhones.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        configuration.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
        configuration.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@localhost:1521:xe");
        configuration.setProperty("hibernate.connection.username", "hr");
        configuration.setProperty("hibernate.connection.password", "userhr");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    public DBServiceHibernateImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    @Override
    public String getMetaData() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public <T extends DataSet, S extends DAO> void save(T dataSet) {
        try (Session session = sessionFactory.openSession()) {
            S dao = (S) ReflectionHelper.instantiate(dataSetToDAO.get(dataSet.getClass()));
            dao.setSession(session);
            dao.save(dataSet);
        }
    }

    @Override
    public <T extends DataSet, S extends DAO> T load(long id, Class<T> clz) {
        return runInSession(session -> {
            S dao = (S) ReflectionHelper.instantiate(dataSetToDAO.get(clz));
            dao.setSession(session);
            return dao.load(id,clz);
        });
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }
}
