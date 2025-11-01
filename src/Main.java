import gui.MainFrame;
import gui.SplashScreen;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting look and feel: " + e.getMessage());
        }

        // Show splash screen on EDT
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen(4000); // 4 seconds

            // Show splash and then main application
            new Thread(() -> {
                splash.showSplash();

                // Now show main application on EDT
                SwingUtilities.invokeLater(() -> {
                    try {
                        MainFrame frame = new MainFrame();
                        frame.setVisible(true);

                        // Center the frame
                        frame.setLocationRelativeTo(null);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,
                                "Error starting application: " + e.getMessage(),
                                "Startup Error",
                                JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                });
            }).start();
        });
    }
}