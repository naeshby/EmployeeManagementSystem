package Operations;

import java.util.Scanner;

public class EmployeeInfo {
    String employeeID;
    String name;
    String contact;
    String email;
    String position;
    String salary;

    public void getInfo() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name: ");
        name = input.nextLine();
        System.out.print("ID: ");
        employeeID = input.nextLine();
        System.out.print("Contact: ");
        contact = input.nextLine();
        System.out.print("Email: ");
        email = input.nextLine();
        System.out.print("Position: ");
        position = input.nextLine();
        System.out.print("Salary: ");
        salary = input.nextLine();
    }
}
