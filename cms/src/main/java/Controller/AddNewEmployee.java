package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import Model.Database;
import Model.Employee;
import Model.Operation;

public class AddNewEmployee implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        try {
            database.getStatement().execute("ALTER SESSION SET CURRENT_SCHEMA = Expleo");

            System.out.printf("%-25s: ", "Enter First Name");
            String firstName = scanner.next();

            System.out.printf("%-25s: ", "Enter Last Name");
            String lastName = scanner.next();

            System.out.printf("%-25s: ", "Enter Email");
            String email = scanner.next();

            System.out.printf("%-25s: ", "Enter Phone Number");
            String phoneNumber = scanner.next();

            System.out.printf("%-25s: ", "Enter Birth Date (YYYY-MM-DD)");
            String birthDate = scanner.next();

            System.out.printf("%-25s: ", "Enter Salary");
            double salary = scanner.nextDouble();

            System.out.printf("%-25s: ", "Enter Department (-1 to Show all Departments)");
            int deptID = scanner.nextInt();

            while (deptID < 0) {
                new ShowAllDepartments().oper(database, scanner);
                System.out.printf("%-25s: ", "Enter Department (-1 to Show all Departments)");
                deptID = scanner.nextInt();
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
            ArrayList<Employee> employees = new ShowAllEmployee().getAllEmployees(database);
            	int ID = 0;
            	if(employees.size()!=0) {
            		ID = employees.get(employees.size()-1).getID()+1;
            	}
        

            String insert = "INSERT INTO Expleo.EMPLOYEES " +
                    "(ID, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER, BIRTHDATE, SALARY, DEPARTMENT, PASSWORD) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = database.getConnection().prepareStatement(insert)) {
            	
                pstmt.setInt(1, ID);
                pstmt.setString(2, firstName);
                pstmt.setString(3, lastName);
                pstmt.setString(4, email);
                pstmt.setString(5, phoneNumber);
                pstmt.setString(6, birthDate);
                pstmt.setDouble(7, salary);
                pstmt.setInt(8, deptID);
                pstmt.setString(9, password);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.printf("\n✅ Employee added successfully!\n");
                    System.out.printf("---------------------------------------------------\n");
                    System.out.printf("%-15s: %d\n", "Employee ID", ID);
                    System.out.printf("%-15s: %s %s\n", "Name", firstName, lastName);
                    System.out.printf("%-15s: %s\n", "Email", email);
                    System.out.printf("%-15s: %s\n", "Phone", phoneNumber);
                    System.out.printf("%-15s: %s\n", "Birth Date", birthDate);
                    System.out.printf("%-15s: %.2f\n", "Salary", salary);
                    System.out.printf("%-15s: %d\n", "Department", deptID);
                    System.out.printf("---------------------------------------------------\n");
                }
            }
        } catch (SQLException e) {
            System.out.printf("❌ Database Error: %s\n", e.getMessage());
        }
    }
}
