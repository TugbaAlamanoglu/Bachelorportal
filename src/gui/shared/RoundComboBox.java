package gui.shared;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoundComboBox<E> extends JComboBox<E> {

    public RoundComboBox(E[] items) {
        super(items);
        setOpaque(false);
        setBackground(UIColors.FIELD_BG);
        setForeground(UIColors.FIELD_TEXT);
        setBorder(new EmptyBorder(10, 12, 10, 12));

        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setBorder(new EmptyBorder(10, 12, 10, 12));
                return c;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
        g2.dispose();
        super.paintComponent(g);
    }
}
