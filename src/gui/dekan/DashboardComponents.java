package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * UI-Komponenten für DashboardStudiendekan
 */

class StatCard extends JPanel {
    StatCard(String title, String subtitle, String value) {
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(16, 16, 16, 16));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 14));
        t.setForeground(UIColors.TEXT_DARK);

        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.BOLD, 28));
        v.setForeground(UIColors.PRIMARY);

        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("SansSerif", Font.PLAIN, 12));
        s.setForeground(UIColors.TEXT_MUTED);

        add(t);
        add(Box.createVerticalStrut(8));
        add(v);
        add(Box.createVerticalStrut(4));
        add(s);
    }
}

class ApprovalItem extends JPanel {
    ApprovalItem(String thema, String student, String betreuer, String status) {
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(12, 12, 12, 12));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel t = new JLabel(thema);
        t.setFont(new Font("SansSerif", Font.BOLD, 13));
        t.setForeground(UIColors.TEXT_DARK);

        JLabel s1 = new JLabel(student);
        s1.setFont(new Font("SansSerif", Font.PLAIN, 12));
        s1.setForeground(UIColors.TEXT_MUTED);

        JLabel s2 = new JLabel(betreuer);
        s2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        s2.setForeground(UIColors.TEXT_MUTED);

        JLabel st = new JLabel(status);
        st.setFont(new Font("SansSerif", Font.BOLD, 12));
        st.setForeground(UIColors.PRIMARY);

        add(t);
        add(Box.createVerticalStrut(6));
        add(s1);
        add(s2);
        add(Box.createVerticalStrut(6));
        add(st);
    }
}

class WorkRow extends JPanel {
    WorkRow(String student, String thema, String status, Color bg) {
        setOpaque(true);
        setBackground(bg);
        setBorder(new EmptyBorder(12, 12, 12, 12));
        setLayout(new BorderLayout());

        JLabel left = new JLabel("<html><b>" + student + "</b><br/>" + thema + "</html>");
        left.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel right = new JLabel(status);
        right.setFont(new Font("SansSerif", Font.BOLD, 12));
        right.setForeground(UIColors.TEXT_DARK);

        add(left, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
    }
}