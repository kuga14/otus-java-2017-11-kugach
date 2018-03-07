package ru.kugach.artem.otus.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

class ConnectionHelper {

    static Connection getConnection() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@" +       //db type
                    "localhost:" +               //host name
                    "1521:" +                    //port
                    "xe";             //SID
            String user = "hr";
            String password = "userhr";
            Locale.setDefault(Locale.ENGLISH);
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
