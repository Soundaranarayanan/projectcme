package Controller;

import java.util.ArrayList;
import java.util.Scanner;

import Model.Course;
import Model.Database;
import Model.Department;
import Model.Employee;
import Model.Operation;
import Model.Class;

public class CreateCourse implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        Course c = new Course();
        int ID = generateNextID(database);
        c.setID(ID);

        // Get Course Name
        System.out.println("Enter Course Name: ");
        scanner.nextLine();  // Consume any leftover newline
        c.setName(scanner.nextLine());

        // Get Class ID
        int classID = getValidID(scanner, database, "Enter Class ID (-1 to show all Classes): ", new ReadClasses());
        c.setCurrentClass(new Class(classID, database));

        // Get Course Description
        System.out.println("Enter Course Description: ");
        c.setDescription(scanner.nextLine());

        // Get Limit (with validation)
        int limit;
        do {
            System.out.println("Enter Course Limit (must be >= 1): ");
            while (!scanner.hasNextInt()) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                scanner.next();  // Discard invalid input
            }
            limit = scanner.nextInt();
            scanner.nextLine();  // Consume leftover newline
        } while (limit < 1);
        c.setLimit(limit);

        // Get Professor ID
        int employeeID = getValidID(scanner, database, "Enter Professor ID (-1 to show all Employees): ", new ReadEmployee());
        c.setProf(new Employee(employeeID, database));

        // Get Department ID
        int deptID = getValidID(scanner, database, "Enter Department ID (-1 to show all Departments): ", new ReadDepartments());
        c.setDept(new Department(deptID, database));

        // Create the Course
        c.create(database);
    }

    // Generates the next available course ID
    private int generateNextID(Database database) {
        ArrayList<Course> courses = new ReadCourses().getAllCourses(database);
        return courses.isEmpty() ? 1 : courses.get(courses.size() - 1).getID() + 1;
    }

    // Handles entity selection for Class, Employee, and Department
    private int getValidID(Scanner scanner, Database database, String prompt, Operation displayOperation) {
        int ID;
        do {
            System.out.println(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                scanner.next();  // Discard invalid input
            }
            ID = scanner.nextInt();
            scanner.nextLine();  // Consume leftover newline
            if (ID == -1) {
                displayOperation.oper(database, scanner);
            }
        } while (ID == -1);
        return ID;
    }
}
