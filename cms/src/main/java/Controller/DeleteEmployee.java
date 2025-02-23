package Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import Model.Database;
import Model.Operation;

public class DeleteEmployee implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        System.out.println("Enter Employee ID (-1 to Show all Employees): ");
        int ID = scanner.nextInt();

        while (ID < 0) {
            new ShowAllEmployee().oper(database, scanner);
            System.out.println("Enter Employee ID (-1 to Show all Employees): ");
            ID = scanner.nextInt();
        }

        String delete = "DELETE FROM Expleo.EMPLOYEES WHERE ID = ?";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(delete)) {
            pstmt.setInt(1, ID);
            int rowsAffected = pstmt.executeUpdate(); 

            if (rowsAffected > 0) {
                System.out.println("Employee Deleted Successfully.");
            } else {
                System.out.println("No Employee found with ID: " + ID);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }
}
