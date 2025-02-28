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
    public void oper(Database database, Scanner scanner, int id) {
        ArrayList<Course> courses = getAllCourses(database);
        if (courses.isEmpty()) {
            System.out.println("❌ No courses found.");
        } else {
            for (Course c : courses) {
                c.print();
            }
        }
    }

    public ArrayList<Course> getAllCourses(Database database) {
        ArrayList<Course> courses = new ArrayList<>();
        Connection conn = null;

        String query = "SELECT ID, Name, Class, Description, Limit, Prof, Department FROM Expleo.COURSES";

        try {
            conn = database.getConnection();
            if (conn == null || conn.isClosed()) {
                System.err.println("❌ Database connection is closed.");
                return courses;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Course course = new Course();
                    course.setID(rs.getInt("ID"));
                    course.setName(rs.getString("Name"));
                    course.setDescription(rs.getString("Description"));
                    course.setLimit(rs.getInt("Limit"));

                    // Ensure valid foreign keys before creating objects
                    int classID = rs.getInt("Class");
                    int profID = rs.getInt("Prof");
                    int deptID = rs.getInt("Department");

                    if (classID > 0) course.setCurrentClass(new Class(classID, database));
                    if (profID > 0) course.setProf(new Employee(profID, database));
                    if (deptID > 0) course.setDept(new Department(deptID, database));

                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching courses: " + e.getMessage());
        } finally {
            database.close(conn); // Ensure connection is properly closed
        }

        return courses;
    }
}