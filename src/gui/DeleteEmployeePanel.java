package gui;

import Operations.EmployeeShow;
import Operations.RemoveEmployee;

import javax.swing.*;
import java.awt.*;

public class DeleteEmployeePanel extends BasePanel {
    private JTextField searchField, confirmField;
    private JButton searchButton, deleteButton, clearButton;
    private JTextArea employeeInfoArea;
    private JScrollPane infoScrollPane;
    private String currentEmployeeID;

    public DeleteEmployeePanel(MainFrame mainFrame) {
        super(mainFrame);
        initializePanel();
    }

    private void initializePanel() {
        // Title
        JLabel titleLabel = createTitleLabel("🗑️ Delete Employee", new Color(220, 20, 60));
        add(titleLabel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        contentPanel.setBackground(mainFrame.getBackgroundColor());

        // Search panel
        JPanel searchPanel = createSearchPanel();
        // Employee info panel
        JPanel infoPanel = createInfoPanel();
        // Delete confirmation panel
        JPanel deletePanel = createDeletePanel();

        contentPanel.add(searchPanel);
        contentPanel.add(infoPanel);
        contentPanel.add(deletePanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        updateTitledBorder(panel, "Find Employee to Delete");
        panel.setBackground(mainFrame.getPanelBackground());

        JLabel searchLabel = new JLabel("Enter Employee ID:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchLabel.setForeground(mainFrame.getForegroundColor());

        searchField = createTextField();
        searchField.setPreferredSize(new Dimension(200, 35));

        searchButton = createActionButton("Find Employee", new Color(70, 130, 180));

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);

        // Add action listener
        searchButton.addActionListener(e -> findEmployee());
        searchField.addActionListener(e -> findEmployee());

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        updateTitledBorder(panel, "Employee Details");
        panel.setBackground(mainFrame.getPanelBackground());

        employeeInfoArea = new JTextArea(8, 50);
        employeeInfoArea.setEditable(false);
        employeeInfoArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        employeeInfoArea.setBackground(mainFrame.getTextAreaBackground());
        employeeInfoArea.setForeground(mainFrame.getTextAreaForeground());
        employeeInfoArea.setText("Please search for an employee to view their details...");

        infoScrollPane = new JScrollPane(employeeInfoArea);
        panel.add(infoScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        updateTitledBorder(panel, "Confirm Deletion");
        panel.setBackground(mainFrame.getPanelBackground());

        JPanel confirmPanel = new JPanel(new FlowLayout());
        confirmPanel.setBackground(mainFrame.getPanelBackground());

        JLabel confirmLabel = new JLabel("Type 'DELETE' to confirm:");
        confirmLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmLabel.setForeground(mainFrame.getForegroundColor());

        confirmField = createTextField();
        confirmField.setPreferredSize(new Dimension(150, 35));
        confirmField.setEnabled(false);

        confirmPanel.add(confirmLabel);
        confirmPanel.add(confirmField);

        panel.add(confirmPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(mainFrame.getPanelBackground());

        deleteButton = createActionButton("Delete Employee", new Color(220, 20, 60));
        deleteButton.setEnabled(false);

        clearButton = createActionButton("Clear", new Color(100, 100, 100));

        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add listeners
        deleteButton.addActionListener(e -> deleteEmployee());
        clearButton.addActionListener(e -> clearForm());

        confirmField.addActionListener(e -> {
            if ("DELETE".equalsIgnoreCase(confirmField.getText().trim())) {
                deleteButton.setEnabled(true);
            } else {
                deleteButton.setEnabled(false);
            }
        });

        return panel;
    }

    @Override
    public void applyTheme(boolean darkMode) {
        setBackground(mainFrame.getBackgroundColor());

        // Apply theme to all components
        for (Component comp : getComponents()) {
            applyThemeToComponent(comp, darkMode);
        }

        // Update text area
        employeeInfoArea.setBackground(mainFrame.getTextAreaBackground());
        employeeInfoArea.setForeground(mainFrame.getTextAreaForeground());
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
            employeeInfoArea.setText(result);

            if (result.contains("not found")) {
                currentEmployeeID = null;
                confirmField.setEnabled(false);
                deleteButton.setEnabled(false);
            } else {
                currentEmployeeID = employeeID;
                confirmField.setEnabled(true);
                confirmField.setText("");
                deleteButton.setEnabled(false);
            }

        } catch (Exception ex) {
            showMessage("❌ Error finding employee: " + ex.getMessage(),
                    "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        if (currentEmployeeID == null) {
            showMessage("Please find an employee first",
                    "No Employee Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!"DELETE".equalsIgnoreCase(confirmField.getText().trim())) {
            showMessage("Please type 'DELETE' to confirm",
                    "Confirmation Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // FIXED: Removed the 'this' parameter from showConfirmDialog call
        int confirmation = showConfirmDialog(
                "Are you sure you want to delete employee " + currentEmployeeID + "?\n" +
                        "This action cannot be undone!",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                RemoveEmployee remover = new RemoveEmployee();
                boolean success = remover.removeEmployee(currentEmployeeID);

                if (success) {
                    showMessage("✅ Employee " + currentEmployeeID + " deleted successfully!",
                            "Deletion Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                }

            } catch (Exception ex) {
                showMessage("❌ Error deleting employee: " + ex.getMessage(),
                        "Deletion Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clearForm() {
        searchField.setText("");
        employeeInfoArea.setText("Please search for an employee to view their details...");
        confirmField.setText("");
        confirmField.setEnabled(false);
        deleteButton.setEnabled(false);
        currentEmployeeID = null;
        searchField.requestFocus();
    }
}