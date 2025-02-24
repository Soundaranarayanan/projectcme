package Controller;

import java.util.Scanner;
import Model.Database;
import Model.Department;
import Model.Operation;

public class UpdateDepartment implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        System.out.println("Enter the Department ID (-1 to Show all Departments): ");
        int ID = scanner.nextInt();
        scanner.nextLine(); // ✅ Fix for nextInt() issue

        while (ID < 0) {
            new ReadDepartments().oper(database, scanner);
            System.out.println("Enter the Department ID (-1 to Show all Departments): ");
            ID = scanner.nextInt();
            scanner.nextLine(); // ✅ Fix again
        }

        Department department = new Department(ID, database);

        if (department.getName() == null) {
            System.out.println("⚠️ Department with ID " + ID + " not found.");
            return;
        }

        System.out.println("Enter Department Name (-1 to Keep [" + department.getName() + "]): ");
        String name = scanner.nextLine().trim();

        if (!name.equals("-1")) {
            department.setName(name);
            department.update(database);
        } 
    }
}
