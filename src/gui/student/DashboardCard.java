package gui.student;

import gui.shared.RoundPanel;
import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardCard extends RoundPanel {

    public DashboardCard(String title, String text, int height) {
        super(16);
        setFill(UIColors.CARD_BG);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setForeground(UIColors.TEXT_DARK);

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textLabel.setForeground(UIColors.TEXT_MUTED);

        add(titleLabel);
        add(Box.createVerticalStrut(12));
        add(textLabel);

        setPreferredSize(new Dimension(260, height));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
    }
}
