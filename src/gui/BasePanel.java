package gui;

import javax.swing.*;
import java.awt.*;

public abstract class BasePanel extends JPanel {
    protected MainFrame mainFrame;

    public BasePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(mainFrame.getBackgroundColor());
    }

    protected JLabel createTitleLabel(String text, Color color, ImageIcon icon) {
        JLabel titleLabel = new JLabel(text, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(color);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        if (icon != null) {
            titleLabel.setIcon(icon);
            titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
            titleLabel.setIconTextGap(15);
        }

        return titleLabel;
    }

    protected JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        updateTitledBorder(formPanel, "Form");
        formPanel.setBackground(mainFrame.getPanelBackground());
        return formPanel;
    }

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
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
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

        return button;
    }

    protected void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    protected int showConfirmDialog(String message, String title, int optionType, int messageType) {
        return JOptionPane.showConfirmDialog(this, message, title, optionType, messageType);
    }
}