package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Course;
import Model.Class;
import Model.Database;
import Model.Department;
import Model.Employee;
import Model.Operation;

public class ReadCourses implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        for (Course c : getAllCourses(database)) {
            c.print();
        }
    }

    public ArrayList<Course> getAllCourses(Database database) {
        ArrayList<Course> courses = new ArrayList<>();

        String query = "SELECT c.ID, c.Name, c.Class, c.Description, c.Limit, c.Prof, c.Department FROM Expleo.COURSES c";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Course course = new Course();
                course.setID(rs.getInt("ID"));
                course.setName(rs.getString("Name"));
                course.setDescription(rs.getString("Description"));
                course.setLimit(rs.getInt("Limit"));

                course.setCurrentClass(new Class(rs.getInt("Class"), database));
                course.setProf(new Employee(rs.getInt("Prof"), database));
                course.setDept(new Department(rs.getInt("Department"), database));

                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }
}
