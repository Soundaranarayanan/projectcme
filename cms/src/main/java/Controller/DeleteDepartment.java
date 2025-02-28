package Controller;

import java.util.Scanner;
import Model.Database;
import Model.Department;
import Model.Operation;

public class DeleteDepartment implements Operation {

    private static final int SHOW_ALL_DEPARTMENTS = -1;

    @Override
    public void oper(Database database, Scanner scanner, int id) {
        int departmentId = promptForDepartmentId(scanner);
        while (departmentId == SHOW_ALL_DEPARTMENTS) {
            new ReadDepartments().oper(database, scanner, id);
            departmentId = promptForDepartmentId(scanner);
        }

        Department department = new Department(departmentId, database);

        if (department.getName() == null) {
            System.out.println("❌ No department found with ID: " + departmentId);
            return;
        }

        if (department.delete(database)) {
            System.out.println("✅ Department deleted successfully.");
        } else {
            System.out.println("❌ Department deletion failed.");
        }
    }

    private int promptForDepartmentId(Scanner scanner) {
        System.out.println("Enter Department ID (-1 to Show All Departments): ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return id;
    }
}