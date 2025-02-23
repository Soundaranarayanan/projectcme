package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Database;
import Model.Department;
import Model.Operation;

public class ShowAllDepartments implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        ArrayList<Department> departments = getAllDepartments(database);

        if (departments.isEmpty()) {
            System.out.println("⚠️ No departments found.");
            return;
        }

        System.out.println("📋 List of Departments:");
        for (Department d : departments) {
            System.out.printf("ID: %d | Name: %s%n", d.getID(), d.getName());
        }
    }

    public ArrayList<Department> getAllDepartments(Database database) {
        ArrayList<Department> departments = new ArrayList<>();
        String select = "SELECT ID, NAME FROM Expleo.DEPARTMENTS"; // ✅ Removed trailing semicolon

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(select);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Department d = new Department();
                d.setID(rs.getInt("ID"));
                d.setName(rs.getString("NAME")); // ✅ Column names are case-sensitive in some DBs
                departments.add(d);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving departments: " + e.getMessage());
        }
        return departments;
    }
}
