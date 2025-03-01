package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Course;
import Model.Database;
import Model.Operation;
import Model.Student;

public class AddCourseGrades implements Operation {

    @Override
    public void oper(Database database, Scanner scanner, int ID) {
        System.out.println("Enter Course ID (-1 to show all your courses) : ");
        int courseID = scanner.nextInt();

        // Ensure the course ID is valid
        while (courseID < 0) {
            System.out.println("Enter a valid Course ID (-1 to show all your courses) : ");
            courseID = scanner.nextInt();
        }

        // Fetch the course and its students
        Course course = new Course(courseID, database);
        ArrayList<Student> students = course.getStudents();

        if (students.isEmpty()) {
            System.out.println("⚠️ No students found in this course.");
            return;
        }

        System.out.println("Enter MAX grade: ");
        double max = scanner.nextDouble();

        // SQL INSERT statement
        String insert = "INSERT INTO Expleo.GRADES (COURSE, CLASS, STUDENT, GRADE, MAX) VALUES (?, ?, ?, ?, ?)";

        // Use try-with-resources to ensure resources are closed properly
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insert)) {

            System.out.println("Type the grade for each student:");

            for (Student student : students) {
                System.out.print(student.getFirstName() + " " + student.getLastName() + ": ");
                double grade = scanner.nextDouble();

                // Set the parameters for the PreparedStatement
                pstmt.setInt(1, courseID);
                pstmt.setInt(2, course.getID());
                pstmt.setInt(3, student.getID());
                pstmt.setDouble(4, grade);
                pstmt.setDouble(5, max);

                // Execute the insert statement
                pstmt.executeUpdate();
            }

            System.out.println("✅ Grades added successfully!");

        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}