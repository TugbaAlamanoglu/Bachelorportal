package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Enthält Komponenten für Studiendekan-Ansichten:
 * ShadowCardPanel, NavButton, CircleLogo, BellWithBadge
 */
class ShadowCardPanel extends JPanel {
    private final int radius;

    ShadowCardPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(18, 18, 18, 18));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int shadow = 18;
        int x = shadow / 2;
        int y = shadow / 2;
        int w = getWidth() - shadow;
        int h = getHeight() - shadow;

        for (int i = 0; i < 10; i++) {
            int alpha = 18 - i * 2;
            g2.setColor(new Color(0, 0, 0, Math.max(alpha, 0)));
            g2.fillRoundRect(x + i, y + i, w - i * 2, h - i * 2, radius, radius);
        }

        g2.setColor(getBackground());
        g2.fillRoundRect(x, y, w, h, radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }
}

class NavButton extends JButton {
    private boolean active;

    NavButton(String text) {
        super(text);
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setHorizontalAlignment(SwingConstants.LEFT);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setBackground(Color.WHITE);
        setForeground(UIColors.TEXT_DARK);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(240, 44));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        setBorder(new EmptyBorder(0, 14, 0, 14));
    }

    void setActive(boolean active) {
        this.active = active;
        if (active) {
            setBackground(new Color(232, 242, 255));
            setForeground(UIColors.PRIMARY);
        } else {
            setBackground(Color.WHITE);
            setForeground(UIColors.TEXT_DARK);
        }
        repaint();
    }
}

class CircleLogo extends JComponent {
    private final int size;
    private final String text;

    CircleLogo(int size, String text) {
        this.size = size;
        this.text = text;
        setPreferredSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int d = Math.min(getWidth(), getHeight());
        int x = (getWidth() - d) / 2;
        int y = (getHeight() - d) / 2;

        g2.setColor(UIColors.PRIMARY);
        g2.fillOval(x, y, d, d);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, d / 3)));
        FontMetrics fm = g2.getFontMetrics();
        int tx = x + (d - fm.stringWidth(text)) / 2;
        int ty = y + (d - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(text, tx, ty);

        g2.dispose();
    }
}

class BellWithBadge extends JPanel {
    BellWithBadge(int count) {
        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(48, 48));

        JButton bell = new JButton("🔔");
        bell.setBounds(0, 0, 48, 48);
        bell.setFocusPainted(false);
        bell.setBorder(BorderFactory.createLineBorder(new Color(233, 236, 242), 1, true));
        bell.setBackground(new Color(247, 248, 250));
        bell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(bell);

        if (count > 0) {
            JLabel badge = new JLabel(String.valueOf(count), SwingConstants.CENTER);
            badge.setOpaque(true);
            badge.setBackground(new Color(0xE30613)); // rot
            badge.setForeground(Color.WHITE);
            badge.setFont(new Font("SansSerif", Font.BOLD, 12));
            badge.setBounds(32, 2, 18, 18);
            badge.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
            add(badge);
        }
    }
}