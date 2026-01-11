package gui.shared;

import javax.swing.*;
import java.awt.*;

public class RoundPanel extends JPanel {
    private final int arc;
    private Color fill = Color.WHITE;

    public RoundPanel(int arc) {
        this.arc = arc;
        setOpaque(false);
    }

    public void setFill(Color c) {
        this.fill = c;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(fill);
        g2.fillRoundRect(0, 0, w, h, arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }
}
