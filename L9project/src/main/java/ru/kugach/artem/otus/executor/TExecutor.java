package ru.kugach.artem.otus.executor;

import java.sql.*;

public class TExecutor {
    private final Connection connection;

    public TExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException {
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    public void execUpdate(String update, ExecuteHandler prepare) {
        try {
            PreparedStatement stmt = getConnection().prepareStatement(update);
            prepare.accept(stmt);
            stmt.close();
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
