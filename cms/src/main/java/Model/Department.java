package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Department {

    private int ID;
    private String name;

    public Department() {}

    public Department(int ID) {
        this.ID = ID;
    }

    // Fetch department details using ID
    public Department(int ID, Database database) {
        String select = "SELECT ID, NAME FROM Expleo.DEPARTMENTS WHERE ID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(select)) {

            pstmt.setInt(1, ID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    this.ID = rs.getInt("ID");
                    this.name = rs.getString("NAME");
                } else {
                    System.out.println("⚠️ No department found with ID: " + ID);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
    }

    // Getters & Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Create a new department (INSERT)
    public void create(Database database) {
        if (this.ID <= 0 || this.name == null || this.name.trim().isEmpty()) {
            System.out.println("⚠️ Invalid department data.");
            return;
        }

        String insert = "INSERT INTO Expleo.DEPARTMENTS (ID, NAME) VALUES (?, ?)";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insert)) {

            pstmt.setInt(1, this.ID);
            pstmt.setString(2, this.name);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Department created successfully.");
            } else {
                System.out.println("⚠️ Failed to create department.");
            }

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
    }

    // Update department name (UPDATE)
    public void update(Database database) {
        if (this.ID <= 0 || this.name == null || this.name.trim().isEmpty()) {
            System.out.println("⚠️ Invalid department data.");
            return;
        }

        String update = "UPDATE Expleo.DEPARTMENTS SET NAME = ? WHERE ID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(update)) {

            pstmt.setString(1, this.name);
            pstmt.setInt(2, this.ID);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Department updated successfully.");
            } else {
                System.out.println("⚠️ No department found with ID: " + this.ID);
            }

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
    }

    // Delete department (DELETE)
    public boolean delete(Database database) {
        if (this.ID <= 0) {
            System.out.println("⚠️ Invalid department ID.");
            return false;
        }

        String delete = "DELETE FROM Expleo.DEPARTMENTS WHERE ID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(delete)) {

            pstmt.setInt(1, this.ID);
            int rowsDeleted = pstmt.executeUpdate();

            return rowsDeleted > 0; // Return true if deletion was successful

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
            return false;
        }
    }
}