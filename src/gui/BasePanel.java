package gui;

import javax.swing.*;
import java.awt.*;

public abstract class BasePanel extends JPanel {
    protected MainFrame mainFrame;

    public BasePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public abstract void applyTheme(boolean darkMode);

    protected JLabel createTitleLabel(String text, Color color) {
        JLabel titleLabel = new JLabel(text, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK); // Force black color for all titles including icons
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        return titleLabel;
    }

    protected JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        updateTitledBorder(formPanel, "Form");
        formPanel.setBackground(mainFrame.getPanelBackground());
        return formPanel;
    }

    // Add the missing method
    protected void updateTitledBorder(JPanel panel, String title) {
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(mainFrame.getBorderColor()),
                        title
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    protected void addFormField(JPanel formPanel, String label, JComponent field, int gridy) {
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
        formPanel.add(field, gbc);
    }

    protected JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(mainFrame.getBorderColor()),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    protected JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color); // Button background color
        button.setForeground(Color.BLACK); // Button text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter()); // Hover background
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color); // Normal background
            }
        });

        return button;
    }

    protected void applyThemeToComponent(Component comp, boolean darkMode) {
        if (comp instanceof JPanel) {
            JPanel panel = (JPanel) comp;
            panel.setBackground(darkMode ?
                    mainFrame.getPanelBackground() :
                    mainFrame.getPanelBackground());
            for (Component child : panel.getComponents()) {
                applyThemeToComponent(child, darkMode);
            }
        } else if (comp instanceof JLabel) {
            JLabel label = (JLabel) comp;
            label.setForeground(darkMode ?
                    mainFrame.getForegroundColor() :
                    mainFrame.getForegroundColor());
        } else if (comp instanceof JTextField) {
            JTextField field = (JTextField) comp;
            field.setBackground(darkMode ?
                    mainFrame.getTextAreaBackground() :
                    Color.WHITE);
            field.setForeground(darkMode ?
                    mainFrame.getTextAreaForeground() :
                    Color.BLACK);
            field.setCaretColor(darkMode ? Color.WHITE : Color.BLACK);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(mainFrame.getBorderColor()),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        } else if (comp instanceof JTextArea) {
            JTextArea area = (JTextArea) comp;
            area.setBackground(darkMode ?
                    mainFrame.getTextAreaBackground() :
                    mainFrame.getTextAreaBackground());
            area.setForeground(darkMode ?
                    mainFrame.getTextAreaForeground() :
                    mainFrame.getTextAreaForeground());
        } else if (comp instanceof JComboBox) {
            JComboBox<?> combo = (JComboBox<?>) comp;
            combo.setBackground(darkMode ?
                    new Color(70, 70, 70) :
                    Color.WHITE);
            combo.setForeground(darkMode ?
                    Color.WHITE :
                    Color.BLACK);
        } else if (comp instanceof JScrollPane) {
            JScrollPane scroll = (JScrollPane) comp;
            scroll.getViewport().setBackground(darkMode ?
                    mainFrame.getTextAreaBackground() :
                    mainFrame.getTextAreaBackground());
            scroll.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(mainFrame.getBorderColor()),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }
    }

    // Fixed showMessage method - removed the Component parameter
    protected void showMessage(String message, String title, int messageType) {
        if (mainFrame.isDarkMode()) {
            UIManager.put("OptionPane.background", mainFrame.getPanelBackground());
            UIManager.put("Panel.background", mainFrame.getPanelBackground());
            UIManager.put("OptionPane.messageForeground", Color.BLACK);
        }

        JOptionPane.showMessageDialog(this, message, title, messageType);

        if (mainFrame.isDarkMode()) {
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.messageForeground", null);
        }
    }

    // Fixed showConfirmDialog method - removed the Component parameter
    protected int showConfirmDialog(String message, String title, int optionType, int messageType) {
        if (mainFrame.isDarkMode()) {
            UIManager.put("OptionPane.background", mainFrame.getPanelBackground());
            UIManager.put("Panel.background", mainFrame.getPanelBackground());
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
        }

        int result = JOptionPane.showConfirmDialog(this, message, title, optionType, messageType);

        if (mainFrame.isDarkMode()) {
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.messageForeground", null);
        }

        return result;
    }
}