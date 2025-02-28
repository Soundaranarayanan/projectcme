package Controller;

import java.util.Scanner;
import Model.Database;
import Model.Operation;
import Model.Class; // ✅ Import Class

public class DeleteClass implements Operation {

    @Override
    public void oper(Database database, Scanner scanner,int id) {
        System.out.println("Enter Class ID (-1 to Show all Classes): ");
        int ID = scanner.nextInt();
        scanner.nextLine(); // ✅ Fix: Consume newline

        while (ID < 0) {
            new ReadClasses().oper(database, scanner, id);
            System.out.println("Enter Class ID (-1 to Show all Classes): ");
            ID = scanner.nextInt();
            scanner.nextLine(); // ✅ Fix: Consume newline again
        }

        Model.Class c = new Model.Class(ID, database); // ✅ Correct Instantiation
        c.delete(database); // ✅ Ensure delete() exists
    }
}