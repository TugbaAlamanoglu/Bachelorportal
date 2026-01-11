package gui.student;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentDashboardView extends JPanel {

    public StudentDashboardView(StudentFenster parent, int mnr, String name, String email) {
        setBackground(UIColors.BG_APP);
        setLayout(new BorderLayout());

        add(buildScrollableContent(), BorderLayout.CENTER);
    }

    private JComponent buildScrollableContent() {
        JPanel content = buildContent();

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getViewport().setBackground(UIColors.BG_APP);
        sp.getVerticalScrollBar().setUnitIncrement(16);

        return sp;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // 👉 WICHTIG: linker Abstand reduziert
        content.setBorder(new EmptyBorder(48, 32, 64, 64));

        // ===== Titel =====
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(UIColors.TEXT_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, title.getPreferredSize().height)
        );

        JLabel subtitle = new JLabel("Übersicht über Ihren Bachelorarbeitsstatus");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setForeground(UIColors.TEXT_MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, subtitle.getPreferredSize().height)
        );

        content.add(title);
        content.add(Box.createVerticalStrut(6));
        content.add(subtitle);
        content.add(Box.createVerticalStrut(40));

        // ===== Obere Karten =====
        JPanel topRow = new JPanel(new GridLayout(1, 3, 32, 0));
        topRow.setOpaque(false);
        topRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        topRow.add(new DashboardCard(
                "Allgemeine Informationen",
                "Noch nicht begonnen",
                160
        ));
        topRow.add(new DashboardCard(
                "Anmeldung Bachelorarbeit",
                "Noch nicht möglich",
                160
        ));
        topRow.add(new DashboardCard(
                "Abgabe Bachelorarbeit",
                "Noch nicht möglich",
                160
        ));

        content.add(topRow);
        content.add(Box.createVerticalStrut(40));

        // ===== Untere Karten =====
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 32, 0));
        bottomRow.setOpaque(false);
        bottomRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));

        bottomRow.add(new DashboardCard(
                "Letzte Benachrichtigungen",
                "Keine Benachrichtigungen vorhanden",
                220
        ));
        bottomRow.add(new DashboardCard(
                "Wichtige Termine",
                "Noch keine Termine vorhanden",
                220
        ));

        content.add(bottomRow);

        return content;
    }
}
