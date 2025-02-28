package Controller;

import java.util.Scanner;

import Model.Course;
import Model.Database;
import Model.Operation;
import Model.Student;

public class ReadCourseStudents implements Operation{

	@Override
	public void oper(Database database, Scanner scanner, int ID) {
		// TODO Auto-generated method stub
		
		System.out.println("Enter Course ID (-1 to show all courses) : ");
		int courseID = scanner.nextInt();
		while(courseID< 0) {
			new ReadEmployeeCourses().oper(database, scanner, ID);
			System.out.println("Enter Course ID (-1 to show all courses) : ");
			 courseID = scanner.nextInt();
		}
		Course course = new Course(courseID, database);
		for(Student s : course.getStudents()) {
			System.out.println("ID\tName\tPhone Number\tBirth Date\tClass : ");
			System.out.print(s.getID()+"\t");
			System.out.print(s.getFirstName()+" "+s.getLastName()+"\t");
			System.out.print(s.getPassword()+"\t");
			System.out.print(s.getBirthDate()+"\t");
			System.out.print(s.getCurrentClass().getName()+"\t");
			
			
		}
		
	}

}