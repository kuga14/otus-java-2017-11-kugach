package ru.kugach.artem.otus.connection;

import ru.kugach.artem.otus.base.DBService;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceConnection implements DBService {

    private final Connection connection;

    public DBServiceConnection(){
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                    "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + getConnection().getMetaData().getDriverName()+ "\n" +
                    "Driver version: " +getConnection().getMetaData().getDriverVersion();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed.");
    }

    protected Connection getConnection() {
        return connection;
    }

    public String getDatabaseProductName(){
        try{
            return getConnection().getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
