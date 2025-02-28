package Controller;

import java.util.Scanner;

import Model.Course;
import Model.Database;
import Model.Operation;
import Model.Student;

public class RegisterCourse implements Operation{

	@Override
	public void oper(Database database, Scanner scanner, int ID) {
		Student student = new Student(ID, database);
		// TODO Auto-generated method stub
		System.out.println("Enter course ID (-1 to show all course) : ");
		int courseID = scanner.nextInt();
		while(courseID<0) {
			new ReadClassCourses().oper(database, scanner, ID);
			System.out.println("Enter course ID (-1 to show all course) : ");
			 courseID = scanner.nextInt();
		}
		
		Course SelectedCourse = new Course(courseID, database);
		if(SelectedCourse.getStudents().size()>SelectedCourse.getLimit()) {
			System.out.println("This course is Full.\n Try Again");
		}
		else if(SelectedCourse.getStudents().contains(student)){
			System.out.println("You have already registred this course");
		}else {
			//student.registerCourse(database,courseID);

		}
		
	}

}
