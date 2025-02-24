package Model;

import java.sql.*;

public class Database {
    private final String user = "system";
    private final String pass = "karan";
    private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private Connection connection;

    // Constructor to establish a connection
    public Database() {
        connect();
    }

    // Method to establish/reconnect connection if needed
    public void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("✅ Database connected successfully.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Connection Error: " + e.getMessage());
        }
    }

    // Ensures connection is always active before returning it
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            System.out.println("❌ Connection Check Error: " + e.getMessage());
        }
        return connection;
    }

    // Create a fresh statement every time
    public Statement getStatement() {
        try {
            return getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            System.out.println("❌ Error creating statement: " + e.getMessage());
            return null;
        }
    }

    // Properly close the connection when done
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error closing connection: " + e.getMessage());
        }
    }
}
