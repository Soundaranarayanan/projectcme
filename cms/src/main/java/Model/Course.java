package Model;

import java.sql.*;
import java.util.ArrayList;

public class Course {
    private int ID;
    private String name;
    private Class c;
    private String description;
    private int limit;
    private ArrayList<Student> students;
    private Employee prof;
    private Department dept;

    public Course() {}

    public Course(int ID, Database database) {
        this.ID = ID;
        this.students = new ArrayList<>();
        loadFromDatabase(database);
    }

    private void loadFromDatabase(Database database) {
        String selectQuery = "SELECT * FROM Expleo.COURSES WHERE ID = ?";
        String studentQuery = "SELECT Student FROM Courses_" + ID;

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {

            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                this.name = rs.getString("Name");
                int classID = rs.getInt("Class");
                this.description = rs.getString("Description");
                this.limit = rs.getInt("Limit");
                int profID = rs.getInt("Prof");
                int deptID = rs.getInt("Department");

                this.c = (classID > 0) ? new Class(classID, database) : null;
                this.prof = (profID > 0) ? new Employee(profID, database) : null;
                this.dept = (deptID > 0) ? new Department(deptID, database) : null;
            }

            // Fetch students
            try (Statement stmt = conn.createStatement();
                 ResultSet rs2 = stmt.executeQuery(studentQuery)) {
                while (rs2.next()) {
                    students.add(new Student(rs2.getInt("Student"), database));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error loading course: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Class getCurrentClass() { return c; }
    public void setCurrentClass(Class c) { this.c = c; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public ArrayList<Student> getStudents() { return students; }
    public void setStudents(ArrayList<Student> students) { this.students = students; }

    public Employee getProf() { return prof; }
    public void setProf(Employee prof) { this.prof = prof; }

    public Department getDept() { return dept; }
    public void setDept(Department dept) { this.dept = dept; }

    public void print() {
        System.out.println("------------------------------------");
        System.out.println("ID: " + getID());
        System.out.println("Name: " + getName());
        System.out.println("Class: " + (c != null ? c.getName() : "N/A"));
        System.out.println("Description: " + getDescription());
        System.out.println("Limit: " + getLimit());
        System.out.println("Prof: " + (prof != null ? prof.getFirstName() + " " + prof.getLastName() : "N/A"));
        System.out.println("Department: " + (dept != null ? dept.getName() : "N/A"));
        System.out.println("------------------------------------");
    }

    public void create(Database database) {
        if (c == null || prof == null || dept == null) {
            System.out.println("❌ Error: Missing Class, Professor, or Department.");
            return;
        }

        String insertQuery = "INSERT INTO Expleo.COURSES (ID, NAME, CLASS, DESCRIPTION, LIMIT, PROF, DEPARTMENT) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String createTableQuery = "CREATE TABLE Courses_" + this.ID + " (Student INT)";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery);
             Statement stmt = conn.createStatement()) {

            pstmt.setInt(1, this.ID);
            pstmt.setString(2, this.name);
            pstmt.setInt(3, this.c.getID());
            pstmt.setString(4, this.description);
            pstmt.setInt(5, this.limit);
            pstmt.setInt(6, this.prof.getID());
            pstmt.setInt(7, this.dept.getID());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Course Created Successfully.");
                stmt.executeUpdate(createTableQuery);
                System.out.println("✅ Table Courses_" + this.ID + " created.");
            } else {
                System.out.println("⚠️ No course inserted.");
            }

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Database database) {
        if (c == null || prof == null || dept == null) {
            System.out.println("❌ Error: Missing Class, Professor, or Department.");
            return;
        }

        String updateQuery = "UPDATE Expleo.COURSES SET NAME = ?, CLASS = ?, DESCRIPTION = ?, LIMIT = ?, PROF = ?, DEPARTMENT = ? WHERE ID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, this.name);
            pstmt.setInt(2, this.c.getID());
            pstmt.setString(3, this.description);
            pstmt.setInt(4, this.limit);
            pstmt.setInt(5, this.prof.getID());
            pstmt.setInt(6, this.dept.getID());
            pstmt.setInt(7, this.ID);

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "✅ Course Updated Successfully." : "⚠️ No changes were made.");

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(Database database) {
        String deleteCourseQuery = "DELETE FROM Expleo.COURSES WHERE ID = ?";
        String checkTableExistsQuery = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        String dropTableQuery = "DROP TABLE Courses_" + this.ID;

        try (Connection conn = database.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteCourseQuery);
             PreparedStatement checkTableStmt = conn.prepareStatement(checkTableExistsQuery);
             Statement dropTableStmt = conn.createStatement()) {

            deleteStmt.setInt(1, this.ID);
            int rowsDeleted = deleteStmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✅ Course Deleted Successfully.");

                // Check if table exists before dropping
                checkTableStmt.setString(1, ("Courses_" + this.ID).toUpperCase());
                ResultSet rs = checkTableStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    dropTableStmt.executeUpdate(dropTableQuery);
                    System.out.println("✅ Table Courses_" + this.ID + " deleted.");
                } else {
                    System.out.println("⚠️ Table Courses_" + this.ID + " does not exist.");
                }
            } else {
                System.out.println("⚠️ No Course Found with ID: " + this.ID);
            }

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}