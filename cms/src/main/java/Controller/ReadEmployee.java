package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import Model.Database;
import Model.Department;
import Model.Employee;
import Model.Operation;

public class ReadEmployee implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
    ArrayList<Employee> employees = getAllEmployees(database);
        for (Employee e : employees) {
            e.print(); 
        }
    }
    public ArrayList<Employee> getAllEmployees(Database database){
    	
    	  ArrayList<Employee> employees = new ArrayList<>();
          ArrayList<Integer> deptIDs = new ArrayList<>();

          try {
              
              String select = "SELECT ID, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER, BIRTHDATE, SALARY, DEPARTMENT, PASSWORD FROM Expleo.EMPLOYEES";
              ResultSet rs = database.getStatement().executeQuery(select);

              while (rs.next()) {
                  Employee employee = new Employee();
                  employee.setID(rs.getInt("ID"));
                  employee.setFirstName(rs.getString("FIRSTNAME"));
                  employee.setLastName(rs.getString("LASTNAME"));
                  employee.setEmail(rs.getString("EMAIL"));
                  employee.setPhoneNumber(rs.getString("PHONENUMBER"));
                  employee.setBirthDate(rs.getString("BIRTHDATE"));
                  employee.setSalary(rs.getDouble("SALARY"));
                  employee.setPassword(rs.getString("PASSWORD"));

                 
                  int deptID = rs.getInt("DEPARTMENT");
                  deptIDs.add(rs.wasNull() ? -1 : deptID);
                  employees.add(employee);
              }
              rs.close(); 
          } catch (SQLException e) {
              System.out.println("Error fetching employees: " + e.getMessage());
              
          }

          for (int i = 0; i < employees.size(); i++) {
              if (deptIDs.get(i) != -1) {
                  employees.get(i).setDepartment(new Department(deptIDs.get(i), database));
              } else {
                  employees.get(i).setDepartment(null); 
              }
          }

          for (Employee e : employees) {
              e.print(); 
          }
          return employees;
    }
}
