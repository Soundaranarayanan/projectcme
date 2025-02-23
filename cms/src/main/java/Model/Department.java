package Model;

import java.sql.SQLException;

public class Department {

	
	private int ID;
	private String name;
	
	
	public Department() {}
	
	
	public Department(int ID, Database database) {
	
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID=ID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	
	public void create(Database database) {
		String insert = "INSERT INTO Expleo.DEPARTMENTS (ID, NAME) VALUES (?, ?)";
		
		try {
			database.getStatement().execute(insert);
			System.out.println("Department created Sucessfully");
		}
catch(SQLException e) {
	System.out.println(e.getMessage());
}
	}
}
