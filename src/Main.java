import gui.MainFrame;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Set some UI improvements
            UIManager.put("Button.foreground", new Color(255, 255, 255));
            UIManager.put("Button.select", new Color(200, 200, 255));

        } catch (Exception e) {
            System.err.println("Error setting look and feel: " + e.getMessage());
        }

        // Create and show GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);

                // Show welcome message
                UIManager.put("OptionPane.background", Color.WHITE);
                UIManager.put("Panel.background", Color.WHITE);

                JOptionPane.showMessageDialog(frame,
                        "Welcome to Employee Management System!\n\n" +
                                "By Naeshby\n\n" +
                                "🚀 Modern Icon-Based Interface\n" +
                                "🎨 Dark/Light Mode Toggle\n" +
                                "📊 Interactive Dashboard\n" +
                                "⚡ Quick Actions Available",
                        "Welcome to EMS",
                        JOptionPane.INFORMATION_MESSAGE);

                // Reset UIManager
                UIManager.put("OptionPane.background", null);
                UIManager.put("Panel.background", null);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}