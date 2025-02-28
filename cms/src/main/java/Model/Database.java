package Model;

import java.sql.*;

public class Database {
    private final String user = "system";
    private final String pass = "karan";
    private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    
    // Constructor calls connect() to ensure the initial connection
    public Database() {
        connect();
    }

    // Method to establish/reconnect connection if needed
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
         // System.out.println("✅ Database connected successfully.");
        } catch (SQLException e) {
            System.err.println("❌ Database Connection Error: " + e.getMessage());
        }
        return conn;
    }

    // Returns a fresh connection every time
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = connect();  // Get a new connection
            if (conn == null || conn.isClosed()) {
                System.err.println("❌ Failed to get a database connection.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Connection Check Error: " + e.getMessage());
        }
        return conn;
    }

    // Create a fresh statement every time
    public Statement getStatement() {
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            System.err.println("❌ Error creating statement: " + e.getMessage());
        }
        return stmt;
    }

    // Close the provided connection
    public void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔒 Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing connection: " + e.getMessage());
        }
    }

    // Close the provided statement
    public void close(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing statement: " + e.getMessage());
        }
    }

    // Close the provided result set
    public void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing result set: " + e.getMessage());
        }
    }
}