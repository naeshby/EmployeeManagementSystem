package Operations;

import javax.swing.JOptionPane;

public class Exit {
    public void exit() {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null,
                    "Goodbye!\n\t~ Coded by Naeshby",
                    "Farewell",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}