package Controller;

import java.util.Scanner;

import Model.Database;
import Model.Operation;

public class UpdateClass implements Operation {

	@Override
	public void oper(Database database, Scanner scanner) {
		// TODO Auto-generated method stub
		
		System.out.println("Enter Class ID (-1 to Show all Classes) : ");
		int ID = scanner.nextInt();
		scanner.nextLine(); // ✅ Fix: Consume newline left after nextInt()

		while (ID < 0) {
			new ReadClasses().oper(database, scanner);
			System.out.println("Enter Class ID (-1 to Show all Classes) : ");
			ID = scanner.nextInt();
			scanner.nextLine(); // ✅ Fix: Consume newline again
		}

		Model.Class c = new Model.Class(ID, database);

		// ✅ Fix: Removed unnecessary scanner.next() call
		System.out.println("Enter Class Name (-1 to Keep " + c.getName() + ") : ");
		String name = scanner.nextLine(); // ✅ Now correctly waits for input

		if (!name.equals("-1")) {
			c.setName(name);
		}

		c.update(database);
	}
}
