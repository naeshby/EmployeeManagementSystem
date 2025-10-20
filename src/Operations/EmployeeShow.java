package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeShow {
    public String getEmployeeDetails(String employeeID) {
        if (employeeID == null || employeeID.trim().isEmpty()) {
            return "Error: Employee ID cannot be empty";
        }

        String query = "SELECT * FROM employees WHERE employee_id = ?";
        StringBuilder result = new StringBuilder();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employeeID.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.append("=== EMPLOYEE DETAILS ===\n\n");
                result.append(String.format("%-15s: %s\n", "Employee ID", rs.getString("employee_id")));
                result.append(String.format("%-15s: %s\n", "Name", rs.getString("name")));
                result.append(String.format("%-15s: %s\n", "Contact", rs.getString("contact")));
                result.append(String.format("%-15s: %s\n", "Email", rs.getString("email")));
                result.append(String.format("%-15s: %s\n", "Position", rs.getString("position")));
                result.append(String.format("%-15s: $%.2f\n", "Salary", rs.getDouble("salary")));
            } else {
                result.append("❌ Employee not found with ID: " + employeeID);
            }

        } catch (SQLException e) {
            result.append("❌ Database error: ").append(e.getMessage());
        }

        return result.toString();
    }

    public EmployeeInfo getEmployeeObject(String employeeID) {
        if (employeeID == null || employeeID.trim().isEmpty()) {
            return null;
        }

        String query = "SELECT * FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employeeID.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new EmployeeInfo(
                        rs.getString("employee_id"),
                        rs.getString("name"),
                        rs.getString("contact"),
                        rs.getString("email"),
                        rs.getString("position"),
                        String.valueOf(rs.getDouble("salary"))
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching employee: " + e.getMessage(), e);
        }

        return null;
    }
}