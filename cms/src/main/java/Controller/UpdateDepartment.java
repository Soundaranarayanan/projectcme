package Controller;

import java.util.Scanner;
import Model.Database;
import Model.Department;
import Model.Operation;

public class UpdateDepartment implements Operation {

    private static final String KEEP_CURRENT_VALUE = "-1";

    @Override
    public void oper(Database database, Scanner scanner, int id) {
        int departmentId = promptForDepartmentId(scanner);
        while (departmentId < 0) {
            new ReadDepartments().oper(database, scanner, id);
            departmentId = promptForDepartmentId(scanner);
        }

        Department department = new Department(departmentId, database);

        if (department.getName() == null) {
            System.out.println("⚠️ Department with ID " + departmentId + " not found.");
            return;
        }

        String newName = promptForDepartmentName(scanner, department.getName());
        if (!newName.equals(KEEP_CURRENT_VALUE)) {
            department.setName(newName);
            department.update(database);
        }
    }

    private int promptForDepartmentId(Scanner scanner) {
        System.out.println("Enter the Department ID (-1 to Show all Departments): ");
        return scanner.nextInt();
    }

    private String promptForDepartmentName(Scanner scanner, String currentName) {
        System.out.println("Enter Department Name (-1 to Keep [" + currentName + "]): ");
        return scanner.nextLine().trim();
    }
}