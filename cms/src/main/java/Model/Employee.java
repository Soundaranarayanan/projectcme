package Model;

import java.sql.*;
import java.util.Scanner;

import Controller.CreateClass;
import Controller.CreateCourse;
import Controller.CreateDepartment;
import Controller.CreateEmployee;
import Controller.CreateStudent;
import Controller.DeleteClass;
import Controller.DeleteCourse;
import Controller.DeleteDepartment;
import Controller.DeleteEmployee;
import Controller.DeleteStudent;
import Controller.ReadClasses;
import Controller.ReadCourseStudents;
import Controller.ReadCourses;
import Controller.ReadDepartments;
import Controller.ReadEmployee;
import Controller.ReadEmployeeCourses;
import Controller.ReadStudents;
import Controller.UpdateClass;
import Controller.UpdateCourse;
import Controller.UpdateDepartment;
import Controller.UpdateEmployee;
import Controller.UpdateStudent;

public class Employee {
 
    private int ID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private double salary;
    private Department department;
    private String password;

    public Employee() {}

    public Employee(int ID, Database database) {
        this.ID = ID;
        loadEmployeeFromDB(database);
    }

    // ✅ Load Employee Data
    private void loadEmployeeFromDB(Database database) {
        String query = "SELECT * FROM Expleo.EMPLOYEES WHERE ID = ?";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                populateFromResultSet(rs, database);
            } else {
                System.out.println("⚠️ No employee found with ID: " + ID);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error loading employee: " + e.getMessage());
        }
    }

    // ✅ Populate Employee Object from ResultSet
    private void populateFromResultSet(ResultSet rs, Database database) throws SQLException {
        this.firstName = rs.getString("FIRSTNAME");
        this.lastName = rs.getString("LASTNAME");
        this.email = rs.getString("EMAIL");
        this.phoneNumber = rs.getString("PHONENUMBER");
        this.birthDate = rs.getString("BIRTHDATE");
        this.salary = rs.getDouble("SALARY");
        this.password = rs.getString("PASSWORD");

        int deptID = rs.getInt("DEPARTMENT");
        if (!rs.wasNull()) {
            this.department = new Department(deptID, database);
        }
    }

    // ✅ Getter & Setter Methods
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // ✅ Optimized Update Method
    public void update(Database database) {
        String query = "UPDATE Expleo.EMPLOYEES SET FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, "
                     + "PHONENUMBER = ?, BIRTHDATE = ?, SALARY = ?, DEPARTMENT = ?, PASSWORD = ? "
                     + "WHERE ID = ?";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, birthDate);
            pstmt.setDouble(6, salary);
            pstmt.setObject(7, (department != null) ? department.getID() : null, Types.INTEGER);
            pstmt.setString(8, password);
            pstmt.setInt(9, ID);

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "✅ Employee updated successfully!" : "⚠️ No employee found with the given ID.");
        } catch (SQLException e) {
            System.out.println("❌ Error updating employee: " + e.getMessage());
        }
    }

    // ✅ Prevent NullPointerException in print()
    public void print() {
        System.out.println("ID : \t\t\t" + ID);
        System.out.println("Name : \t\t\t" + firstName + " " + lastName);
        System.out.println("Email : \t\t" + email);
        System.out.println("PhoneNumber : \t\t" + phoneNumber);
        System.out.println("BirthDate : \t\t" + birthDate);
        System.out.println("Salary : \t\t" + salary);
        System.out.println("Department : \t\t" + (department != null ? department.getName() : "None"));
        System.out.println("_____________________________________________________\n");
    }

    // ✅ Improved Create Method
    public void create(Database database) {
        String query = "INSERT INTO Expleo.EMPLOYEES (ID, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER, BIRTHDATE, SALARY, DEPARTMENT, PASSWORD) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, ID);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, birthDate);
            pstmt.setDouble(7, salary);
            pstmt.setObject(8, (department != null) ? department.getID() : null, Types.INTEGER);
            pstmt.setString(9, password);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("\n✅ Employee added successfully!");
                print();
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }
    
    private Operation [] managerOperations = new Operation[] {
    		new CreateDepartment(),
    		new ReadDepartments(),
    		new UpdateDepartment(),
    		new DeleteDepartment(),
    		new CreateClass(),
    		new ReadClasses(),
    		new UpdateClass(),
    		new DeleteClass(),
    		new CreateCourse(),
    		new ReadCourses(),
    		new UpdateCourse(),
    		new DeleteCourse(),
    		new CreateEmployee(),
    		new ReadEmployee(),
    		new UpdateEmployee(),
    		new DeleteEmployee(),
    		new CreateStudent(),
    		new ReadStudents(),
    		new UpdateStudent(),
    		new DeleteStudent()
    };
    
    private Operation[] profOperations = new Operation[] {
    	new ReadDepartments(),
    	new ReadClasses(),
    	new ReadCourses(),
    	new ReadEmployeeCourses(),
    	new ReadCourseStudents()
    	
    };
    
    
    public void showList(Database database, Scanner scanner) {
    	if(department.getName().equals("Management")) {
    		System.out.println("\n---------------------------------------");
    		System.out.println("1. Add New Department");
    		System.out.println("2. Show all Departments");
    		System.out.println("3. Edit Department");
    		System.out.println("4. Delete Department");
    		System.out.println("5. Add New Class");
    		System.out.println("6. Show all Class");
    		System.out.println("7. Edit Class");
    		System.out.println("8. Delete Class");
    		System.out.println("9. Add New Course");
    		System.out.println("10. Show all Course");
    		System.out.println("11. Edit Course");
    		System.out.println("12. Delete Course");
    		System.out.println("13. Add New Employee");
    		System.out.println("14. Show all Employee");
    		System.out.println("15. Edit Employee");
    		System.out.println("16. Delete Employee");
    		System.out.println("17. Add New Student");
    		System.out.println("18. Show all Students");
    		System.out.println("19. Edit Student");
    		System.out.println("20. Delete Student");
    		System.out.println("---------------------------------------\n");

    		int selected = scanner.nextInt();
    		managerOperations[selected-1].oper(database, scanner, getID());
    		showList(database,scanner);
    		
    	}else {
    		System.out.println("\n--------------------------");
    		System.out.println("1. Show all Departments");
    		System.out.println("2. Show all Classes ");
    		System.out.println("3. Show all Courses");
    		System.out.println("4. Show my Courses");
    		System.out.println("5. Show Course Students");
    		System.out.println("6. Add Course Grades");
    		System.out.println("7. Show Course Grades");
    		System.out.println("8. Edit Course Grades");
    		System.out.println("9. Delete Course Grades");
    		System.out.println("--------------------------\n");
    		
    		int selected = scanner.nextInt();
    		profOperations[selected-1].oper(database, scanner, getID());
    		showList(database,scanner);
    		
    	}
    }
}