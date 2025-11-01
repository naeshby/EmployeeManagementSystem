package gui;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    private int duration;

    public SplashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplash() {
        // Create main panel with gradient background
        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(70, 130, 180);
                Color color2 = new Color(32, 82, 132);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        content.setLayout(new BorderLayout());

        // Set window size and position
        int width = 700;
        int height = 450;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // Main title
        JLabel titleLabel = new JLabel("EMPLOYEE MANAGEMENT SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Modern Employee Database Solution", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(200, 220, 255));
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Author
        JLabel authorLabel = new JLabel("Developed by Naeshby", JLabel.CENTER);
        authorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        authorLabel.setForeground(new Color(180, 200, 255));
        authorLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Features list
        JPanel featuresPanel = new JPanel();
        featuresPanel.setOpaque(false);
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        String[] features = {
                "Real-time Employee Database",
                "Interactive Dashboard",
                "Advanced Search & Filter",
                "Secure Data Management",
                "Modern User Interface"
        };

        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature, JLabel.CENTER);
            featureLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
            featureLabel.setForeground(Color.WHITE);
            featureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            featuresPanel.add(featureLabel);
        }

        // Loading panel
        JPanel loadingPanel = new JPanel();
        loadingPanel.setOpaque(false);
        loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.Y_AXIS));
        loadingPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(500, 25));
        progressBar.setMaximumSize(new Dimension(500, 25));
        progressBar.setForeground(new Color(60, 179, 113));
        progressBar.setBackground(new Color(200, 200, 200));
        progressBar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        progressBar.setStringPainted(true);

        JLabel loadingLabel = new JLabel("Initializing Application...", JLabel.CENTER);
        loadingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        loadingPanel.add(progressBar);
        loadingPanel.add(loadingLabel);

        // Add everything to content
        content.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(subtitleLabel, BorderLayout.NORTH);
        centerPanel.add(featuresPanel, BorderLayout.CENTER);
        centerPanel.add(authorLabel, BorderLayout.SOUTH);

        content.add(centerPanel, BorderLayout.CENTER);
        content.add(loadingPanel, BorderLayout.SOUTH);

        setContentPane(content);
        setVisible(true);

        // Animate with different loading messages
        animateWithMessages(progressBar, loadingLabel);

        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(false);
        dispose();
    }

    private void animateWithMessages(JProgressBar progressBar, JLabel loadingLabel) {
        new Thread(() -> {
            String[] messages = {
                    "Initializing Application...",
                    "Loading Database Modules...",
                    "Setting Up User Interface...",
                    "Preparing Dashboard...",
                    "Almost Ready...",
                    "Welcome!"
            };

            int messageIndex = 0;

            for (int i = 0; i <= 100; i++) {
                final int progress = i;
                final String message = messages[messageIndex];

                SwingUtilities.invokeLater(() -> {
                    progressBar.setValue(progress);
                    progressBar.setString(progress + "%");
                    loadingLabel.setText(message);
                });

                // Change message at certain progress points
                if (progress == 20) messageIndex = 1;
                if (progress == 40) messageIndex = 2;
                if (progress == 60) messageIndex = 3;
                if (progress == 80) messageIndex = 4;
                if (progress == 95) messageIndex = 5;

                try {
                    Thread.sleep(duration / 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}