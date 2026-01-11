package gui.design;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DesignCard extends JPanel {

    public DesignCard(String title, String text, int height) {
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 15));
        t.setForeground(UIColors.TEXT_DARK);

        JLabel s = new JLabel(text);
        s.setFont(new Font("SansSerif", Font.PLAIN, 14));
        s.setForeground(UIColors.TEXT_MUTED);

        add(t);
        add(Box.createVerticalStrut(10));
        add(s);

        setPreferredSize(new Dimension(320, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0xE5E7EB));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
    }
}
