package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeUpdate {
    public boolean updateEmployee(String employeeID, String field, String newValue) {
        if (employeeID == null || employeeID.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty");
        }

        // Validate field name
        String[] allowedFields = {"name", "contact", "email", "position", "salary"};
        boolean validField = false;
        for (String allowedField : allowedFields) {
            if (allowedField.equalsIgnoreCase(field)) {
                validField = true;
                field = allowedField; // Use lowercase for consistency
                break;
            }
        }

        if (!validField) {
            throw new IllegalArgumentException("Invalid field name: " + field);
        }

        // Validate salary if updating salary field
        if ("salary".equals(field)) {
            try {
                Double.parseDouble(newValue);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid salary format");
            }
        }

        String query = "UPDATE employees SET " + field + " = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newValue);
            stmt.setString(2, employeeID);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee: " + e.getMessage(), e);
        }
    }

    public boolean updateEmployee(EmployeeInfo employee) {
        if (!employee.isValid()) {
            throw new IllegalArgumentException("Invalid employee data");
        }

        String query = "UPDATE employees SET name = ?, contact = ?, email = ?, position = ?, salary = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getContact());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPosition());
            stmt.setDouble(5, Double.parseDouble(employee.getSalary()));
            stmt.setString(6, employee.getEmployeeID());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee: " + e.getMessage(), e);
        }
    }
}