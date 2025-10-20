package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeShow {
    public void viewEmployee(String employeeID) {
        String query = "SELECT * FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employeeID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- Employee Details ---");
                System.out.println("ID: " + rs.getString("employee_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Contact: " + rs.getString("contact"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Position: " + rs.getString("position"));
                System.out.println("Salary: " + rs.getDouble("salary"));
            } else {
                System.out.println("❌ Employee not found.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
