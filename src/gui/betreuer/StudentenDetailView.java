package gui.betreuer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentenDetailView extends JDialog {

    public StudentenDetailView(JFrame parent,
                               String name, String matrikel, String email,
                               String thema, String betreuer, String anmeldung, String abgabe, String note) {
        super(parent, "Studenten-Details", true);
        setMinimumSize(new Dimension(860, 540));
        setLocationRelativeTo(parent);
        getContentPane().setBackground(DashboardBetreuer.BG);
        setLayout(new BorderLayout());

        DashboardBetreuer.RoundedPanel card = new DashboardBetreuer.RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(18, 20, 18, 20));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(name);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(DashboardBetreuer.TEXT);

        JLabel meta = new JLabel("Mat.-Nr.: " + matrikel + " | " + email);
        meta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        meta.setForeground(DashboardBetreuer.TEXT_MUTED);

        JLabel tLabel = new JLabel("Thema");
        tLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        tLabel.setForeground(DashboardBetreuer.TEXT);
        tLabel.setBorder(new EmptyBorder(16, 0, 8, 0));

        JTextField themaField = new JTextField(thema);
        themaField.setEditable(false);
        themaField.setBackground(new Color(0xFBFBFC));
        themaField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DashboardBetreuer.BORDER),
                new EmptyBorder(12, 12, 12, 12)
        ));
        themaField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel grid = new JPanel(new GridLayout(2, 2, 18, 12));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(14, 0, 0, 0));
        grid.add(item("Betreuer", betreuer));
        grid.add(item("Anmeldung", anmeldung));
        grid.add(item("Abgabe", abgabe));
        grid.add(item("Note", note));

        JButton close = new JButton("Schließen");
        close.setFocusPainted(false);
        close.setOpaque(true);
        close.setBackground(new Color(0xF8FAFC));
        close.setForeground(DashboardBetreuer.TEXT);
        close.setFont(new Font("SansSerif", Font.BOLD, 14));
        close.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DashboardBetreuer.BORDER),
                new EmptyBorder(10, 16, 10, 16)
        ));
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addActionListener(e -> dispose());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(18, 0, 0, 0));
        actions.add(close);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(meta);
        card.add(tLabel);
        card.add(themaField);
        card.add(grid);
        card.add(actions);

        JPanel pad = new JPanel(new BorderLayout());
        pad.setOpaque(false);
        pad.setBorder(new EmptyBorder(22, 28, 28, 28));
        pad.add(card, BorderLayout.CENTER);

        add(pad, BorderLayout.CENTER);
    }

    private JPanel item(String label, String value) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel l = new JLabel(label);
        l.setForeground(DashboardBetreuer.TEXT_MUTED);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel v = new JLabel(value);
        v.setForeground(DashboardBetreuer.TEXT);
        v.setFont(new Font("SansSerif", Font.BOLD, 14));

        p.add(l);
        p.add(Box.createVerticalStrut(4));
        p.add(v);
        return p;
    }
}
