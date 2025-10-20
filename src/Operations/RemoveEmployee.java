package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveEmployee {
    public void removeEmployee(String employeeID) {
        String query = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employeeID);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Employee removed successfully.");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error removing employee: " + e.getMessage());
        }
    }
}

