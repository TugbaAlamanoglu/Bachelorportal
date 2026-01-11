package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GenehmigungAntrag extends JPanel {

    private static final Color CARD_BORDER = new Color(230, 233, 239);

    public GenehmigungAntrag() {
        setLayout(new BorderLayout());
        setBackground(UIColors.BG_APP);

        add(buildScroll(), BorderLayout.CENTER);
    }

    private JComponent buildScroll() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(48, 64, 64, 64));

        JLabel title = new JLabel("Genehmigungen");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(UIColors.TEXT_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Übersicht über eingereichte Anträge");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(UIColors.TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(title);
        content.add(Box.createVerticalStrut(6));
        content.add(sub);
        content.add(Box.createVerticalStrut(24));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new RoundedBorder(14, CARD_BORDER));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBorder(new EmptyBorder(48, 48, 48, 48));

        JLabel icon = new JLabel("📄");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 48));
        icon.setForeground(UIColors.TEXT_MUTED);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel text = new JLabel("Keine wartenden Anträge");
        text.setFont(new Font("SansSerif", Font.PLAIN, 15));
        text.setForeground(UIColors.TEXT_MUTED);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        inner.add(icon);
        inner.add(Box.createVerticalStrut(12));
        inner.add(text);

        card.add(inner, BorderLayout.CENTER);
        content.add(card);

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getViewport().setBackground(UIColors.BG_APP);
        sp.getVerticalScrollBar().setUnitIncrement(16);

        return sp;
    }

    // ===== Rounded Border (Studenten-Stil) =====
    static class RoundedBorder extends javax.swing.border.LineBorder {
        private final int r;

        RoundedBorder(int r, Color c) {
            super(c, 1, true);
            this.r = r;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.drawRoundRect(x, y, w - 1, h - 1, r, r);
            g2.dispose();
        }
    }
}
