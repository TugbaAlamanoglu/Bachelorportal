package gui.student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Dashboard Student (Panel) – Design unverändert, Logik ergänzt
 */
public class DashboardStudent extends JPanel {

    private static final Color BG = StudentFenster.BG;
    private static final Color BORDER = StudentFenster.BORDER;
    private static final Color PRIMARY = StudentFenster.PRIMARY;
    private static final Color ACCENT_RED = StudentFenster.ACCENT_RED;
    private static final Color TEXT_DARK = StudentFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudentFenster.TEXT_MUTED;

    private static final Color NOTIF_BG = new Color(234, 245, 255);
    private static final Color WARN_BG  = new Color(255, 244, 230);

    private final StudentFenster parent;

    public DashboardStudent(StudentFenster parent, int mnr, String name, String email) {
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

        JLabel h1 = new JLabel("Dashboard");
        h1.setFont(new Font("SansSerif", Font.BOLD, 22));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h1);

        content.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Übersicht über Ihren Bachelorarbeits-Status");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h2);

        content.add(Box.createVerticalStrut(18));

        JPanel cardsRow = new JPanel(new GridLayout(1, 3, 18, 0));
        cardsRow.setOpaque(false);

        cardsRow.add(makeStatusCard(
                "Allgemeine Informationen",
                "Grunddaten Ihrer Bachelorarbeit",
                "Noch nicht begonnen",
                () -> parent.showPage(StudentFenster.PAGE_ALLGEMEIN)
        ));

        cardsRow.add(makeStatusCard(
                "Anmeldung Bachelorarbeit",
                "Offizieller Anmeldestatus",
                "Noch nicht möglich",
                () -> parent.showPage(StudentFenster.PAGE_ANMELDUNG)
        ));

        // ✅ FIX: nicht auf Feld zugreifen, sondern Methode nutzen
        boolean abgegeben = AbgabeBachelorarbeit.isSubmitted();

        cardsRow.add(makeStatusCard(
                "Abgabe Bachelorarbeit",
                "Finale Einreichung",
                abgegeben ? "Eingereicht" : "Noch nicht möglich",
                () -> parent.showPage(StudentFenster.PAGE_ABGABE)
        ));

        cardsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));
        content.add(cardsRow);

        content.add(Box.createVerticalStrut(18));
        content.add(makeNotificationsCard());

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

        boolean ok =
                "Genehmigt".equalsIgnoreCase(statusText) ||
                "Gespeichert".equalsIgnoreCase(statusText) ||
                "Eingereicht".equalsIgnoreCase(statusText);

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

        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (onClick != null) onClick.run();
            }
        });

        return card;
    }

    // ---------------- Notifications ----------------

    private JComponent makeNotificationsCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new RoundedBorder(14, BORDER));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(18, 18, 10, 18));

        JLabel t = new JLabel("Letzte Benachrichtigungen");
        t.setFont(new Font("SansSerif", Font.BOLD, 16));
        t.setForeground(TEXT_DARK);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel badge = new JLabel("0 ungelesen");
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.setOpaque(true);
        badge.setForeground(TEXT_DARK);
        badge.setBackground(new Color(245, 245, 245));
        badge.setBorder(new EmptyBorder(6, 10, 6, 10));

        JPanel badgeWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        badgeWrapper.setOpaque(false);
        badgeWrapper.add(badge);

        header.add(t, BorderLayout.WEST);
        header.add(badgeWrapper, BorderLayout.EAST);
        card.add(header, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setBorder(new EmptyBorder(6, 18, 18, 18));
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        JLabel noNotifications = new JLabel("keine Benachrichtigungen vorhanden");
        noNotifications.setFont(new Font("SansSerif", Font.PLAIN, 14));
        noNotifications.setForeground(TEXT_MUTED);
        noNotifications.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.add(noNotifications);

        card.add(list, BorderLayout.CENTER);
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