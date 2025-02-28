package Controller;

import java.sql.*;
import java.util.Scanner;
import Model.Database;
import Model.Employee;
import Model.Operation;
import Model.Department;

public class CreateEmployee implements Operation {

    @Override
    public void oper(Database database, Scanner scanner, int id) {
        try {
            database.getStatement().execute("ALTER SESSION SET CURRENT_SCHEMA = Expleo");

            Employee e = new Employee();

            System.out.printf("%-25s: ", "Enter First Name");
            e.setFirstName(scanner.next());

            System.out.printf("%-25s: ", "Enter Last Name");
            e.setLastName(scanner.next());

            System.out.printf("%-25s: ", "Enter Email");
            e.setEmail(scanner.next());

            System.out.printf("%-25s: ", "Enter Phone Number");
            e.setPhoneNumber(scanner.next());

            System.out.printf("%-25s: ", "Enter Birth Date (YYYY-MM-DD)");
            e.setBirthDate(scanner.next());

            System.out.printf("%-25s: ", "Enter Salary");
            e.setSalary(scanner.nextDouble());

            System.out.printf("%-25s: ", "Enter Department (-1 to Show all Departments)");
            int deptID = scanner.nextInt();

            while (deptID < 0) {
                new ReadDepartments().oper(database, scanner,id);
                System.out.printf("%-25s: ", "Enter Department (-1 to Show all Departments)");
                deptID = scanner.nextInt();
            }

            if (deptID > 0) {
                e.setDepartment(new Department(deptID, database)); // ✅ Assign department
            }

            System.out.printf("%-25s: ", "Enter Password");
            String password = scanner.next();

            System.out.printf("%-25s: ", "Confirm Password");
            String confirmPassword = scanner.next();

            while (!confirmPassword.equals(password)) {
                System.out.println("⚠️ Passwords do not match. Try again.");
                System.out.printf("%-25s: ", "Enter Password");
                password = scanner.next();
                System.out.printf("%-25s: ", "Confirm Password");
                confirmPassword = scanner.next();
            }

            e.setPassword(password);

            // ✅ Efficient way to get the next ID
            int newID = getNextEmployeeID(database);
            e.setID(newID);

            e.create(database);
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }

    // ✅ Efficient method to get next available ID
    private int getNextEmployeeID(Database database) {
        String query = "SELECT COALESCE(MAX(ID), 0) + 1 FROM Expleo.EMPLOYEES";
        try (Statement stmt = database.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching next ID: " + e.getMessage());
        }
        return 1; // Default to 1 if there's an issue
    }
}