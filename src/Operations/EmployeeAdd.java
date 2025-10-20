package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeAdd {
    public void createEmployee(EmployeeInfo employee) {
        String query = "INSERT INTO employees (employee_id, name, contact, email, position, salary) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employee.employeeID);
            stmt.setString(2, employee.name);
            stmt.setString(3, employee.contact);
            stmt.setString(4, employee.email);
            stmt.setString(5, employee.position);
            stmt.setDouble(6, Double.parseDouble(employee.salary));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding employee: " + e.getMessage(), e);
        }
    }
}
