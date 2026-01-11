package gui.shared;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RoundPasswordField extends JPasswordField {

    private boolean focused = false;
    private static final int RADIUS = 14;

    public RoundPasswordField(String placeholder) {
        super();

        setOpaque(false);
        setBorder(new EmptyBorder(14, 16, 14, 16));
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        setCaretColor(Color.BLACK);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                repaint();
            }
        });
    }

    public String getRealPassword() {
        return new String(getPassword());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Hintergrund
        g2.setColor(new Color(0xF3F4F6));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rand
        g2.setColor(focused ? new Color(0x2563EB) : new Color(0xD1D5DB));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);

        g2.dispose();
    }
}
