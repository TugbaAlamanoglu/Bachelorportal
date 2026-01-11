package start;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import gui.authentication.LoginFenster;

public class StartPortal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ignored) {}

            new LoginFenster().setVisible(true);
        });
    }
}
