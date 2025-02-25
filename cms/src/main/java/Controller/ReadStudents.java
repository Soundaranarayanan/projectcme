package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import Model.Student;
import Model.Class;
import Model.Database;
import Model.Operation;

public class ReadStudents implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
    	ArrayList<Student> students = getAllStudents(database);
    	
        for (Student s : students) {
            s.print();
        }
    }

    public ArrayList<Student> getAllStudents(Database database) {
        ArrayList<Student> students = new ArrayList<>();
        String select = "SELECT * FROM Expleo.STUDENTS";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(select);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Student s = new Student();
                s.setID(rs.getInt("ID"));
                s.setFirstName(rs.getString("FirstName"));
                s.setLastName(rs.getString("LastName"));
                s.setEmail(rs.getString("Email"));
                s.setPhoneNumber(rs.getString("PhoneNumber"));
                s.setBirthDate(rs.getString("BirthDate"));
                s.setPassword(rs.getString("Password"));
                students.add(s);

                // Handle Class association
                int classID = rs.getInt("Class");
                if (!rs.wasNull()) {
                    s.setClass(new Class(classID, database));
                }

                students.add(s); // ✅ Now adding the student to the list
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }

        return students;
    }
}
