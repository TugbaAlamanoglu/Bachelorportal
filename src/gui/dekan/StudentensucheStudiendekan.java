package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentensucheStudiendekan extends JPanel {

    public StudentensucheStudiendekan() {
        setOpaque(true);
        setBackground(UIColors.BG_APP);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel h1 = new JLabel("Studentensuche");
        h1.setFont(new Font("SansSerif", Font.BOLD, 22));
        h1.setForeground(UIColors.TEXT_DARK);

        JLabel sub = new JLabel("Studierende suchen und Bachelorarbeiten einsehen");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(UIColors.TEXT_MUTED);

        add(h1);
        add(Box.createVerticalStrut(4));
        add(sub);
        add(Box.createVerticalStrut(18));

        ShadowCardPanel card = new ShadowCardPanel(18);
        card.setBorder(new EmptyBorder(18, 18, 18, 18));
        card.setLayout(new BorderLayout(12, 12));

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(300, 38));

        JButton btn = new JButton("Suchen");
        btn.setBackground(UIColors.PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setOpaque(false);
        top.add(search, BorderLayout.CENTER);
        top.add(btn, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);

        JTextArea results = new JTextArea("• Tom Schmidt (12345680)\n• Lisa Müller (345678)\n• Max Mustermann (234567)");
        results.setEditable(false);
        results.setFont(new Font("SansSerif", Font.PLAIN, 13));
        results.setOpaque(false);
        results.setForeground(UIColors.TEXT_DARK);

        card.add(results, BorderLayout.CENTER);

        add(card);
    }
}
