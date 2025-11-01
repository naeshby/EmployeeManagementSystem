package gui;

import Operations.DatabaseOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel dashboardPanel;

    // Colors
    private Color backgroundColor = new Color(240, 245, 255);
    private Color foregroundColor = Color.BLACK;
    private Color panelBackground = Color.WHITE;
    private Color borderColor = new Color(200, 200, 200);
    private Color accentColor = new Color(70, 130, 180);
    private Color sidebarBackground = new Color(240, 240, 240);
    private Color sidebarForeground = Color.BLACK;

    // Dashboard stat components
    private JLabel totalEmployeesValue;
    private JLabel averageSalaryValue;
    private JLabel departmentsValue;
    private JLabel topPerformersValue;

    // Icons
    private ImageIcon dashboardIcon, addIcon, viewIcon, updateIcon, deleteIcon, refreshIcon, exitIcon;
    private ImageIcon employeeIcon, salaryIcon, departmentIcon, starIcon;

    public MainFrame() {
        setTitle("Employee Management System - By Naeshby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        loadIcons();
        initializeUI();
    }

    private void loadIcons() {
        try {
            // Load sidebar icons
            dashboardIcon = createScaledIcon("/icons/dashboard.png", 20, 20);
            addIcon = createScaledIcon("/icons/add.png", 20, 20);
            viewIcon = createScaledIcon("/icons/view.png", 20, 20);
            updateIcon = createScaledIcon("/icons/update.png", 20, 20);
            deleteIcon = createScaledIcon("/icons/delete.png", 20, 20);
            refreshIcon = createScaledIcon("/icons/refresh.png", 20, 20);
            exitIcon = createScaledIcon("/icons/exit.png", 20, 20);

            // Load dashboard stat icons
            employeeIcon = createScaledIcon("/icons/employee.png", 65, 65);
            salaryIcon = createScaledIcon("/icons/salary.png", 65, 65);
            departmentIcon = createScaledIcon("/icons/department.png", 65, 65);
            starIcon = createScaledIcon("/icons/star.png", 65, 65);

        } catch (Exception e) {
            System.err.println("Error loading icons: " + e.getMessage());
        }
    }

    private ImageIcon createScaledIcon(String path, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Failed to load icon: " + path);
            return null;
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Create main content area
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        AddEmployeePanel addPanel = new AddEmployeePanel(this);
        ViewEmployeePanel viewPanel = new ViewEmployeePanel(this);
        UpdateEmployeePanel updatePanel = new UpdateEmployeePanel(this);
        DeleteEmployeePanel deletePanel = new DeleteEmployeePanel(this);

        // Create dashboard panel
        dashboardPanel = createDashboardPanel();

        mainPanel.add(dashboardPanel, "DASHBOARD");
        mainPanel.add(addPanel, "ADD");
        mainPanel.add(viewPanel, "VIEW");
        mainPanel.add(updatePanel, "UPDATE");
        mainPanel.add(deletePanel, "DELETE");

        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "DASHBOARD");
        refreshDashboardData();
    }

    public void refreshDashboardData() {
        if (dashboardPanel != null) {
            updateDashboardStats();
        }
    }

    private void updateDashboardStats() {
        int totalEmployees = DatabaseOperations.getTotalEmployees();
        double avgSalary = DatabaseOperations.getAverageSalary();
        int deptCount = DatabaseOperations.getDepartmentCount();
        int topPerformers = DatabaseOperations.getTopPerformersCount();

        if (totalEmployeesValue != null) totalEmployeesValue.setText(String.valueOf(totalEmployees));
        if (averageSalaryValue != null) averageSalaryValue.setText(String.format("$%,.2f", avgSalary));
        if (departmentsValue != null) departmentsValue.setText(String.valueOf(deptCount));
        if (topPerformersValue != null) topPerformersValue.setText(String.valueOf(topPerformers));
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBackground(sidebarBackground);

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(sidebarBackground);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JLabel header = new JLabel("EMS");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(sidebarForeground);
        headerPanel.add(header);
        sidebar.add(headerPanel);

        // Navigation buttons
        JButton[] navButtons = {
                createNavButton("Dashboard", "DASHBOARD", dashboardIcon),
                createNavButton("Add Employee", "ADD", addIcon),
                createNavButton("View Employee", "VIEW", viewIcon),
                createNavButton("Update Employee", "UPDATE", updateIcon),
                createNavButton("Delete Employee", "DELETE", deleteIcon),
                createNavButton("Refresh Data", "REFRESH", refreshIcon),
                createNavButton("Exit", "EXIT", exitIcon)
        };

        for (JButton button : navButtons) {
            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        sidebar.add(Box.createVerticalGlue());

        // Footer
        JLabel footer = new JLabel("By Naeshby");
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setForeground(sidebarForeground);
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        sidebar.add(footer);

        return sidebar;
    }

    private JButton createNavButton(String text, String command, ImageIcon icon) {
        JButton button = new JButton(text);

        if (icon != null) {
            button.setIcon(icon);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setIconTextGap(10);
        }

        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(sidebarForeground);
        button.setBackground(sidebarBackground);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(sidebarBackground);
            }
        });

        button.setActionCommand(command);
        button.addActionListener(new NavButtonListener());

        return button;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(backgroundColor);
        dashboard.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Header
        JLabel header = new JLabel("Employee Management Dashboard", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.setForeground(accentColor);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        dashboard.add(header, BorderLayout.NORTH);

        // Stats cards
        JPanel statsPanel = createStatsPanel();
        dashboard.add(statsPanel, BorderLayout.CENTER);

        // Quick actions
        JPanel quickActionsPanel = createQuickActionsPanel();
        dashboard.add(quickActionsPanel, BorderLayout.SOUTH);

        return dashboard;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBackground(backgroundColor);

        int totalEmployees = DatabaseOperations.getTotalEmployees();
        double avgSalary = DatabaseOperations.getAverageSalary();
        int deptCount = DatabaseOperations.getDepartmentCount();
        int topPerformers = DatabaseOperations.getTopPerformersCount();

        JPanel totalEmployeesCard = createStatCard(employeeIcon, "Total Employees",
                String.valueOf(totalEmployees), new Color(70, 130, 180));
        JPanel avgSalaryCard = createStatCard(salaryIcon, "Average Salary",
                String.format("$%,.2f", avgSalary), new Color(60, 179, 113));
        JPanel departmentsCard = createStatCard(departmentIcon, "Departments",
                String.valueOf(deptCount), new Color(255, 165, 0));
        JPanel topPerformersCard = createStatCard(starIcon, "Top Performers",
                String.valueOf(topPerformers), new Color(220, 20, 60));

        totalEmployeesValue = (JLabel) ((JPanel) totalEmployeesCard.getComponent(0)).getComponent(2);
        averageSalaryValue = (JLabel) ((JPanel) avgSalaryCard.getComponent(0)).getComponent(2);
        departmentsValue = (JLabel) ((JPanel) departmentsCard.getComponent(0)).getComponent(2);
        topPerformersValue = (JLabel) ((JPanel) topPerformersCard.getComponent(0)).getComponent(2);

        statsPanel.add(totalEmployeesCard);
        statsPanel.add(avgSalaryCard);
        statsPanel.add(departmentsCard);
        statsPanel.add(topPerformersCard);

        return statsPanel;
    }

    private JPanel createStatCard(ImageIcon icon, String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLabel = new JLabel();
        if (icon != null) {
            iconLabel.setIcon(icon);
        }
        iconLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(iconLabel, BorderLayout.NORTH);
        contentPanel.add(titleLabel, BorderLayout.CENTER);
        contentPanel.add(valueLabel, BorderLayout.SOUTH);

        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createQuickActionsPanel() {
        JPanel quickActions = new JPanel(new GridLayout(1, 4, 15, 15));
        quickActions.setBackground(backgroundColor);
        quickActions.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        JButton[] actionButtons = {
                createQuickActionButton("Add New", "ADD", addIcon, new Color(60, 179, 113)),
                createQuickActionButton("Find", "VIEW", viewIcon, new Color(70, 130, 180)),
                createQuickActionButton("Update", "UPDATE", updateIcon, new Color(255, 165, 0)),
                createQuickActionButton("Remove", "DELETE", deleteIcon, new Color(220, 20, 60))
        };

        for (JButton button : actionButtons) {
            quickActions.add(button);
        }

        return quickActions;
    }

    private JButton createQuickActionButton(String text, String command, ImageIcon icon, Color color) {
        JButton button = new JButton(text);

        if (icon != null) {
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
        }

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        button.setActionCommand(command);
        button.addActionListener(new NavButtonListener());

        return button;
    }

    private class NavButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "DASHBOARD":
                    cardLayout.show(mainPanel, "DASHBOARD");
                    refreshDashboardData();
                    break;
                case "ADD":
                    cardLayout.show(mainPanel, "ADD");
                    break;
                case "VIEW":
                    cardLayout.show(mainPanel, "VIEW");
                    ((ViewEmployeePanel) mainPanel.getComponent(2)).clearResults();
                    break;
                case "UPDATE":
                    cardLayout.show(mainPanel, "UPDATE");
                    ((UpdateEmployeePanel) mainPanel.getComponent(3)).clearForm();
                    break;
                case "DELETE":
                    cardLayout.show(mainPanel, "DELETE");
                    ((DeleteEmployeePanel) mainPanel.getComponent(4)).clearForm();
                    break;
                case "REFRESH":
                    refreshDashboardData();
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Dashboard data refreshed!", "Data Updated", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "EXIT":
                    new Operations.Exit().exit();
                    break;
            }
        }
    }

    // Getters for colors and icons
    public Color getBackgroundColor() { return backgroundColor; }
    public Color getForegroundColor() { return foregroundColor; }
    public Color getPanelBackground() { return panelBackground; }
    public Color getBorderColor() { return borderColor; }
    public Color getTextAreaBackground() { return new Color(248, 248, 248); }
    public Color getTextAreaForeground() { return Color.BLACK; }

    public ImageIcon getAddIcon() { return addIcon; }
    public ImageIcon getViewIcon() { return viewIcon; }
    public ImageIcon getUpdateIcon() { return updateIcon; }
    public ImageIcon getDeleteIcon() { return deleteIcon; }
}