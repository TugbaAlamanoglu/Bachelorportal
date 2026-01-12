package gui.dekan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardStudiendekan extends JPanel {

    private static final Color BG = StudiendekanFenster.BG;
    private static final Color BORDER = StudiendekanFenster.BORDER;
    private static final Color PRIMARY = StudiendekanFenster.PRIMARY;
    private static final Color TEXT_DARK = StudiendekanFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudiendekanFenster.TEXT_MUTED;

    private final StudiendekanFenster parent;

    public DashboardStudiendekan(StudiendekanFenster parent, String name, String email) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildScrollableContent(), BorderLayout.CENTER);
    }

    private JComponent buildScrollableContent() {
        JPanel content = new JPanel();
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(28, 28, 28, 28));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Header linksbündig
        JLabel h1 = new JLabel("Dashboard");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h1);

        content.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Übersicht über alle Bachelorarbeiten");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h2);

        content.add(Box.createVerticalStrut(18));

        // Kartenreihe
        JPanel cardsRow = new JPanel(new GridLayout(1, 3, 18, 0));
        cardsRow.setOpaque(false);

        cardsRow.add(makeStatusCard(
                "Offene Genehmigungen",
                "Warten auf Ihre Entscheidung",
                "0",
                () -> parent.showPage(StudiendekanFenster.PAGE_GENEHMIGUNGEN)
        ));

        cardsRow.add(makeStatusCard(
                "Aktive Arbeiten",
                "Laufende Bachelorarbeiten",
                "0",
                () -> parent.showPage(StudiendekanFenster.PAGE_UEBERSICHT)
        ));

        cardsRow.add(makeStatusCard(
                "Bewertungen ausstehend",
                "Notenvergabe erforderlich",
                "0",
                () -> parent.showPage(StudiendekanFenster.PAGE_NOTENVERGABE)
        ));

        cardsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));
        content.add(cardsRow);

        content.add(Box.createVerticalStrut(18));

        // Zweite Reihe mit Textkarten
        JPanel secondRow = new JPanel(new GridLayout(1, 2, 18, 0));
        secondRow.setOpaque(false);
        secondRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        secondRow.add(makeTextCard(
                "Aktuelle Phase",
                "Keine aktive Phase",
                "📊"
        ));

        secondRow.add(makeTextCard(
                "Aktuelle Aktivitäten",
                "Keine aktuellen Aktivitäten",
                "📝"
        ));

        content.add(secondRow);

        content.add(Box.createVerticalStrut(30));

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(BG);
        return sp;
    }

    private JComponent makeStatusCard(String title, String subtitle, String statusText, Runnable onClick) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new RoundedBorder(14, BORDER));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel inner = new JPanel(new BorderLayout());
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel icon = new JLabel("📄");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 22));
        icon.setForeground(TEXT_MUTED);
        inner.add(icon, BorderLayout.WEST);

        JLabel pill = new JLabel(statusText);
        pill.setFont(new Font("SansSerif", Font.BOLD, 12));
        pill.setOpaque(true);
        pill.setBorder(new EmptyBorder(6, 12, 6, 12));

        boolean ok = !"0".equals(statusText);

        pill.setBackground(ok ? new Color(231, 244, 255) : new Color(245, 245, 245));
        pill.setForeground(ok ? PRIMARY : TEXT_DARK);

        JPanel pillWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pillWrap.setOpaque(false);
        pillWrap.add(pill);
        inner.add(pillWrap, BorderLayout.EAST);

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setBorder(new EmptyBorder(12, 0, 0, 0));
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 16));
        t.setForeground(TEXT_DARK);

        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("SansSerif", Font.PLAIN, 13));
        s.setForeground(TEXT_MUTED);

        text.add(t);
        text.add(Box.createVerticalStrut(6));
        text.add(s);

        inner.add(text, BorderLayout.SOUTH);
        card.add(inner, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                if (onClick != null) onClick.run();
            }
        });

        return card;
    }

    private JComponent makeTextCard(String title, String text, String iconText) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new RoundedBorder(14, BORDER));

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel icon = new JLabel(iconText);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 22));
        icon.setForeground(TEXT_MUTED);
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(icon);

        inner.add(Box.createVerticalStrut(12));

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 16));
        t.setForeground(TEXT_DARK);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(t);

        inner.add(Box.createVerticalStrut(6));

        JLabel txt = new JLabel(text);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txt.setForeground(TEXT_MUTED);
        txt.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(txt);

        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    static class RoundedBorder extends javax.swing.border.LineBorder {
        private final int radius;
        RoundedBorder(int radius, Color color) {
            super(color, 1, true);
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }
    }
}