package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeUpdate {
    public void updateEmployee(String employeeID, String field, String newValue) {
        String query = "UPDATE employees SET " + field + " = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newValue);
            stmt.setString(2, employeeID);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Employee updated successfully.");
            } else {
                System.out.println("❌ Employee not found.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error updating employee: " + e.getMessage());
        }
    }
}
