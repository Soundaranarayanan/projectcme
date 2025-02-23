package Model;

import java.sql.*;

public class Database {
    private String user = "system";
    private String pass = "karan";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private Connection connection;
    private Statement statement;

    public Database() {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            System.out.println("Database Connection Error: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
 public Statement getStatement() {
        return statement;
    }

  
}
