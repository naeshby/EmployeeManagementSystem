package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initializeUI();
    }

    private void initializeUI() {
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu operationsMenu = new JMenu("Operations");

        JMenuItem addEmployeeItem = new JMenuItem("Add Employee");
        JMenuItem viewEmployeeItem = new JMenuItem("View Employee");
        JMenuItem updateEmployeeItem = new JMenuItem("Update Employee");
        JMenuItem deleteEmployeeItem = new JMenuItem("Delete Employee");
        JMenuItem exitItem = new JMenuItem("Exit");

        operationsMenu.add(addEmployeeItem);
        operationsMenu.add(viewEmployeeItem);
        operationsMenu.add(updateEmployeeItem);
        operationsMenu.add(deleteEmployeeItem);
        operationsMenu.addSeparator();
        operationsMenu.add(exitItem);
        menuBar.add(operationsMenu);
        setJMenuBar(menuBar);

        // Create card layout for different panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create different panels
        AddEmployeePanel addPanel = new AddEmployeePanel();
        ViewEmployeePanel viewPanel = new ViewEmployeePanel();
        UpdateEmployeePanel updatePanel = new UpdateEmployeePanel();
        DeleteEmployeePanel deletePanel = new DeleteEmployeePanel();

        mainPanel.add(addPanel, "ADD");
        mainPanel.add(viewPanel, "VIEW");
        mainPanel.add(updatePanel, "UPDATE");
        mainPanel.add(deletePanel, "DELETE");

        add(mainPanel);

        // Add action listeners for menu items
        addEmployeeItem.addActionListener(e -> cardLayout.show(mainPanel, "ADD"));
        viewEmployeeItem.addActionListener(e -> cardLayout.show(mainPanel, "VIEW"));
        updateEmployeeItem.addActionListener(e -> cardLayout.show(mainPanel, "UPDATE"));
        deleteEmployeeItem.addActionListener(e -> cardLayout.show(mainPanel, "DELETE"));
        exitItem.addActionListener(e -> System.exit(0));
    }
}