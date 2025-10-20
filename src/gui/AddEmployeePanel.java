package gui;

import Operations.EmployeeAdd;
import Operations.EmployeeInfo;

import javax.swing.*;
import java.awt.*;

public class AddEmployeePanel extends BasePanel {
    private JTextField nameField, idField, contactField, emailField, positionField, salaryField;
    private JButton submitButton, clearButton;

    public AddEmployeePanel(MainFrame mainFrame) {
        super(mainFrame);
        initializePanel();
    }

    private void initializePanel() {
        // Title
        JLabel titleLabel = createTitleLabel("Add New Employee", new Color(60, 179, 113));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = createFormPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Initialize fields
        nameField = createTextField();
        idField = createTextField();
        contactField = createTextField();
        emailField = createTextField();
        positionField = createTextField();
        salaryField = createTextField();

        // Add form fields
        addFormField(formPanel, "Name *:", nameField, 0);
        addFormField(formPanel, "Employee ID *:", idField, 1);
        addFormField(formPanel, "Contact:", contactField, 2);
        addFormField(formPanel, "Email:", emailField, 3);
        addFormField(formPanel, "Position:", positionField, 4);
        addFormField(formPanel, "Salary *:", salaryField, 5);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(mainFrame.getBackgroundColor());

        submitButton = createActionButton("Add Employee", new Color(60, 179, 113));
        clearButton = createActionButton("Clear Form", new Color(220, 20, 60));

        submitButton.addActionListener(e -> addEmployee());
        clearButton.addActionListener(e -> clearForm());

        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void applyTheme(boolean darkMode) {
        setBackground(mainFrame.getBackgroundColor());

        // Apply theme to all components
        for (Component comp : getComponents()) {
            applyThemeToComponent(comp, darkMode);
        }

        repaint();
    }

    private void addEmployee() {
        try {
            // Validate required fields
            if (nameField.getText().trim().isEmpty() ||
                    idField.getText().trim().isEmpty() ||
                    salaryField.getText().trim().isEmpty()) {
                showMessage("Please fill all required fields (*)",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate salary format
            try {
                Double.parseDouble(salaryField.getText().trim());
            } catch (NumberFormatException e) {
                showMessage("Please enter a valid salary amount",
                        "Invalid Salary", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create employee info
            EmployeeInfo employee = new EmployeeInfo();
            employee.setName(nameField.getText().trim());
            employee.setEmployeeID(idField.getText().trim());
            employee.setContact(contactField.getText().trim());
            employee.setEmail(emailField.getText().trim());
            employee.setPosition(positionField.getText().trim());
            employee.setSalary(salaryField.getText().trim());

            // Add to database
            EmployeeAdd employeeAdd = new EmployeeAdd();
            boolean success = employeeAdd.createEmployee(employee);

            if (success) {
                showMessage("✅ Employee added successfully!\n\n" +
                                "Name: " + employee.getName() + "\n" +
                                "ID: " + employee.getEmployeeID() + "\n" +
                                "Position: " + employee.getPosition(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            }

        } catch (Exception ex) {
            showMessage("❌ Error adding employee: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        idField.setText("");
        contactField.setText("");
        emailField.setText("");
        positionField.setText("");
        salaryField.setText("");
        nameField.requestFocus();
    }
}