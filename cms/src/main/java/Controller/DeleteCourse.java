package Controller;

import java.util.Scanner;
import Model.Course;
import Model.Database;
import Model.Operation;

public class DeleteCourse implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        System.out.println("Enter Course ID (-1 to show all): ");
        int ID = scanner.nextInt();

        while (ID == -1) {
            new ReadCourses().oper(database, scanner);
            System.out.println("Enter Course ID: ");
            ID = scanner.nextInt();
        }

        Course course = new Course(ID);
        course.delete(database);
    }
}
