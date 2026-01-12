package gui.dekan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UebersichtPage extends JPanel {

    private static final Color BG = StudiendekanFenster.BG;
    private static final Color BORDER = StudiendekanFenster.BORDER;
    private static final Color PRIMARY = StudiendekanFenster.PRIMARY;
    private static final Color TEXT_DARK = StudiendekanFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudiendekanFenster.TEXT_MUTED;

    private final StudiendekanFenster parent;

    public UebersichtPage(StudiendekanFenster parent, String name, String email) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildContent(), BorderLayout.CENTER);
    }

    private JComponent buildContent() {
        // Outer wrapper
        JPanel outerWrapper = new JPanel(new BorderLayout());
        outerWrapper.setBackground(BG);
        
        // Main content panel
        JPanel content = new JPanel();
        content.setBackground(BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(28, 28, 28, 28));

        // Header LINKSBÜNDIG
        JLabel h1 = new JLabel("Übersicht Abschlussarbeiten");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h1);

        content.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Übersicht aller laufenden und abgeschlossenen Arbeiten");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h2);

        content.add(Box.createVerticalStrut(40));

        // Tabelle in einer Card
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new RoundedBorder(14, BORDER));
        tableCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel inner = new JPanel(new BorderLayout());
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(18, 18, 18, 18));

        // Tabellendaten
        String[] cols = {"Mat.-Nr.", "Name", "Thema", "Betreuer", "Anmeldung", "Abgabe", "Note"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Beispiel-Daten
        model.addRow(new Object[]{"123456", "Anna Müller", "KI-gestützter Chatbot für Kundenservice", "Prof. Dr. Schmidt", "Genehmigt", "In Bearbeitung", "1,7"});
        model.addRow(new Object[]{"234567", "Max Weber", "Blockchain Supply Chain Management", "Prof. Dr. Müller", "Ausstehend", "Nicht gestartet", "-"});
        model.addRow(new Object[]{"345678", "Lisa Schmidt", "IoT-Sicherheit in Smart Homes", "Dr. Weber", "Genehmigt", "Eingereicht", "2,0"});
        model.addRow(new Object[]{"456789", "Tom Becker", "Mikroservice-Architektur mit Kubernetes", "Prof. Dr. Schmidt", "Genehmigt", "In Bearbeitung", "-"});
        model.addRow(new Object[]{"567890", "Sarah Klein", "Machine Learning in der Bildverarbeitung", "Prof. Dr. Müller", "Genehmigt", "Eingereicht", "1,3"});

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(245, 246, 248));
        table.getTableHeader().setForeground(TEXT_DARK);
        table.setFillsViewportHeight(true);
        table.setGridColor(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        inner.add(scrollPane, BorderLayout.CENTER);

        tableCard.add(inner, BorderLayout.CENTER);
        content.add(tableCard);

        content.add(Box.createVerticalStrut(20));

        // Info Text
        JLabel info = new JLabel("<html><div style='color: #666666; font-size: 13px;'>Angezeigt werden alle Bachelorarbeiten, unabhängig vom aktuellen Status.</div></html>");
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(info);

        content.add(Box.createVerticalGlue());
        
        outerWrapper.add(content, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(outerWrapper);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(BG);
        return sp;
    }

    static class RoundedBorder extends javax.swing.border.LineBorder {
        private final int radius;
        RoundedBorder(int radius, Color color) {
            super(color, 1, true);
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }
    }
}