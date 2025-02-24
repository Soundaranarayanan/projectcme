package Controller;

import java.util.Scanner;
import Model.Database;
import Model.Department;
import Model.Operation;

public class DeleteDepartment implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        System.out.println("Enter Department ID (-1 to Show All Departments): ");
        int ID = scanner.nextInt();
        
        while (ID < 0) {
            new ReadDepartments().oper(database, scanner);
            System.out.println("Enter Department ID (-1 to Show All Departments): ");
            ID = scanner.nextInt();
        }

        Department d = new Department(ID, database); 
        
        if (d.getName() == null) { // ✅ Check if department exists before deleting
            System.out.println("❌ No department found with ID: " + ID);
            return;
        }

        if (d.delete(database)) {
            System.out.println("✅ Department deleted successfully.");
        } else {
            System.out.println("❌ Department deletion failed.");
        }
    }
}
