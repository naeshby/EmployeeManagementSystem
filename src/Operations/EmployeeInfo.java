package Operations;

public class EmployeeInfo {
    public String employeeID;
    public String name;
    public String contact;
    public String email;
    public String position;
    public String salary;

    // Optional: Add constructors, getters, and setters
    public EmployeeInfo() {}

    public EmployeeInfo(String id, String name, String contact, String email, String position, String salary) {
        this.employeeID = id;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.position = position;
        this.salary = salary;
    }
}