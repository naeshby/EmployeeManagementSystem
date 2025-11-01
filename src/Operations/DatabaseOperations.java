package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {

    // Get total number of employees
    public static int getTotalEmployees() {
        String query = "SELECT COUNT(*) as total FROM employees";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getting total employees: " + e.getMessage());
        }
        return 0;
    }

    // Get average salary
    public static double getAverageSalary() {
        String query = "SELECT AVG(salary) as average_salary FROM employees";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("average_salary");
            }
        } catch (SQLException e) {
            System.err.println("Error getting average salary: " + e.getMessage());
        }
        return 0.0;
    }

    // Get number of departments (unique positions)
    public static int getDepartmentCount() {
        String query = "SELECT COUNT(DISTINCT position) as dept_count FROM employees WHERE position IS NOT NULL AND position != ''";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("dept_count");
            }
        } catch (SQLException e) {
            System.err.println("Error getting department count: " + e.getMessage());
        }
        return 0;
    }

    // Get top performers (employees with highest salaries)
    public static int getTopPerformersCount() {
        // Consider top 25% of employees by salary as top performers
        String query = "SELECT COUNT(*) as top_count FROM (" +
                "SELECT employee_id, salary FROM employees " +
                "WHERE salary >= (SELECT AVG(salary) * 1.25 FROM employees)" +
                ") as top_employees";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("top_count");
            }
        } catch (SQLException e) {
            System.err.println("Error getting top performers count: " + e.getMessage());
        }
        return 0;
    }

    // Get list of all departments (positions)
    public static List<String> getDepartments() {
        List<String> departments = new ArrayList<>();
        String query = "SELECT DISTINCT position FROM employees WHERE position IS NOT NULL AND position != '' ORDER BY position";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                departments.add(rs.getString("position"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting departments: " + e.getMessage());
        }
        return departments;
    }
}