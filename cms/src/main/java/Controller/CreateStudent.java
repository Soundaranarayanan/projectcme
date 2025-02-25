package Controller;

import java.util.ArrayList;
import java.util.Scanner;
import Model.Class;
import Model.Database;
import Model.Operation;
import Model.Student;

public class CreateStudent implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        Student s = new Student();
        ArrayList<Student> students = new ReadStudents().getAllStudents(database);
        int ID = 0;
        if(students.size()!=0) {
        	ID = students.get(students.size()-1).getID()+1;
        }
        
        s.setID(ID);

        System.out.println("Enter Student ID: ");
        int studentID = scanner.nextInt();
        s.setID(studentID);

        scanner.nextLine(); // ✅ Fix scanner buffer issue

        System.out.println("Enter First Name:");
        s.setFirstName(scanner.nextLine());

        System.out.println("Enter Last Name:");
        s.setLastName(scanner.nextLine());

        System.out.println("Enter Email:");
        s.setEmail(scanner.nextLine());

        System.out.println("Enter Phone Number:");
        s.setPhoneNumber(scanner.nextLine());

        System.out.println("Enter Birth Date (YYYY-MM-DD):");
        s.setBirthDate(scanner.nextLine());

        // ✅ Improved Class Selection
        int classID;
        do {
            System.out.println("Enter the Class ID (-1 to show all classes): ");
            classID = scanner.nextInt();

            if (classID == -1) {
                new ReadClasses().oper(database, scanner);
            }

        } while (classID == -1);

        s.setClass(new Class(classID, database));

        scanner.nextLine(); // ✅ Fix scanner issue before password input

        // ✅ Secure Password Input
        String password, confirmPassword;
        do {
            System.out.println("Enter Password: ");
            password = scanner.nextLine();

            System.out.println("Confirm Password: ");
            confirmPassword = scanner.nextLine();

            if (!password.equals(confirmPassword)) {
                System.out.println("❌ Passwords do not match. Try again!");
            }
        } while (!password.equals(confirmPassword));

        s.setPassword(password);
        
        // ✅ Create Student in Database
        s.create(database);
    }
}
