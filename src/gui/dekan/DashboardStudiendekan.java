package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardStudiendekan extends JPanel {

    public DashboardStudiendekan() {
        setOpaque(true);
        setBackground(UIColors.BG_APP);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel h1 = new JLabel("Studiendekan Dashboard");
        h1.setFont(new Font("SansSerif", Font.BOLD, 22));
        h1.setForeground(UIColors.TEXT_DARK);

        JLabel sub = new JLabel("Übersicht über alle Bachelorarbeiten");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(UIColors.TEXT_MUTED);

        add(h1);
        add(Box.createVerticalStrut(4));
        add(sub);
        add(Box.createVerticalStrut(18));

        // Stat cards row
        JPanel stats = new JPanel(new GridLayout(1, 4, 16, 16));
        stats.setOpaque(false);

        stats.add(new StatCard("Offene Genehmigungen", "Zur Entscheidung", "1"));
        stats.add(new StatCard("Genehmigte Anträge", "Bewilligt", "1"));
        stats.add(new StatCard("Finale Abgaben", "Eingereicht", "0"));
        stats.add(new StatCard("Seminarnoten", "Ausstehend", "0"));

        add(stats);
        add(Box.createVerticalStrut(18));

        // Offene Genehmigungen section
        ShadowCardPanel sec1 = new ShadowCardPanel(18);
        sec1.setLayout(new BoxLayout(sec1, BoxLayout.Y_AXIS));
        sec1.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel s1t = new JLabel("Offene Genehmigungen");
        s1t.setFont(new Font("SansSerif", Font.BOLD, 16));
        s1t.setForeground(UIColors.TEXT_DARK);
        sec1.add(s1t);
        sec1.add(Box.createVerticalStrut(12));

        sec1.add(new ApprovalItem(
                "Mobile App-Entwicklung mit React Native für IoT-Gerätesteuerung",
                "Tom Schmidt (Matrikelnr. 12345680)",
                "Betreuer: Prof. Dr. Michael Weber",
                "Wartet auf Genehmigung"
        ));

        add(sec1);
        add(Box.createVerticalStrut(18));

        // Alle Bachelorarbeiten section
        ShadowCardPanel sec2 = new ShadowCardPanel(18);
        sec2.setLayout(new BoxLayout(sec2, BoxLayout.Y_AXIS));
        sec2.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel s2t = new JLabel("Alle Bachelorarbeiten (3)");
        s2t.setFont(new Font("SansSerif", Font.BOLD, 16));
        s2t.setForeground(UIColors.TEXT_DARK);
        sec2.add(s2t);
        sec2.add(Box.createVerticalStrut(12));

        sec2.add(new WorkRow("Max Mustermann", "Entwicklung einer KI-gestützten Webapplikation für automatisierte Datenanalyse", "Genehmigt", new Color(220, 245, 230)));
        sec2.add(Box.createVerticalStrut(10));
        sec2.add(new WorkRow("Lisa Müller", "Cloud-basierte Microservices-Architektur für E-Commerce-Plattformen", "Bei Betreuer", new Color(230, 238, 255)));
        sec2.add(Box.createVerticalStrut(10));
        sec2.add(new WorkRow("Tom Schmidt", "Mobile App-Entwicklung mit React Native für IoT-Gerätesteuerung", "Bei Studiendekan", new Color(255, 245, 220)));

        add(sec2);
    }
}

