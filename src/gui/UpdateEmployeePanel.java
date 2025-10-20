package gui;

import Operations.EmployeeShow;
import Operations.EmployeeUpdate;
import Operations.EmployeeInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class UpdateEmployeePanel extends BasePanel {
    private JTextField searchField, updateField;
    private JButton searchButton, updateButton, clearButton;
    private JComboBox<String> fieldComboBox;
    private JTextArea currentInfoArea;
    private JScrollPane infoScrollPane;
    private String currentEmployeeID;

    public UpdateEmployeePanel(MainFrame mainFrame) {
        super(mainFrame);
        initializePanel();
    }

    private void initializePanel() {
        // Title
        JLabel titleLabel = createTitleLabel("✏️ Update Employee Information", new Color(255, 165, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        contentPanel.setBackground(mainFrame.getBackgroundColor());

        // Search panel
        JPanel searchPanel = createSearchPanel();
        // Update panel
        JPanel updatePanel = createUpdatePanel();

        contentPanel.add(searchPanel);
        contentPanel.add(updatePanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        updateTitledBorder(panel, "Find Employee");
        panel.setBackground(mainFrame.getPanelBackground());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(mainFrame.getPanelBackground());

        JLabel searchLabel = new JLabel("Employee ID:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchLabel.setForeground(mainFrame.getForegroundColor());

        searchField = createTextField();
        searchField.setPreferredSize(new Dimension(200, 35));

        searchButton = createActionButton("Find Employee", new Color(70, 130, 180));

        inputPanel.add(searchLabel);
        inputPanel.add(searchField);
        inputPanel.add(searchButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Current info area
        currentInfoArea = new JTextArea(8, 50);
        currentInfoArea.setEditable(false);
        currentInfoArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        currentInfoArea.setBackground(mainFrame.getTextAreaBackground());
        currentInfoArea.setForeground(mainFrame.getTextAreaForeground());
        infoScrollPane = new JScrollPane(currentInfoArea);

        panel.add(infoScrollPane, BorderLayout.CENTER);

        // Add action listener
        searchButton.addActionListener(e -> findEmployee());
        searchField.addActionListener(e -> findEmployee());

        return panel;
    }

    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        updateTitledBorder(panel, "Update Information");
        panel.setBackground(mainFrame.getPanelBackground());

        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setBackground(mainFrame.getPanelBackground());

        JLabel fieldLabel = new JLabel("Field to update:");
        fieldLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldLabel.setForeground(mainFrame.getForegroundColor());

        String[] fields = {"Select Field", "name", "contact", "email", "position", "salary"};
        fieldComboBox = new JComboBox<>(fields);
        fieldComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldComboBox.setPreferredSize(new Dimension(120, 35));

        JLabel valueLabel = new JLabel("New value:");
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueLabel.setForeground(mainFrame.getForegroundColor());

        updateField = createTextField();
        updateField.setPreferredSize(new Dimension(200, 35));
        updateField.setEnabled(false);

        formPanel.add(fieldLabel);
        formPanel.add(fieldComboBox);
        formPanel.add(valueLabel);
        formPanel.add(updateField);

        panel.add(formPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(mainFrame.getPanelBackground());

        updateButton = createActionButton("Update", new Color(60, 179, 113));
        updateButton.setEnabled(false);

        clearButton = createActionButton("Clear", new Color(220, 20, 60));

        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add listeners
        fieldComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selected = (String) fieldComboBox.getSelectedItem();
                updateField.setEnabled(!"Select Field".equals(selected));
                updateField.setText("");
            }
        });

        updateButton.addActionListener(e -> updateEmployee());
        clearButton.addActionListener(e -> clearUpdateForm());

        return panel;
    }

    @Override
    public void applyTheme(boolean darkMode) {
        setBackground(mainFrame.getBackgroundColor());

        // Apply theme to all components
        for (Component comp : getComponents()) {
            applyThemeToComponent(comp, darkMode);
        }

        // Update text areas
        currentInfoArea.setBackground(mainFrame.getTextAreaBackground());
        currentInfoArea.setForeground(mainFrame.getTextAreaForeground());
        if (infoScrollPane != null) {
            infoScrollPane.getViewport().setBackground(mainFrame.getTextAreaBackground());
        }

        repaint();
    }

    private void findEmployee() {
        String employeeID = searchField.getText().trim();
        if (employeeID.isEmpty()) {
            showMessage("Please enter Employee ID",
                    "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            EmployeeShow viewer = new EmployeeShow();
            String result = viewer.getEmployeeDetails(employeeID);
            currentInfoArea.setText(result);

            if (result.contains("not found")) {
                currentEmployeeID = null;
                updateButton.setEnabled(false);
            } else {
                currentEmployeeID = employeeID;
                updateButton.setEnabled(true);
            }

        } catch (Exception ex) {
            showMessage("❌ Error finding employee: " + ex.getMessage(),
                    "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee() {
        if (currentEmployeeID == null) {
            showMessage("Please find an employee first",
                    "No Employee Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String field = (String) fieldComboBox.getSelectedItem();
        String newValue = updateField.getText().trim();

        if ("Select Field".equals(field) || newValue.isEmpty()) {
            showMessage("Please select a field and enter new value",
                    "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate salary if updating salary
        if ("salary".equals(field)) {
            try {
                Double.parseDouble(newValue);
            } catch (NumberFormatException e) {
                showMessage("Please enter a valid salary amount",
                        "Invalid Salary", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            EmployeeUpdate updater = new EmployeeUpdate();
            boolean success = updater.updateEmployee(currentEmployeeID, field, newValue);

            if (success) {
                showMessage("✅ Employee updated successfully!\n\n" +
                                "Field: " + field + "\n" +
                                "New Value: " + newValue,
                        "Update Successful", JOptionPane.INFORMATION_MESSAGE);

                // Refresh the displayed info
                findEmployee();
                clearUpdateForm();
            }

        } catch (Exception ex) {
            showMessage("❌ Error updating employee: " + ex.getMessage(),
                    "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearUpdateForm() {
        fieldComboBox.setSelectedIndex(0);
        updateField.setText("");
        updateField.setEnabled(false);
    }

    public void clearForm() {
        searchField.setText("");
        currentInfoArea.setText("");
        clearUpdateForm();
        currentEmployeeID = null;
        updateButton.setEnabled(false);
        searchField.requestFocus();
    }
}