import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL {
    private static final String URL = "jdbc:oracle:thin:@oracle12c.cs.torontomu.ca:1521:orcl12c";
    private static final String USERNAME = "kgalinga";
    private static final String PASSWORD = "01167503";
    private static Connection connection;
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return connection;
    }
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
