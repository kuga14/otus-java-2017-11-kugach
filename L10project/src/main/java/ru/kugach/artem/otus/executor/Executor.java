package ru.kugach.artem.otus.executor;

import java.sql.*;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execSimpleSelect(String query, long id, TResultHandler<T> resultHandler) throws SQLException {
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            System.out.println("Load: "+query);
            stmt.setLong(1,id);
            stmt.execute();
            T object;
            try(ResultSet result = stmt.getResultSet()){
                object = resultHandler.handle(result);

            }
            return object;
        }
    }

    public void execUpdate(String update, ExecuteHandler prepare) {
        try(PreparedStatement stmt = connection.prepareStatement(update)) {
            System.out.println("Insert: "+update);
            prepare.accept(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface ExecuteHandler {
        void accept(PreparedStatement statement) throws SQLException;
    }

    @FunctionalInterface
    public interface TResultHandler<T> {
        T handle(ResultSet resultSet) throws SQLException;
    }

    Connection getConnection() {
        return connection;
    }
}
