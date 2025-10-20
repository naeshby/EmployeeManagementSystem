package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private boolean darkMode = false;
    private Color lightBackground = new Color(240, 245, 255);
    private Color darkBackground = new Color(45, 45, 55);
    private Color lightForeground = Color.BLACK;
    private Color darkForeground = Color.WHITE;
    private Color lightPanelBg = Color.WHITE;
    private Color darkPanelBg = new Color(60, 63, 65);
    private Color lightBorder = new Color(200, 200, 200);
    private Color darkBorder = new Color(80, 80, 80);
    private Color accentColor = new Color(70, 130, 180);

    public MainFrame() {
        setTitle("Employee Management System - By Naeshby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        initializeUI();
        applyLightTheme();
    }

    private void initializeUI() {
        // Create main layout with sidebar and content area
        setLayout(new BorderLayout());

        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Create main content area with card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create different panels
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

        // Show dashboard initially
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBackground(accentColor);

        // Header
        JLabel header = new JLabel("EMS");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(Color.WHITE);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        sidebar.add(header);

        // Navigation buttons
        String[] navItems = {
                "🏠 Dashboard", "➕ Add Employee", "👁️ View Employee",
                "✏️ Update Employee", "🗑️ Delete Employee", "🌙 Dark Mode", "🚪 Exit"
        };

        String[] commands = {
                "DASHBOARD", "ADD", "VIEW", "UPDATE", "DELETE", "THEME", "EXIT"
        };

        for (int i = 0; i < navItems.length; i++) {
            JButton navButton = createNavButton(navItems[i], commands[i]);
            sidebar.add(navButton);
            sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Add spacer to push everything to top
        sidebar.add(Box.createVerticalGlue());

        // Footer
        JLabel footer = new JLabel("By Naeshby");
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footer.setForeground(Color.WHITE);
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        sidebar.add(footer);

        return sidebar;
    }

    private JButton createNavButton(String text, String command) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(accentColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(accentColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(accentColor);
            }
        });

        button.setActionCommand(command);
        button.addActionListener(new NavButtonListener());

        return button;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(lightBackground);
        dashboard.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Header
        JLabel header = new JLabel("🏠 Employee Management Dashboard", JLabel.CENTER);
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
        statsPanel.setBackground(lightBackground);

        // Create stat cards
        String[] statIcons = {"👥", "💰", "📊", "⭐"};
        String[] statTitles = {"Total Employees", "Average Salary", "Departments", "Top Performers"};
        String[] statValues = {"24", "$65,420", "5", "8"};
        Color[] statColors = {
                new Color(70, 130, 180),
                new Color(60, 179, 113),
                new Color(255, 165, 0),
                new Color(220, 20, 60)
        };

        for (int i = 0; i < 4; i++) {
            JPanel statCard = createStatCard(statIcons[i], statTitles[i], statValues[i], statColors[i]);
            statsPanel.add(statCard);
        }

        return statsPanel;
    }

    private JPanel createStatCard(String icon, String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 36));
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
        quickActions.setBackground(lightBackground);
        quickActions.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        String[] actions = {"➕ Add New", "🔍 Find", "✏️ Update", "🗑️ Remove"};
        String[] commands = {"ADD", "VIEW", "UPDATE", "DELETE"};
        Color[] colors = {
                new Color(60, 179, 113),
                new Color(70, 130, 180),
                new Color(255, 165, 0),
                new Color(220, 20, 60)
        };

        for (int i = 0; i < 4; i++) {
            JButton actionBtn = createQuickActionButton(actions[i], commands[i], colors[i]);
            quickActions.add(actionBtn);
        }

        return quickActions;
    }

    private JButton createQuickActionButton(String text, String command, Color color) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
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
                case "THEME":
                    toggleDarkMode();
                    break;
                case "EXIT":
                    new Operations.Exit().exit();
                    break;
            }
        }
    }

    private void toggleDarkMode() {
        darkMode = !darkMode;
        if (darkMode) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
        refreshAllPanels();

        // Show theme change message
        String theme = darkMode ? "Dark" : "Light";
        showMessage(this, "Switched to " + theme + " Mode 🌙", "Theme Changed", JOptionPane.INFORMATION_MESSAGE);
    }

    private void applyLightTheme() {
        getContentPane().setBackground(lightBackground);
        if (dashboardPanel != null) {
            dashboardPanel.setBackground(lightBackground);
            updateDashboardTheme(false);
        }
    }

    private void applyDarkTheme() {
        getContentPane().setBackground(darkBackground);
        if (dashboardPanel != null) {
            dashboardPanel.setBackground(darkBackground);
            updateDashboardTheme(true);
        }
    }

    private void updateDashboardTheme(boolean darkMode) {
        Component[] components = dashboardPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(darkMode ? darkForeground : lightForeground);
            } else if (comp instanceof JPanel) {
                updatePanelTheme((JPanel) comp, darkMode);
            }
        }
    }

    private void updatePanelTheme(JPanel panel, boolean darkMode) {
        panel.setBackground(darkMode ? darkBackground : lightBackground);
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel childPanel = (JPanel) comp;
                if (childPanel.getComponentCount() > 0) {
                    // Check if this is a stat card
                    Component firstChild = childPanel.getComponent(0);
                    if (firstChild instanceof JPanel) {
                        // It's a stat card, update its background
                        firstChild.setBackground(darkMode ? darkPanelBg : Color.WHITE);
                        updateStatCardTheme((JPanel) firstChild, darkMode);
                    } else if (firstChild instanceof JButton) {
                        // It's a button, keep its original color
                    }
                }
            }
        }
    }

    private void updateStatCardTheme(JPanel card, boolean darkMode) {
        card.setBackground(darkMode ? darkPanelBg : Color.WHITE);
        for (Component comp : card.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel contentPanel = (JPanel) comp;
                contentPanel.setBackground(darkMode ? darkPanelBg : Color.WHITE);
                for (Component contentComp : contentPanel.getComponents()) {
                    if (contentComp instanceof JLabel) {
                        JLabel label = (JLabel) contentComp;
                        if (label.getText().matches("👥|💰|📊|⭐")) {
                            // Keep icon color
                        } else if (label.getForeground().equals(Color.GRAY)) {
                            label.setForeground(darkMode ? Color.LIGHT_GRAY : Color.GRAY);
                        } else {
                            // Keep value color (accent colors)
                        }
                    }
                }
            }
        }
    }

    private void refreshAllPanels() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof BasePanel) {
                ((BasePanel) comp).applyTheme(darkMode);
            }
        }
        repaint();
        revalidate();
    }

    private void showMessage(Component parent, String message, String title, int messageType) {
        if (darkMode) {
            UIManager.put("OptionPane.background", darkPanelBg);
            UIManager.put("Panel.background", darkPanelBg);
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
        }

        JOptionPane.showMessageDialog(parent, message, title, messageType);

        if (darkMode) {
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.messageForeground", null);
        }
    }

    // Getters for theme colors
    public Color getBackgroundColor() {
        return darkMode ? darkBackground : lightBackground;
    }

    public Color getForegroundColor() {
        return darkMode ? darkForeground : lightForeground;
    }

    public Color getPanelBackground() {
        return darkMode ? darkPanelBg : lightPanelBg;
    }

    public Color getBorderColor() {
        return darkMode ? darkBorder : lightBorder;
    }

    public Color getTextAreaBackground() {
        return darkMode ? new Color(43, 43, 43) : new Color(248, 248, 248);
    }

    public Color getTextAreaForeground() {
        return darkMode ? Color.WHITE : Color.BLACK;
    }

    public boolean isDarkMode() {
        return darkMode;
    }
}