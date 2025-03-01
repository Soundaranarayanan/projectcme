package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import Model.Database;
import Model.Employee;
import Model.Operation;
import Model.Student;

public class UpdateStudentPassword implements Operation{

	@Override
	public void oper(Database database, Scanner scanner, int ID) {
		// TODO Auto-generated method stub
        Student student = new Student(ID, database);
        
        System.out.println("Enter Old Password: ");
        String oldPassword = scanner.next();
        
        if (!student.getPassword().equals(oldPassword)) {
            System.out.println("❌ Wrong Password");
            return;
        }

        String newPassword, confirmPassword;
        do {
            System.out.println("Enter New Password: ");
            newPassword = scanner.next();
            System.out.println("Confirm Password: ");
            confirmPassword = scanner.next();
            
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("❌ Passwords do not match. Try again.");
            }
        } while (!newPassword.equals(confirmPassword));

        String updateQuery = "UPDATE Expleo.STUDENTS SET PASSWORD = ? WHERE ID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, newPassword);
            pstmt.setInt(2, ID);
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("✅ Password Updated Successfully");
            } else {
                System.out.println("❌ Failed to Update Password");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error Updating Password: " + e.getMessage());
        }
	}

}
