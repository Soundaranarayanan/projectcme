package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Model.Database;
import Model.Employee;
import Model.Student;

public class Login {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database database = new Database();
        System.out.println("Welcome to College Management System");
        System.out.println("1. Staff");
        System.out.println("2. Student");
        int selected = scanner.nextInt();

        System.out.println("Enter Email  : ");
        String email = scanner.next();
        System.out.println("Enter Password : ");
        String password = scanner.next();

        boolean loggedIn = false;
        String select;

        if (selected == 1) {
            select = "SELECT ID, EMAIL, PASSWORD FROM Expleo.Employees WHERE EMAIL = ?";
        } else {
            select = "SELECT ID, EMAIL, PASSWORD FROM Expleo.Students WHERE EMAIL = ?";
        }

        try {
            // Use PreparedStatement instead of Statement
            PreparedStatement pstmt = database.getConnection().prepareStatement(select);
            pstmt.setString(1, email); // Bind email parameter
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (password.equals(rs.getString("PASSWORD"))) {  // Case-sensitive comparison
                    if (selected == 1) {
                        Employee e = new Employee(rs.getInt("ID"), database);
                        e.showList(database, scanner);
                    } else {
                        Student e = new Student(rs.getInt("ID"), database);
                        e.showList(database, scanner);
                    }
                    loggedIn = true;
                    break;
                }
            }

            if (!loggedIn) {
                System.out.println("Wrong Password or Email ");
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
