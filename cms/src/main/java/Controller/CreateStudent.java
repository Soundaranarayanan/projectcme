package Controller;

import java.util.Scanner;
import Model.Class;
import Model.Database;
import Model.Operation;
import Model.Student;

public class CreateStudent implements Operation {

	@Override
	public void oper(Database database, Scanner scanner) {
		// TODO Auto-generated method stub
		
		Student s = new Student();
		
		System.out.println("Enter First Name");
		s.setFirstName(scanner.next());
		
		System.out.println("Enter Last Name");
		s.setLastName(scanner.next());
		
		System.out.println("Enter Email");
		s.setEmail(scanner.next());
		
		System.out.println("Enter PhoneNUmber");
		s.setPhoneNumber(scanner.next());
		
		System.out.println("Enter BirthDate");
		s.setBirthDate(scanner.next());
		
		System.out.println("Enter the class ID ( -1 to show all classes) : ");
		int classID = scanner.nextInt();
		while (classID < 0) {
			new ReadClasses().oper(database, scanner);
			
			System.out.println("Enter the class ID ( -1 to show all classes) : ");
			 classID = scanner.nextInt();
		}
		
		s.setClass(new Class(classID, database));
		s.create(database);
	}

}
