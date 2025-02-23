package Controller;

import java.util.Scanner;
import Model.Database;
import Model.Department;
import Model.Employee;
import Model.Operation;

public class UpdateEmployee implements Operation {

    @Override
    public void oper(Database database, Scanner scanner) {
        System.out.print("Enter User ID (-1 to Show all Employees): ");
        int userID = scanner.nextInt();

        while (userID == -1) {
            new ShowAllEmployee().oper(database, scanner);
            System.out.print("Enter User ID (-1 to Show all Employees): ");
            userID = scanner.nextInt();
        }

        Employee employee = new Employee(userID, database);

        System.out.printf("Enter First Name (-1 to Keep \"%s\"): ", employee.getFirstName());
        String firstName = scanner.next();
        if (!firstName.equals("-1")) employee.setFirstName(firstName);

        System.out.printf("Enter Last Name (-1 to Keep \"%s\"): ", employee.getLastName());
        String lastName = scanner.next();
        if (!lastName.equals("-1")) employee.setLastName(lastName);

        System.out.printf("Enter Email (-1 to Keep \"%s\"): ", employee.getEmail());
        String email = scanner.next();
        if (!email.equals("-1")) employee.setEmail(email);

        System.out.printf("Enter Phone Number (-1 to Keep \"%s\"): ", employee.getPhoneNumber());
        String phoneNumber = scanner.next();
        if (!phoneNumber.equals("-1")) employee.setPhoneNumber(phoneNumber);

        System.out.printf("Enter Birth Date (-1 to Keep \"%s\"): ", employee.getBirthDate());
        String birthDate = scanner.next();
        if (!birthDate.equals("-1")) employee.setBirthDate(birthDate);

        System.out.printf("Enter Salary (-1 to Keep \"%.2f\"): ", employee.getSalary());
        double salary = scanner.nextDouble();
        if (salary != -1) employee.setSalary(salary);

        System.out.print("Enter Password (-1 to Keep old Password): ");
        String password = scanner.next();
        if (!password.equals("-1")) {
            System.out.print("Confirm Password: ");
            String confirmPassword = scanner.next();
            while (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Try again.");
                System.out.print("Enter Password: ");
                password = scanner.next();
                System.out.print("Confirm Password: ");
                confirmPassword = scanner.next();
            }
            employee.setPassword(password);
        }

       
        System.out.printf("Enter Department (-1 to Keep \"%s\", -2 to Show all Departments): ",
                employee.getDepartment() != null ? employee.getDepartment().getName() : "No Department Assigned");

        int deptID = scanner.nextInt();
        if (deptID != -1) {
            while (deptID == -2) {
                new ShowAllDepartments().oper(database, scanner);
                System.out.print("Enter Department (-1 to Keep current, -2 to Show all): ");
                deptID = scanner.nextInt();
            }
            employee.setDepartment(new Department(deptID, database));
        }

        employee.update(database);
    }
}
