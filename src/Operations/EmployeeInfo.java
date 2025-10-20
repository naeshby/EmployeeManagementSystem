package Operations;

public class EmployeeInfo {
    public String employeeID;
    public String name;
    public String contact;
    public String email;
    public String position;
    public String salary;

    public EmployeeInfo() {}

    public EmployeeInfo(String id, String name, String contact, String email, String position, String salary) {
        this.employeeID = id;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.position = position;
        this.salary = salary;
    }

    // Getters and setters
    public String getEmployeeID() { return employeeID; }
    public void setEmployeeID(String employeeID) { this.employeeID = employeeID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public boolean isValid() {
        return employeeID != null && !employeeID.trim().isEmpty() &&
                name != null && !name.trim().isEmpty() &&
                salary != null && !salary.trim().isEmpty();
    }
}