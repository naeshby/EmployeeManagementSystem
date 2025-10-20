package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveEmployee {
    public boolean removeEmployee(String employeeID) {
        if (employeeID == null || employeeID.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty");
        }

        String query = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employeeID.trim());
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error removing employee: " + e.getMessage(), e);
        }
    }
}