package Controller;

import java.util.ArrayList;
import java.util.Scanner;

import Model.Course;
import Model.Database;
import Model.Employee;
import Model.Operation;

public class ReadEmployeeCourses implements Operation{

	@Override
	public void oper(Database database, Scanner scanner, int ID) {
		// TODO Auto-generated method stub
		
		
		for (Course c : new ReadCourses().getAllCourses(database)) {
			if(c.getProf().getID()==ID)
            c.print();
        }
        }
		
	}


