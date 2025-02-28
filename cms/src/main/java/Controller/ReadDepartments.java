package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Database;
import Model.Department;
import Model.Operation;

public class ReadDepartments implements Operation {

    @Override
    public void oper(Database database, Scanner scanner, int id) {
        ArrayList<Department> departments = getAllDepartments(database);

        if (departments.isEmpty()) {
            System.out.println("⚠️ No departments found.");
            return;
        }

        System.out.println("\n📋 List of Departments:");
        System.out.println("-----------------------------");
        for (Department d : departments) {
            System.out.printf("ID: %d | Name: %s%n", d.getID(), 
                              d.getName() != null ? d.getName() : "N/A");
        }
    }

    public ArrayList<Department> getAllDepartments(Database database) {
        ArrayList<Department> departments = new ArrayList<>();
        String select = "SELECT ID, NAME FROM Expleo.DEPARTMENTS"; // ✅ No semicolon

        if (database.getConnection() == null) {
            System.out.println("❌ Error: Database connection is null.");
            return departments; // Return empty list
        }

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(select);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Department d = new Department();
                d.setID(rs.getInt("ID"));
                d.setName(rs.getString("NAME")); // ✅ Fixed case sensitivity
                departments.add(d);
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
        return departments;
    }
}