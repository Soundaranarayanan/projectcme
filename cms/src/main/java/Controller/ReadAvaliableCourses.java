package Controller;

import java.util.ArrayList;
import java.util.Scanner;

import Model.Course;
import Model.Database;
import Model.Operation;
import Model.Student;

public class ReadAvaliableCourses implements Operation{

	@Override
	public void oper(Database database, Scanner scanner, int ID) {
		// TODO Auto-generated method stub
		
		Student student = new Student(ID, database);
		ArrayList<Course> courses = new ReadCourses().getAllCourses(database);
		
		for(Course c : courses) {
			if(c.getCurrentClass().getID()==student.getCurrentClass().getID() && c.getStudents().size()<c.getLimit()) {
				c.print();
			}
		}
		
	}

}
