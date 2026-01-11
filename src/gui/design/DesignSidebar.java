package gui.design;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class DesignSidebar extends JPanel {

    private final Map<String, JPanel> items = new HashMap<>();
    private SidebarListener listener;

    public interface SidebarListener {
        void onNavigate(String page);
    }

    public DesignSidebar() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(300, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0xE5E7EB)));

        add(buildBrand());

        addItem("dashboard", "Dashboard");
        addItem("allgemein", "Allgemeine Informationen");
        addItem("anmeldung", "Anmeldung Bachelorarbeit");
        addItem("arbeitsstand", "Arbeitsstand & Uploads");
        addItem("abgabe", "Endgültige Abgabe");
        addItem("bachelorseminar", "Bachelorseminar");
        addItem("note", "Note");

        add(Box.createVerticalGlue());

        setActive("dashboard");
    }

    public void setSidebarListener(SidebarListener l) {
        this.listener = l;
    }

    public void setActive(String key) {
        for (String k : items.keySet()) {
            JPanel p = items.get(k);
            JLabel label = (JLabel) p.getComponent(0);
            boolean active = k.equals(key);

            p.setBackground(active ? UIColors.PRIMARY : Color.WHITE);
            label.setForeground(active ? Color.WHITE : UIColors.TEXT_DARK);
        }
    }

    private void addItem(final String key, String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        panel.setBackground(Color.WHITE);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setBorder(new EmptyBorder(16, 24, 16, 24));
        label.setForeground(UIColors.TEXT_DARK);

        panel.add(label, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {
                    listener.onNavigate(key);
                }
                setActive(key);
            }
        });

        items.put(key, panel);
        add(panel);
    }

    private JComponent buildBrand() {
        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setBorder(new EmptyBorder(24, 24, 24, 24));
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));

        JLabel hft = new JLabel("HFT Stuttgart");
        hft.setFont(new Font("SansSerif", Font.BOLD, 18));
        hft.setForeground(UIColors.TEXT_DARK);

        JLabel sub = new JLabel("BA-Portal");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(UIColors.TEXT_MUTED);

        brand.add(hft);
        brand.add(Box.createVerticalStrut(4));
        brand.add(sub);

        return brand;
    }
}