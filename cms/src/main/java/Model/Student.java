package Model;

import java.sql.*;

public class Student {

    private int ID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private Model.Class c;
    private String password;

    public Student() {}

    // ✅ Constructor to fetch student by ID
    public Student(int ID, Database database) {
        this.ID = ID;
        String select = "SELECT * FROM Expleo.STUDENTS WHERE ID = ?";
        
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(select)) {
            pstmt.setInt(1, ID); // ✅ Bind the ID parameter
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                setFirstName(rs.getString("FirstName"));
                setLastName(rs.getString("LastName"));
                setEmail(rs.getString("Email"));
                setPhoneNumber(rs.getString("PhoneNumber"));
                setBirthDate(rs.getString("BirthDate"));
                setPassword(rs.getString("Password"));

                int classID = rs.getInt("Class");
                if (!rs.wasNull()) {
                    setClass(new Class(classID, database));
                }
            } else {
                System.out.println("⚠️ No student found with ID: " + ID);
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }
    public Student(int ID) {
    	this.ID = ID;
    }

    // Getters and Setters
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

    public Class getCurrentClass() { return c; }
    public void setClass(Class c) { this.c = c; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // ✅ CREATE Student
    public void create(Database database) {
        String insert = "INSERT INTO Expleo.STUDENTS (ID, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER, BIRTHDATE, CLASS, PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(insert)) {
            pstmt.setInt(1, ID);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, birthDate != null && !birthDate.isEmpty() ? birthDate : null);
            pstmt.setObject(7, (c != null) ? c.getID() : null, Types.INTEGER);
            pstmt.setString(8, password);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Student added successfully!");
            } else {
                System.out.println("⚠️ No student was added.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }

    // ✅ UPDATE Student
    public void update(Database database) {
        String update = "UPDATE Expleo.STUDENTS SET FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, PHONENUMBER = ?, BIRTHDATE = ?, CLASS = ?, PASSWORD = ? WHERE ID = ?";
        
        try (PreparedStatement pstmt = database.getConnection().prepareStatement(update)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, birthDate != null && !birthDate.isEmpty() ? birthDate : null);
            pstmt.setObject(6, (c != null) ? c.getID() : null, Types.INTEGER);
            pstmt.setString(7, password);
            pstmt.setInt(8, ID);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Student updated successfully!");
            } else {
                System.out.println("⚠️ No student was updated.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }

    // ✅ DELETE Student
    public void delete(Database database) {
        String delete = "DELETE FROM Expleo.STUDENTS WHERE ID = ?";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(delete)) {
            pstmt.setInt(1, ID);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Student deleted successfully!");
            } else {
                System.out.println("⚠️ No student found with ID: " + ID);
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }

    // ✅ PRINT Student Details
    public void print() {
        System.out.println("--------------------------------------------------");
        System.out.println("📌 Student Details:");
        System.out.println("ID         : " + ID);
        System.out.println("Name       : " + firstName + " " + lastName);
        System.out.println("Email      : " + email);
        System.out.println("Phone      : " + phoneNumber);
        System.out.println("BirthDate  : " + (birthDate != null ? birthDate : "N/A"));
        System.out.println("Class      : " + (c != null ? c.getName() : "None"));
        System.out.println("--------------------------------------------------");
    }
    public void showList() {
    	System.out.println("");
    }
}
