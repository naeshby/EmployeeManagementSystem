package GUI;

import Operations.EmployeeAdd;
import Operations.EmployeeInfo;

import javax.swing.*;
import java.awt.*;

public class AddEmployeePanel extends JPanel {
    private JTextField nameField, idField, contactField, emailField, positionField, salaryField;
    private JButton submitButton, clearButton;

    public AddEmployeePanel() {
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Add New Employee", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        nameField = new JTextField();
        idField = new JTextField();
        contactField = new JTextField();
        emailField = new JTextField();
        positionField = new JTextField();
        salaryField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Employee ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionField);
        formPanel.add(new JLabel("Salary:"));
        formPanel.add(salaryField);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        submitButton = new JButton("Add Employee");
        clearButton = new JButton("Clear");

        submitButton.addActionListener(e -> addEmployee());
        clearButton.addActionListener(e -> clearForm());

        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addEmployee() {
        // Validate fields
        if (nameField.getText().trim().isEmpty() ||
                idField.getText().trim().isEmpty() ||
                salaryField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Create employee info
            EmployeeInfo employee = new EmployeeInfo();
            employee.name = nameField.getText().trim();
            employee.employeeID = idField.getText().trim();
            employee.contact = contactField.getText().trim();
            employee.email = emailField.getText().trim();
            employee.position = positionField.getText().trim();
            employee.salary = salaryField.getText().trim();

            // Add to database
            EmployeeAdd employeeAdd = new EmployeeAdd();
            // You'll need to modify EmployeeAdd to accept EmployeeInfo as parameter
            employeeAdd.createEmployee(employee);

            JOptionPane.showMessageDialog(this, "Employee added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        idField.setText("");
        contactField.setText("");
        emailField.setText("");
        positionField.setText("");
        salaryField.setText("");
    }
}