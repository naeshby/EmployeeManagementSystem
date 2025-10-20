package GUI;

import Operations.EmployeeShow;

import javax.swing.*;
import java.awt.*;

public class ViewEmployeePanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;

    public ViewEmployeePanel() {
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Employee ID:"));
        searchField = new JTextField(15);
        searchButton = new JButton("Search");

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Result area
        resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listener
        searchButton.addActionListener(e -> searchEmployee());
        searchField.addActionListener(e -> searchEmployee());
    }

    private void searchEmployee() {
        String employeeID = searchField.getText().trim();
        if (employeeID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Employee ID",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // You'll need to modify EmployeeShow to return data instead of printing
        EmployeeShow viewer = new EmployeeShow();
        String result = viewer.getEmployeeDetails(employeeID);
        resultArea.setText(result);
    }
}