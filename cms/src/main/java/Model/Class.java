package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Class {
    private int ID;
    private String name;

    public Class() {}

    // Constructor to load class details from the database
    public Class(int ID, Database database) {
        this.ID = ID;
        String selectQuery = "SELECT NAME FROM Expleo.CLASSES WHERE ID = ?";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(selectQuery)) {
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.name = rs.getString("NAME");
            } else {
                System.out.println("⚠️ No class found with ID: " + ID);
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
    }

    public Class(int ID) {
        this.ID = ID;
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void print() {
        System.out.println("ID: " + ID);
        System.out.println("Name: " + name);
        System.out.println("---------------------------");
    }

    // ✅ Corrected `create()` method
    public void create(Database database) {
        String insertQuery = "INSERT INTO Expleo.CLASSES (ID, NAME) VALUES (?, ?)";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(insertQuery)) {
            pstmt.setInt(1, this.ID);
            pstmt.setString(2, this.name);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Class inserted successfully.");
            } else {
                System.out.println("⚠️ No class was inserted.");
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
    }

    // ✅ Corrected `update()` method
    public void update(Database database) {
        String updateQuery = "UPDATE Expleo.CLASSES SET NAME = ? WHERE ID = ?";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(updateQuery)) {
            pstmt.setString(1, this.name);
            pstmt.setInt(2, this.ID);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Class updated successfully.");
            } else {
                System.out.println("⚠️ No class found with ID: " + this.ID);
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
    }

    // ✅ Fixed `delete()` method
    public void delete(Database database) {
        String deleteQuery = "DELETE FROM Expleo.CLASSES WHERE ID = ?";
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(deleteQuery)) {
            pstmt.setInt(1, this.ID);  // ✅ Correctly bind the ID

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Class deleted successfully.");
            } else {
                System.out.println("⚠️ No class found with ID: " + this.ID);
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
    }
}
