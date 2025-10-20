package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeAdd {
    public boolean createEmployee(EmployeeInfo employee) {
        if (!employee.isValid()) {
            throw new IllegalArgumentException("Invalid employee data");
        }

        // Validate salary format
        try {
            Double.parseDouble(employee.getSalary());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid salary format");
        }

        String query = "INSERT INTO employees (employee_id, name, contact, email, position, salary) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employee.getEmployeeID());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getContact());
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getPosition());
            stmt.setDouble(6, Double.parseDouble(employee.getSalary()));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error adding employee: " + e.getMessage(), e);
        }
    }
}