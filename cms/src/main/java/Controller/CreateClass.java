package Controller;

import java.util.ArrayList;
import java.util.Scanner;
import Model.Database;
import Model.Operation;
import Model.Class;

public class CreateClass implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        System.out.println("Enter the Class Name:");
        scanner.nextLine();  // Consume any leftover newline
        String name = scanner.nextLine();  // Read full class name including spaces

        ArrayList<Class> classes = new ReadClasses().getAllClasses(database);
        int ID = (classes.isEmpty()) ? 1 : classes.get(classes.size() - 1).getID() + 1;

        Class c = new Class();
        c.setID(ID);
        c.setName(name);

        // ✅ Ensure `create()` method in `Class` uses PreparedStatement
        c.create(database);
    }
}
