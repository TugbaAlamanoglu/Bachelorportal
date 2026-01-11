package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UebersichtAbschlussarbeiten extends JPanel {

    public UebersichtAbschlussarbeiten() {
        setOpaque(true);
        setBackground(UIColors.BG_APP);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel h1 = new JLabel("Alle Bachelorarbeiten");
        h1.setFont(new Font("SansSerif", Font.BOLD, 22));
        h1.setForeground(UIColors.TEXT_DARK);

        JLabel sub = new JLabel("Übersicht aller laufenden und abgeschlossenen Arbeiten");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(UIColors.TEXT_MUTED);

        add(h1);
        add(Box.createVerticalStrut(4));
        add(sub);
        add(Box.createVerticalStrut(18));

        ShadowCardPanel card = new ShadowCardPanel(18);
        card.setBorder(new EmptyBorder(18, 18, 18, 18));
        card.setLayout(new BorderLayout());

        String[] cols = {"Mat.-Nr.", "Name", "Thema", "Betreuer", "Anmeldung", "Abgabe", "Note"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"123456", "Anna Müller", "KI-gestützter Chatbot für Kundenservice", "Prof. Dr. Schmidt", "Genehmigt", "In Bearbeitung", "1,7"});
        model.addRow(new Object[]{"234567", "Max Weber", "Blockchain Supply Chain Management", "Prof. Dr. Müller", "Ausstehend", "Nicht gestartet", "-"});
        model.addRow(new Object[]{"345678", "Lisa Schmidt", "IoT-Sicherheit in Smart Homes", "Dr. Weber", "Genehmigt", "Eingereicht", "2,0"});
        model.addRow(new Object[]{"456789", "Tom Becker", "Mikroservice-Architektur mit Kubernetes", "Prof. Dr. Schmidt", "Genehmigt", "In Bearbeitung", "-"});
        model.addRow(new Object[]{"567890", "Sarah Klein", "Machine Learning in der Bildverarbeitung", "Prof. Dr. Müller", "Genehmigt", "Eingereicht", "1,3"});

        JTable table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.setFillsViewportHeight(true);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        card.add(sp, BorderLayout.CENTER);

        add(card);
    }
}

