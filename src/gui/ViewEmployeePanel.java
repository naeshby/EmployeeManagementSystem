package gui;

import Operations.EmployeeShow;

import javax.swing.*;
import java.awt.*;

public class ViewEmployeePanel extends BasePanel {
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;
    private JScrollPane scrollPane;

    public ViewEmployeePanel(MainFrame mainFrame) {
        super(mainFrame);
        initializePanel();
    }

    private void initializePanel() {
        // Title with icon
        JLabel titleLabel = createTitleLabel("View Employee Details", new Color(70, 130, 180), mainFrame.getViewIcon());
        add(titleLabel, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(mainFrame.getBackgroundColor());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JLabel searchLabel = new JLabel("Enter Employee ID:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchLabel.setForeground(mainFrame.getForegroundColor());

        searchField = createTextField();
        searchField.setPreferredSize(new Dimension(200, 35));

        searchButton = createActionButton("Search Employee", new Color(70, 130, 180));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.CENTER);

        // Result area
        resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setBackground(mainFrame.getTextAreaBackground());
        resultArea.setForeground(mainFrame.getTextAreaForeground());
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Create a panel for the scrollpane
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(mainFrame.getBackgroundColor());
        updateTitledBorder(resultPanel, "Employee Details");
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        add(resultPanel, BorderLayout.SOUTH);

        // Add action listeners
        searchButton.addActionListener(e -> searchEmployee());
        searchField.addActionListener(e -> searchEmployee());
    }

    private void searchEmployee() {
        String employeeID = searchField.getText().trim();
        if (employeeID.isEmpty()) {
            showMessage("Please enter Employee ID to search",
                    "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            EmployeeShow viewer = new EmployeeShow();
            String result = viewer.getEmployeeDetails(employeeID);
            resultArea.setText(result);

            // Scroll to top
            resultArea.setCaretPosition(0);

        } catch (Exception ex) {
            showMessage("Error searching employee: " + ex.getMessage(),
                    "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearResults() {
        searchField.setText("");
        resultArea.setText("");
        searchField.requestFocus();
    }
}