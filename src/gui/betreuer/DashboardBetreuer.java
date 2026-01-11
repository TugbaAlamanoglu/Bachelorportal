package gui.betreuer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardBetreuer extends JPanel {

    private static final Color BG = BetreuerFenster.BG;
    private static final Color BORDER = BetreuerFenster.BORDER;
    private static final Color PRIMARY = BetreuerFenster.PRIMARY;
    private static final Color TEXT_DARK = BetreuerFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = BetreuerFenster.TEXT_MUTED;

    private final BetreuerFenster parent;

    public DashboardBetreuer(BetreuerFenster parent, String name, String email) {
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

        JLabel h2 = new JLabel("Übersicht Ihrer Betreuungen");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h2);

        content.add(Box.createVerticalStrut(18));

        // Kartenreihe wie beim Student-Dashboard
        JPanel cardsRow = new JPanel(new GridLayout(1, 3, 18, 0));
        cardsRow.setOpaque(false);

        cardsRow.add(makeStatusCard(
                "Offene Anträge",
                "Warten auf Ihre Entscheidung",
                "0",
                () -> parent.showPage(BetreuerFenster.PAGE_ANTRAEGE)
        ));

        cardsRow.add(makeStatusCard(
                "Aktive Betreuungen",
                "Laufende Bachelorarbeiten",
                "0",
                () -> parent.showPage(BetreuerFenster.PAGE_BETREUTE_STUDIERENDE)
        ));

        cardsRow.add(makeStatusCard(
                "Neue Uploads",
                "Noch nicht gesichtet",
                "0",
                () -> parent.showPage(BetreuerFenster.PAGE_ARBEITSSTAND)
        ));

        cardsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));
        content.add(cardsRow);

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