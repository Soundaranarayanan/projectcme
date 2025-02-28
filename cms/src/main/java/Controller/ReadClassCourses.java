package Controller;

import java.util.ArrayList;
import java.util.Scanner;

import Model.Course;
import Model.Database;
import Model.Operation;
import Model.Student;

public class ReadClassCourses implements Operation{

	@Override
	public void oper(Database database, Scanner scanner, int ID) {
		// TODO Auto-generated method stub
		Student student = new Student(ID, database);
		ArrayList<Course> courses = new ReadCourses().getAllCourses(database);
		System.out.println("ID\tName\tDescription\tProf\tDepartment");

		for(Course c : courses) {
			if(c.getCurrentClass().getID()==student.getCurrentClass().getID()) {
				System.out.print(c.getID()+"\t");
				System.out.print(c.getName()+"\t");
				System.out.print(c.getDescription()+"\t");
				System.out.print(c.getProf().getFirstName()+" "+c.getProf().getLastName()+"\t");
				System.out.print(c.getDept().getName()+"\n");



			}
		}
	}

}
