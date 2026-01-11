package gui.dekan;

import gui.shared.DashboardCard;
import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardStudiendekan extends JPanel {

    public DashboardStudiendekan() {
        setBackground(UIColors.BG_APP);
        setLayout(new BorderLayout());
        add(buildScrollableContent(), BorderLayout.CENTER);
    }

    private JComponent buildScrollableContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // 🔥 EXAKT wie StudentDashboardView
        content.setBorder(new EmptyBorder(48, 64, 64, 64));

        // ===== Titel =====
        JLabel h1 = new JLabel("Studiendekan Dashboard");
        h1.setFont(new Font("SansSerif", Font.BOLD, 22));
        h1.setForeground(UIColors.TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Übersicht über alle Bachelorarbeiten");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(UIColors.TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(h1);
        content.add(Box.createVerticalStrut(6));
        content.add(sub);
        content.add(Box.createVerticalStrut(24));

        // ===== Obere Cards (wie beim Studenten) =====
        JPanel topRow = new JPanel(new GridLayout(1, 3, 18, 0));
        topRow.setOpaque(false);
        topRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        topRow.add(simpleCard("Offene Genehmigungen", "0"));
        topRow.add(simpleCard("Aktive Arbeiten", "0"));
        topRow.add(simpleCard("Bewertungen ausstehend", "0"));

        content.add(topRow);
        content.add(Box.createVerticalStrut(18));

        // ===== Untere Cards =====
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 18, 0));
        bottomRow.setOpaque(false);
        bottomRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        bottomRow.add(textCard(
                "Aktuelle Phase",
                "Keine aktive Phase"
        ));

        bottomRow.add(textCard(
                "Aktuelle Aktivitäten",
                "Keine aktuellen Aktivitäten"
        ));

        content.add(bottomRow);

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getViewport().setBackground(UIColors.BG_APP);
        sp.getVerticalScrollBar().setUnitIncrement(16);

        return sp;
    }

    // ===== Kleine Status-Card =====
    private JComponent simpleCard(String title, String value) {
        DashboardCard card = new DashboardCard();

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 16));
        t.setForeground(UIColors.TEXT_DARK);

        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.PLAIN, 14));
        v.setForeground(UIColors.TEXT_MUTED);

        card.add(t);
        card.add(Box.createVerticalStrut(6));
        card.add(v);

        return card;
    }

    // ===== Große Text-Card =====
    private JComponent textCard(String title, String text) {
        DashboardCard card = new DashboardCard();

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 16));
        t.setForeground(UIColors.TEXT_DARK);

        JLabel body = new JLabel(text);
        body.setFont(new Font("SansSerif", Font.PLAIN, 14));
        body.setForeground(UIColors.TEXT_MUTED);

        card.add(t);
        card.add(Box.createVerticalStrut(12));
        card.add(body);

        return card;
    }
}
