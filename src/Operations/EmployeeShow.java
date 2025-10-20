package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeShow {
    public String getEmployeeDetails(String employeeID) {
        String query = "SELECT * FROM employees WHERE employee_id = ?";
        StringBuilder result = new StringBuilder();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employeeID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.append("--- Employee Details ---\n");
                result.append("ID: ").append(rs.getString("employee_id")).append("\n");
                result.append("Name: ").append(rs.getString("name")).append("\n");
                result.append("Contact: ").append(rs.getString("contact")).append("\n");
                result.append("Email: ").append(rs.getString("email")).append("\n");
                result.append("Position: ").append(rs.getString("position")).append("\n");
                result.append("Salary: ").append(rs.getDouble("salary")).append("\n");
            } else {
                result.append("Employee not found.");
            }

        } catch (SQLException e) {
            result.append("Error: ").append(e.getMessage());
        }

        return result.toString();
    }
}
