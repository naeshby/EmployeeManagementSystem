package gui;

import Operations.EmployeeAdd;
import Operations.EmployeeInfo;

import javax.swing.*;
import java.awt.*;

public class AddEmployeePanel extends BasePanel {
    private JTextField nameField, idField, contactField, emailField, salaryField;
    private JComboBox<String> positionComboBox;
    private JButton submitButton, clearButton;

    // Predefined positions
    private final String[] PREDEFINED_POSITIONS = {
            "Select Position",
            "Software Engineer",
            "Senior Developer",
            "Project Manager",
            "Business Analyst",
            "Quality Assurance",
            "DevOps Engineer",
            "Data Scientist",
            "UI/UX Designer",
            "System Administrator",
            "Technical Lead"
    };

    public AddEmployeePanel(MainFrame mainFrame) {
        super(mainFrame);
        initializePanel();
    }

    private void initializePanel() {
        // Title with icon
        JLabel titleLabel = createTitleLabel("Add New Employee", new Color(0, 100, 0), mainFrame.getAddIcon());
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
        salaryField = createTextField();

        // Create position combo box
        positionComboBox = createStyledComboBox();

        // Add form fields
        addFormField(formPanel, "Name *:", nameField, 0);
        addFormField(formPanel, "Employee ID *:", idField, 1);
        addFormField(formPanel, "Contact:", contactField, 2);
        addFormField(formPanel, "Email:", emailField, 3);
        addFormFieldWithComboBox(formPanel, "Position *:", positionComboBox, 4);
        addFormField(formPanel, "Salary *:", salaryField, 5);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(mainFrame.getBackgroundColor());

        submitButton = createActionButton("Add Employee", new Color(0, 150, 0));
        clearButton = createActionButton("Clear Form", new Color(200, 0, 0));

        submitButton.addActionListener(e -> addEmployee());
        clearButton.addActionListener(e -> clearForm());

        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(PREDEFINED_POSITIONS);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(250, 35));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return comboBox;
    }

    // Helper method for combo box fields
    private void addFormFieldWithComboBox(JPanel formPanel, String label, JComboBox<String> comboBox, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 15);

        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jLabel.setForeground(mainFrame.getForegroundColor());
        formPanel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(comboBox, gbc);
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

            // Validate position selection
            String selectedPosition = (String) positionComboBox.getSelectedItem();
            if (selectedPosition == null || selectedPosition.equals("Select Position")) {
                showMessage("Please select a position from the list",
                        "Position Required", JOptionPane.WARNING_MESSAGE);
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
            employee.setPosition(selectedPosition);
            employee.setSalary(salaryField.getText().trim());

            // Add to database
            EmployeeAdd employeeAdd = new EmployeeAdd();
            boolean success = employeeAdd.createEmployee(employee);

            if (success) {
                showMessage("Employee added successfully!\n\n" +
                                "Name: " + employee.getName() + "\n" +
                                "ID: " + employee.getEmployeeID() + "\n" +
                                "Position: " + selectedPosition + "\n" +
                                "Salary: $" + String.format("%,.2f", Double.parseDouble(employee.getSalary())),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();

                // Refresh dashboard data
                mainFrame.refreshDashboardData();
            }

        } catch (Exception ex) {
            showMessage("Error adding employee: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        idField.setText("");
        contactField.setText("");
        emailField.setText("");
        positionComboBox.setSelectedIndex(0);
        salaryField.setText("");
        nameField.requestFocus();
    }
}