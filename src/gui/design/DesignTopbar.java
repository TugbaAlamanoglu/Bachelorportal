package gui.design;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DesignTopbar extends JPanel {

    public DesignTopbar(String name) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xE5E7EB)));
        setPreferredSize(new Dimension(0, 72));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setBorder(new EmptyBorder(16, 32, 16, 32));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel t = new JLabel("Bachelorarbeits-Portal");
        t.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel s = new JLabel(name);
        s.setFont(new Font("SansSerif", Font.PLAIN, 14));
        s.setForeground(UIColors.TEXT_MUTED);

        left.add(t);
        left.add(Box.createVerticalStrut(2));
        left.add(s);

        add(left, BorderLayout.WEST);
    }
}
