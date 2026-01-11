package gui.design;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DesignDashboard extends JPanel {

    public DesignDashboard() {
        setBackground(UIColors.BG_APP);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(40, 60, 40, 60));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(UIColors.TEXT_DARK);

        JLabel subtitle = new JLabel("Übersicht über Ihren Bachelorarbeitsstatus");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setForeground(UIColors.TEXT_MUTED);

        add(title);
        add(Box.createVerticalStrut(6));
        add(subtitle);
        add(Box.createVerticalStrut(40));

        JPanel top = new JPanel(new GridLayout(1, 3, 32, 0));
        top.setOpaque(false);

        top.add(new DesignCard("Allgemeine Informationen", "Noch nicht begonnen", 160));
        top.add(new DesignCard("Anmeldung Bachelorarbeit", "Noch nicht möglich", 160));
        top.add(new DesignCard("Abgabe Bachelorarbeit", "Noch nicht möglich", 160));

        add(top);
        add(Box.createVerticalStrut(40));

        JPanel bottom = new JPanel(new GridLayout(1, 2, 32, 0));
        bottom.setOpaque(false);

        bottom.add(new DesignCard("Letzte Benachrichtigungen", "Keine Benachrichtigungen vorhanden", 220));
        bottom.add(new DesignCard("Wichtige Termine", "Noch keine Termine vorhanden", 220));

        add(bottom);
    }
}
