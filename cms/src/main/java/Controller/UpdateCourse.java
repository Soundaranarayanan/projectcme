package Controller;

import java.util.Scanner;

import Model.Course;
import Model.Database;
import Model.Department;
import Model.Employee;
import Model.Operation;
import Model.Class;

public class UpdateCourse implements Operation {

    @Override
    public void oper(Database database, Scanner scanner, int id) {
        System.out.println("Enter Course ID (-1 to show all): ");
        int ID = scanner.nextInt();

        System.out.println("Enter Course ID (-1 to show all): ");
         ID = scanner.nextInt();

        // Loop until a valid (non-negative) Course ID is entered
        while (ID < 0) {
            // If the user enters -1, show all courses
            if (ID == -1) {
                new ReadCourses().oper(database, scanner, id);
            }

            // Prompt the user again for a Course ID
            System.out.println("Enter Course ID (-1 to show all): ");
            ID = scanner.nextInt();
        }

        // Validate if the course exists
        Course c = new Course(ID, database);
     

        scanner.nextLine(); // Consume leftover newline
        System.out.println("Enter Course Name (-1 to keep \"" + c.getName() + "\"): ");
        String name = scanner.nextLine();
        if (!name.equals("-1")) c.setName(name);

        // Class Update
        System.out.println("Enter Class ID (-1 to keep, -2 to show all): ");
        int classID = scanner.nextInt();
        if (classID == -2) {
            new ReadClasses().oper(database, scanner, id);
            System.out.println("Enter Class ID (-1 to keep): ");
            classID = scanner.nextInt();
        }
        if (classID != -1) {
            Class newClass = new Class(classID, database);
            if (newClass.getName() != null) {
                c.setCurrentClass(newClass);
            } else {
                System.out.println("❌ Class with ID " + classID + " does not exist.");
            }
        }

        scanner.nextLine(); // Consume newline

        // Description Update
        System.out.println("Enter Description (-1 to keep \"" + c.getDescription() + "\"): ");
        String description = scanner.nextLine();
        if (!description.equals("-1")) c.setDescription(description);

        // Limit Update
        System.out.println("Enter Course Limit (-1 to keep " + c.getLimit() + "): ");
        int limit = scanner.nextInt();
        if (limit != -1) c.setLimit(limit);

        scanner.nextLine(); // Consume newline

        // Professor Update
        System.out.println("Enter Prof ID (-1 to keep, -2 to show all): ");
        int profID = scanner.nextInt();
        if (profID == -2) {
            new ReadEmployee().oper(database, scanner, id);
            System.out.println("Enter Prof ID (-1 to keep): ");
            profID = scanner.nextInt();
        }
        if (profID != -1) {
            Employee newProf = new Employee(profID, database);
            if (newProf.getFirstName() != null) {
                c.setProf(newProf);
            } else {
                System.out.println("❌ Professor with ID " + profID + " does not exist.");
            }
        }

        scanner.nextLine(); // Consume newline

        // Department Update
        System.out.println("Enter Department ID (-1 to keep, -2 to show all): ");
        int deptID = scanner.nextInt();
        if (deptID == -2) {
            new ReadDepartments().oper(database, scanner, id);
            System.out.println("Enter Department ID (-1 to keep): ");
            deptID = scanner.nextInt();
        }
        if (deptID != -1) {
            Department newDept = new Department(deptID, database);
            if (newDept.getName() != null) {
                c.setDept(newDept);
            } else {
                System.out.println("❌ Department with ID " + deptID + " does not exist.");
            }
        }

        // Update the course in the database
        c.update(database);
        System.out.println("✅ Course updated successfully.");
    }
}