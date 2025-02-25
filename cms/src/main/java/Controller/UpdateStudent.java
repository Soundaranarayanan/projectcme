package Controller;

import java.util.Scanner;

import Model.Database;
import Model.Operation;
import Model.Student;

public class UpdateStudent implements Operation{

	@Override
	public void oper(Database database, Scanner scanner) {
		// TODO Auto-generated method stub
		
		System.out.println("Enter ID( -1 to show all Students) ; ");
		int ID = scanner.nextInt();
		while(ID < 0) {
			new ReadStudents().oper(database, scanner);
			System.out.println("Enter ID( -1 to show all Students) ; ");
			ID = scanner.nextInt();
		}
		
		Student s = new Student(ID, database);
		System.out.println("Enter the FirstName (-1 to keep " +s.getFirstName()+") : ");
		String firstName = scanner.next();
		if(!firstName.equals("-1")) s.setFirstName(firstName);
		
		System.out.println("Enter the LastName (-1 to keep " +s.getLastName()+") : ");
		String lastName = scanner.next();
		if(!lastName.equals("-1")) s.setLastName(lastName);
		
		System.out.println("Enter the Email (-1 to keep " +s.getEmail()+") : ");
		String email = scanner.next();
		if(!email.equals("-1")) s.setEmail(email);
		
		System.out.println("Enter the PhoneNumber (-1 to keep " +s.getPhoneNumber()+") : ");
		String phoneNumber = scanner.next();
		if(!phoneNumber.equals("-1")) s.setPhoneNumber(phoneNumber);
		
		System.out.println("Enter the BirthDate (-1 to keep " +s.getBirthDate()+") : ");
		String birthDate = scanner.next();
		if(!birthDate.equals("-1")) s.setBirthDate(birthDate);
		
		System.out.println("Enter the Class (-1 to keep " +s.getCurrentClass()+" -2 to show all ) : ");
		int classID = scanner.nextInt();
		if(classID != -1) {
			while(classID == -2) {
				new ReadClasses().oper(database, scanner);
				System.out.println("Enter the password");
				String password = scanner.next();		
				if(!password.equals("-1")) {
					System.out.println("Confirm Password");
					String confirmPassword = scanner.next();
					while(!password.equals(confirmPassword)) {
						System.out.println("Enter the password");
					    password = scanner.next();	
						System.out.println("Confirm Password");
						 confirmPassword = scanner.next();
					}
					s.setPassword(password);
				}
				s.update(database);
				
			}
		}
		
		
		
	}

}
