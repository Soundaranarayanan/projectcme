package Controller;

import java.io.File;
import java.io.FileNotFoundException;
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

        String[] loginDetails = readLoginDetails(args, scanner);
        if (loginDetails == null) {
            System.out.println("Exiting program.");
            return;
        }

        int selected = Integer.parseInt(loginDetails[0]);
        String email = loginDetails[1];
        String password = loginDetails[2];

        login(selected, email, password, database, scanner);
    }

    private static String[] readLoginDetails(String[] args, Scanner scanner) {
        if (args.length == 3) {
            return args;  // Use command-line args if provided
        }

        File file = new File("inputt.txt");
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                if (fileScanner.hasNextInt()) {
                    int selected = fileScanner.nextInt();
                    String email = fileScanner.next();
                    String password = fileScanner.next();
                    return new String[]{String.valueOf(selected), email, password};
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error reading input file: " + e.getMessage());
            }
        }

        System.out.println("Input file not found. Running interactively.");
        System.out.println("Welcome to College Management System");
        System.out.println("1. Staff");
        System.out.println("2. Student");

        int selected = scanner.nextInt();
        System.out.print("Enter Email  : ");
        String email = scanner.next();
        System.out.print("Enter Password : ");
        String password = scanner.next();

        return new String[]{String.valueOf(selected), email, password};
    }

    private static void login(int selected, String email, String password, Database database, Scanner scanner) {
        boolean loggedIn = false;
        String selectQuery = selected == 1
                ? "SELECT ID, EMAIL, PASSWORD FROM Expleo.Employees WHERE EMAIL = ?"
                : "SELECT ID, EMAIL, PASSWORD FROM Expleo.Students WHERE EMAIL = ?";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(selectQuery)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (password.equals(rs.getString("PASSWORD"))) {
                    if (selected == 1) {
                        Employee e = new Employee(rs.getInt("ID"), database);
                        e.showList(database, scanner);  // Pass scanner here
                    } else {
                        Student s = new Student(rs.getInt("ID"), database);
                        s.showList(database, scanner);  // Pass scanner here
                    }
                    loggedIn = true;
                    break;
                }
            }

            if (!loggedIn) {
                System.out.println("Wrong Password or Email ");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
