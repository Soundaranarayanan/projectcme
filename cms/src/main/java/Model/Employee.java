package Model;

import java.sql.*;

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
        try {
            this.ID = ID;

            String select = "SELECT ID, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER, BIRTHDATE, "
                          + "SALARY, DEPARTMENT, PASSWORD FROM Expleo.EMPLOYEES WHERE ID = ?";

            try (PreparedStatement pstmt = database.getConnection().prepareStatement(select)) {
                pstmt.setInt(1, ID);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    this.firstName = rs.getString("FIRSTNAME");
                    this.lastName = rs.getString("LASTNAME");
                    this.email = rs.getString("EMAIL");
                    this.phoneNumber = rs.getString("PHONENUMBER");
                    this.birthDate = rs.getString("BIRTHDATE");
                    this.salary = rs.getDouble("SALARY");
                    this.password = rs.getString("PASSWORD");

                    int deptID = rs.getInt("DEPARTMENT");
                    if (!rs.wasNull()) {  // ✅ Fix: Handle NULL department
                        this.department = new Department(deptID, database);
                    }
                } else {
                    System.out.println("No employee found with ID: " + ID);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
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

    // ✅ Fixed update() method
    public void update(Database database) {
        String update = "UPDATE Expleo.EMPLOYEES SET FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, "
                      + "PHONENUMBER = ?, BIRTHDATE = ?, SALARY = ?, DEPARTMENT = ?, PASSWORD = ? "
                      + "WHERE ID = ?";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(update)) {
            pstmt.setString(1, this.firstName);
            pstmt.setString(2, this.lastName);
            pstmt.setString(3, this.email);
            pstmt.setString(4, this.phoneNumber);
            pstmt.setString(5, this.birthDate);
            pstmt.setDouble(6, this.salary);
            
            if (this.department != null) {
                pstmt.setInt(7, this.department.getID());
            } else {
                pstmt.setNull(7, Types.INTEGER);  // ✅ Handle NULL department
            }

            pstmt.setString(8, this.password);
            pstmt.setInt(9, this.ID);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("No employee found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void print() {
    	System.out.println("ID : \t\t\t"+getID());
    	System.out.println("Name : \t\t\t"+getFirstName()+" "+getLastName());
    	System.out.println("Email : \t\t"+getEmail());
    	System.out.println("PhoneNumber : \t\t"+getPhoneNumber());
    	System.out.println("BirthDate : \t\t"+getBirthDate());
    	System.out.println("Salary : \t\t"+getSalary());
    	System.out.println("Department : \t\t"+getDepartment().getName());
    	System.out.println("_____________________________________________________\n");
    	//System.out.println("Password : \t\t"+getPassword());
    }
}
