package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NotenvergabeStudiendekan extends JPanel {

    public NotenvergabeStudiendekan() {
        setOpaque(true);
        setBackground(UIColors.BG_APP);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel h1 = new JLabel("Notenvergabe");
        h1.setFont(new Font("SansSerif", Font.BOLD, 22));
        h1.setForeground(UIColors.TEXT_DARK);

        JLabel sub = new JLabel("Noten einsehen und bearbeiten");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(UIColors.TEXT_MUTED);

        add(h1);
        add(Box.createVerticalStrut(4));
        add(sub);
        add(Box.createVerticalStrut(18));

        ShadowCardPanel card = new ShadowCardPanel(18);
        card.setBorder(new EmptyBorder(18, 18, 18, 18));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(new JLabel("Demo: Hier kommt eine Tabelle / Liste mit Abschlussarbeiten + Noten."));
        add(card);
    }
}

