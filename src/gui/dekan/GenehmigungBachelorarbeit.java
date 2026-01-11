package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GenehmigungBachelorarbeit extends JPanel {

    public GenehmigungBachelorarbeit() {
        setOpaque(true);
        setBackground(UIColors.BG_APP);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel h1 = new JLabel("Genehmigungen");
        h1.setFont(new Font("SansSerif", Font.BOLD, 22));
        h1.setForeground(UIColors.TEXT_DARK);

        JLabel sub = new JLabel("Anträge prüfen und entscheiden");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(UIColors.TEXT_MUTED);

        add(h1);
        add(Box.createVerticalStrut(4));
        add(sub);
        add(Box.createVerticalStrut(18));

        ShadowCardPanel card = new ShadowCardPanel(18);
        card.setBorder(new EmptyBorder(18, 18, 18, 18));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.add(new ApprovalItem(
                "Mobile App-Entwicklung mit React Native für IoT-Gerätesteuerung",
                "Tom Schmidt (Matrikelnr. 12345680)",
                "Betreuer: Prof. Dr. Michael Weber",
                "Wartet auf Genehmigung"
        ));
        card.add(Box.createVerticalStrut(12));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton deny = new JButton("Ablehnen");
        deny.setBackground(new Color(245, 246, 248));
        deny.setForeground(UIColors.TEXT_DARK);
        deny.setFocusPainted(false);

        JButton approve = new JButton("Genehmigen");
        approve.setBackground(UIColors.PRIMARY);
        approve.setForeground(Color.WHITE);
        approve.setFocusPainted(false);

        actions.add(deny);
        actions.add(approve);

        card.add(actions);

        add(card);
    }
}

