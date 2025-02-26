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
    public void oper(Database database, Scanner scanner) {
        System.out.println("Enter Course ID (-1 to show all): ");
        int ID = scanner.nextInt();

        while (ID < 0) {
            new ReadCourses().oper(database, scanner);
            System.out.println("Enter Course ID (-1 to show all): ");
            ID = scanner.nextInt();
        }

        // Validate if the course exists
        Course c = new Course(ID, database);
        if (c.getName() == null) {
            System.out.println("❌ Course with ID " + ID + " does not exist.");
            return;
        }

        scanner.nextLine(); // Consume leftover newline
        System.out.println("Enter Course Name (-1 to keep \"" + c.getName() + "\"): ");
        String name = scanner.nextLine();
        if (!name.equals("-1")) c.setName(name);

        // Class Update
        System.out.println("Enter Class ID (-1 to keep, -2 to show all): ");
        int classID = scanner.nextInt();
        if (classID == -2) {
            new ReadClasses().oper(database, scanner);
            System.out.println("Enter Class ID (-1 to keep): ");
            classID = scanner.nextInt();
        }
        if (classID != -1) c.setCurrentClass(new Class(classID, database));

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
            new ReadEmployee().oper(database, scanner);
            System.out.println("Enter Prof ID (-1 to keep): ");
            profID = scanner.nextInt();
        }
        if (profID != -1) c.setProf(new Employee(profID, database));

        scanner.nextLine(); // Consume newline

        // Department Update
        System.out.println("Enter Department ID (-1 to keep, -2 to show all): ");
        int deptID = scanner.nextInt();
        if (deptID == -2) {
            new ReadDepartments().oper(database, scanner);
            System.out.println("Enter Department ID (-1 to keep): ");
            deptID = scanner.nextInt();
        }
        if (deptID != -1) c.setDept(new Department(deptID, database));

        // Update the course in the database
        c.update(database);
        System.out.println("✅ Course updated successfully.");
    }
}
