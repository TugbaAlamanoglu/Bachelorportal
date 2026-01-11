package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NotenvergabeStudiendekan extends JPanel {

    public NotenvergabeStudiendekan() {
        setBackground(UIColors.BG_APP);
        setLayout(new BorderLayout());

        add(buildScrollableContent(), BorderLayout.CENTER);
    }

    // ================= Scroll =================
    private JComponent buildScrollableContent() {
        JPanel content = buildContent();

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getViewport().setBackground(UIColors.BG_APP);
        sp.getVerticalScrollBar().setUnitIncrement(16);

        return sp;
    }

    // ================= Inhalt =================
    private JPanel buildContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(40, 48, 64, 48));

        // -------- Titel --------
        JLabel title = new JLabel("Notenvergabe (Zweitprüfer)");
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(UIColors.TEXT_DARK);

        content.add(title);
        content.add(Box.createVerticalStrut(32));

        // -------- Hauptkarte --------
        content.add(buildEmptyStateCard());

        return content;
    }

    // ================= Leere-Status-Card =================
    private JComponent buildEmptyStateCard() {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(48, 48, 48, 48));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));

        // Stern (Unicode – simpel & stabil)
        JLabel icon = new JLabel("☆");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 64));
        icon.setForeground(new Color(0xD1D5DB));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel text = new JLabel("Keine ausstehenden Bewertungen");
        text.setFont(new Font("SansSerif", Font.PLAIN, 16));
        text.setForeground(UIColors.TEXT_MUTED);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(icon);
        card.add(Box.createVerticalStrut(16));
        card.add(text);
        card.add(Box.createVerticalGlue());

        return new RoundedCardWrapper(card);
    }

    // ================= Rounded Border Wrapper =================
    private static class RoundedCardWrapper extends JPanel {
        RoundedCardWrapper(JComponent content) {
            setLayout(new BorderLayout());
            setOpaque(false);
            add(content, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0xE5E7EB));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            g2.dispose();
        }
    }
}
