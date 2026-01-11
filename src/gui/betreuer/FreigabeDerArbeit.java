package gui.betreuer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FreigabeDerArbeit extends JDialog {

    public FreigabeDerArbeit(JFrame parent, DashboardBetreuer.Application app) {
        super(parent, "Genehmigung prüfen", true);
        setMinimumSize(new Dimension(760, 520));
        setLocationRelativeTo(parent);
        getContentPane().setBackground(DashboardBetreuer.BG);
        setLayout(new BorderLayout());

        DashboardBetreuer.RoundedPanel card = new DashboardBetreuer.RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(18, 20, 18, 20));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel head = new JLabel(app.studentName);
        head.setFont(new Font("SansSerif", Font.BOLD, 22));
        head.setForeground(DashboardBetreuer.TEXT);

        JLabel meta = new JLabel("Mat.-Nr.: " + app.matrikel + " | " + app.email);
        meta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        meta.setForeground(DashboardBetreuer.TEXT_MUTED);

        JLabel tLabel = new JLabel("Thema der Bachelorarbeit");
        tLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        tLabel.setForeground(DashboardBetreuer.TEXT);

        JTextArea thema = new JTextArea(app.thema);
        thema.setWrapStyleWord(true);
        thema.setLineWrap(true);
        thema.setEditable(false);
        thema.setBackground(new Color(0xFBFBFC));
        thema.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DashboardBetreuer.BORDER),
                new EmptyBorder(12, 12, 12, 12)
        ));
        thema.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel grid = new JPanel(new GridLayout(2, 2, 18, 12));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(14, 0, 0, 0));
        grid.add(item("Unternehmen", app.unternehmen));
        grid.add(item("Betreuer im Unternehmen", app.unternehmensBetreuer));
        grid.add(item("Zeitraum", app.zeitraum));
        grid.add(item("NDA-Status", app.ndaStatus));

        JTextArea kommentar = new JTextArea();
        kommentar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DashboardBetreuer.BORDER),
                new EmptyBorder(10, 10, 10, 10)
        ));
        kommentar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        kommentar.setRows(4);
        kommentar.setBackground(Color.WHITE);

        JLabel kLabel = new JLabel("Kommentar (optional)");
        kLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        kLabel.setForeground(DashboardBetreuer.TEXT);
        kLabel.setBorder(new EmptyBorder(14, 0, 8, 0));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(16, 0, 0, 0));

        JButton ablehnen = new JButton("Ablehnen");
        styleSecondary(ablehnen);

        JButton genehmigen = new JButton("Genehmigen");
        stylePrimary(genehmigen);

        ablehnen.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abgelehnt (Demo).");
            dispose();
        });

        genehmigen.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Genehmigt (Demo).");
            dispose();
        });

        actions.add(ablehnen);
        actions.add(genehmigen);

        card.add(head);
        card.add(Box.createVerticalStrut(4));
        card.add(meta);
        card.add(Box.createVerticalStrut(16));
        card.add(tLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(new JScrollPane(thema) {{
            setBorder(null);
            setOpaque(false);
            getViewport().setOpaque(false);
            setPreferredSize(new Dimension(10, 90));
        }});
        card.add(grid);
        card.add(kLabel);
        card.add(new JScrollPane(kommentar) {{
            setBorder(null);
            setOpaque(false);
            getViewport().setOpaque(false);
        }});
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

    private void stylePrimary(JButton b) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(DashboardBetreuer.BLUE);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBorder(new EmptyBorder(10, 16, 10, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondary(JButton b) {
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBackground(new Color(0xF8FAFC));
        b.setForeground(DashboardBetreuer.TEXT);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DashboardBetreuer.BORDER),
                new EmptyBorder(10, 16, 10, 16)
        ));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}

