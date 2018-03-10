package ru.kugach.artem.otus.executor;

import java.sql.*;

public class TExecutor {
    private final Connection connection;

    public TExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execSimpleSelect(String query, long id, TResultHandler<T> resultHandler) throws SQLException {
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            System.out.println("Load: "+query);
            stmt.setLong(1,id);
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            T object = resultHandler.handle(result);
            result.close();
            return object;
        }
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
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
