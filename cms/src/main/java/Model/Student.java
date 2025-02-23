package Model;

public class Student {

	
	private int ID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String birthDate;
	private Model.Class c;
	
	
	
	public Student () {}
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID=ID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName=firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName=lastName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
		
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber=phoneNumber;
	}
	public String getBirtDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate=birthDate;
	}
	public Class getCurrentClass() {
		return c;
	}
	public void setClass(Class c) {
		this.c=c;
	}
}
