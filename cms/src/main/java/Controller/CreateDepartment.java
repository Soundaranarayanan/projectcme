package Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Database;
import Model.Department;
import Model.Operation;

public class CreateDepartment implements Operation {

    @Override
    public void oper(Database database, Scanner scanner,int id) {
        System.out.println("Enter Department Name: ");
        String name = scanner.nextLine();

        int ID = 1; 
        ArrayList<Department> departments = new ReadDepartments().getAllDepartments(database);

        if (!departments.isEmpty()) {
            ID = departments.get(departments.size() - 1).getID() + 1; // Auto-increment ID
        }

        
        String insertQuery = "INSERT INTO Expleo.DEPARTMENTS (ID, NAME) VALUES (?, ?)";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(insertQuery)) {
            pstmt.setInt(1, ID);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("✅ Department created successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}