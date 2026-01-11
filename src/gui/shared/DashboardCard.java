package gui.shared;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardCard extends JPanel {

    public DashboardCard() {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // exakt derselbe Look wie im Studentendashboard
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 233, 239), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));
    }
}
