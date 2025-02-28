package Controller;

import java.util.Scanner;

import Model.Database;
import Model.Operation;
import Model.Student;

public class DeleteStudent implements Operation{

	@Override
	public void oper(Database database, Scanner scanner, int id) {
		// TODO Auto-generated method stub
		
		System.out.println("Enter Student ID (-1 to showw All ID)");
		int ID = scanner.nextInt();
		while(ID < 0) {
			new ReadStudents().oper(database, scanner, id);
			System.out.println("Enter Student ID (-1 to showw All ID)");
			 ID = scanner.nextInt();
		}
		Student s = new Student(ID);
		s.delete(database);
	}

}