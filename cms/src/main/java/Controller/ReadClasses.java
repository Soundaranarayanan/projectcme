package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Class;
import Model.Database;
import Model.Operation;

public class ReadClasses implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        for (Class c : getAllClasses(database)) {
            c.print();
        }
    }

    public ArrayList<Class> getAllClasses(Database database) {
        ArrayList<Class> classes = new ArrayList<>();
        String selectQuery = "SELECT ID, NAME FROM Expleo.CLASSES";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(selectQuery);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Class c = new Class();
                c.setID(rs.getInt("ID"));
                c.setName(rs.getString("NAME")); // ✅ Ensure column name matches DB
                classes.add(c);
            }

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
        return classes;
    }
}
