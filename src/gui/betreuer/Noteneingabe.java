package gui.betreuer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Noteneingabe extends JPanel {

    private final DashboardBetreuer parent;

    public Noteneingabe(DashboardBetreuer parent) {
        this.parent = parent;
        setOpaque(false);
        setLayout(new GridLayout(1, 2, 20, 0));
        setBorder(new EmptyBorder(10, 0, 0, 0));

        add(buildLeft());
        add(buildRight());
    }

    private JComponent buildLeft() {
        DashboardBetreuer.RoundedPanel card = new DashboardBetreuer.RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel("Noteneingabe");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(DashboardBetreuer.TEXT);

        JLabel sub = new JLabel("Bewertung einer Bachelorarbeit");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(DashboardBetreuer.TEXT_MUTED);

        JLabel choose = new JLabel("Student auswählen");
        choose.setFont(new Font("SansSerif", Font.BOLD, 14));
        choose.setForeground(DashboardBetreuer.TEXT);
        choose.setBorder(new EmptyBorder(16, 0, 8, 0));

        JComboBox<String> combo = new JComboBox<>(new String[]{
                "Student auswählen...", "Anna Müller (123456)", "Lisa Schmidt (345678)"
        });
        combo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        combo.setBackground(new Color(0xFBFBFC));
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DashboardBetreuer.BORDER),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JPanel placeholder = new JPanel();
        placeholder.setOpaque(false);
        placeholder.setLayout(new BoxLayout(placeholder, BoxLayout.Y_AXIS));
        placeholder.setBorder(new EmptyBorder(36, 0, 0, 0));

        JLabel medal = new JLabel("🏅", SwingConstants.CENTER);
        medal.setFont(new Font("SansSerif", Font.PLAIN, 52));
        medal.setForeground(new Color(0xD1D5DB));
        medal.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hint = new JLabel("Wählen Sie einen Studenten aus, um eine Note einzutragen");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 14));
        hint.setForeground(DashboardBetreuer.TEXT_MUTED);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        placeholder.add(medal);
        placeholder.add(Box.createVerticalStrut(14));
        placeholder.add(hint);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(choose);
        card.add(combo);
        card.add(placeholder);

        return card;
    }

    private JComponent buildRight() {
        DashboardBetreuer.RoundedPanel card = new DashboardBetreuer.RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel("Benotete Arbeiten");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(DashboardBetreuer.TEXT);

        JLabel sub = new JLabel("Übersicht aller vergebenen Noten");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(DashboardBetreuer.TEXT_MUTED);

        DashboardBetreuer.RoundedPanel inner = new DashboardBetreuer.RoundedPanel(14);
        inner.setBackground(new Color(0xFBFBFC));
        inner.setBorder(new EmptyBorder(16, 16, 16, 16));
        inner.setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel name = new JLabel("Anna Müller");
        name.setFont(new Font("SansSerif", Font.BOLD, 16));
        name.setForeground(DashboardBetreuer.TEXT);

        JLabel mat = new JLabel("Mat.-Nr.: 123456");
        mat.setFont(new Font("SansSerif", Font.PLAIN, 13));
        mat.setForeground(DashboardBetreuer.TEXT_MUTED);

        JLabel thema = new JLabel("Entwicklung eines KI-gestützten Chatbots für…");
        thema.setFont(new Font("SansSerif", Font.PLAIN, 14));
        thema.setForeground(DashboardBetreuer.TEXT);
        thema.setBorder(new EmptyBorder(10, 0, 10, 0));

        JTextField comment = new JTextField("\"Sehr gute Arbeit mit innovativem Ansatz.\"");
        comment.setEditable(false);
        comment.setBackground(Color.WHITE);
        comment.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DashboardBetreuer.BORDER),
                new EmptyBorder(10, 10, 10, 10)
        ));
        comment.setFont(new Font("SansSerif", Font.ITALIC, 14));

        JPanel dates = new JPanel(new BorderLayout());
        dates.setOpaque(false);
        dates.setBorder(new EmptyBorder(12, 0, 0, 0));

        JLabel abgabe = new JLabel("Abgabe: 15.12.2024");
        abgabe.setForeground(DashboardBetreuer.TEXT_MUTED);
        abgabe.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel benotet = new JLabel("Benotet: 20.12.2024");
        benotet.setForeground(DashboardBetreuer.TEXT_MUTED);
        benotet.setFont(new Font("SansSerif", Font.PLAIN, 13));

        dates.add(abgabe, BorderLayout.WEST);
        dates.add(benotet, BorderLayout.EAST);

        left.add(name);
        left.add(Box.createVerticalStrut(2));
        left.add(mat);
        left.add(thema);
        left.add(comment);
        left.add(dates);

        JLabel grade = new JLabel("1,7", SwingConstants.CENTER);
        grade.setOpaque(true);
        grade.setBackground(DashboardBetreuer.BLUE);
        grade.setForeground(Color.WHITE);
        grade.setFont(new Font("SansSerif", Font.BOLD, 16));
        grade.setBorder(new EmptyBorder(10, 14, 10, 14));

        inner.add(left, BorderLayout.CENTER);
        inner.add(grade, BorderLayout.EAST);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(14));
        card.add(inner);

        return card;
    }
}
