package gui.betreuer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StudentenSucheView extends JPanel {

    private final DashboardBetreuer parent;

    private final List<Row> rows = List.of(
            new Row("123456", "Anna Müller", "KI-gestützter Chatbot", "Prof. Dr. Schmidt", "Genehmigt", "In Bearbeitung", "-"),
            new Row("234567", "Max Weber", "Blockchain Supply Chain", "Prof. Dr. Schmidt", "Ausstehend", "Nicht gestartet", "-"),
            new Row("345678", "Lisa Schmidt", "IoT-Sicherheit in Smart Homes", "Prof. Dr. Schmidt", "Genehmigt", "Eingereicht", "1.7"),
            new Row("456789", "Tom Becker", "Mikroservice-Architektur", "Prof. Dr. Schmidt", "Genehmigt", "In Bearbeitung", "-")
    );

    public StudentenSucheView(DashboardBetreuer parent) {
        this.parent = parent;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 0, 0, 0));

        add(buildCard(), BorderLayout.CENTER);
    }

    private JComponent buildCard() {
        DashboardBetreuer.RoundedPanel card = new DashboardBetreuer.RoundedPanel(18);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Studenten-Suche");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(DashboardBetreuer.TEXT);

        JLabel sub = new JLabel("Suchen Sie nach Studenten, Matrikelnummern oder Themen");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(DashboardBetreuer.TEXT_MUTED);

        JLabel label = new JLabel("Suche");
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(DashboardBetreuer.TEXT);
        label.setBorder(new EmptyBorder(16, 0, 8, 0));

        JTextField search = new JTextField("   🔍   Name, Matrikelnummer oder Thema eingeben…");
        search.setForeground(new Color(0x6B7280));
        search.setBackground(new Color(0xFBFBFC));
        search.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DashboardBetreuer.BORDER),
                new EmptyBorder(12, 12, 12, 12)
        ));
        search.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Placeholder Verhalten
        search.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                if (search.getText().contains("Name, Matrikelnummer")) {
                    search.setText("");
                    search.setForeground(DashboardBetreuer.TEXT);
                }
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                if (search.getText().isBlank()) {
                    search.setText("   🔍   Name, Matrikelnummer oder Thema eingeben…");
                    search.setForeground(new Color(0x6B7280));
                }
            }
        });

        top.add(title);
        top.add(Box.createVerticalStrut(4));
        top.add(sub);
        top.add(label);
        top.add(search);

        JTable table = new JTable(new Model(rows));
        table.setRowHeight(46);
        table.setFillsViewportHeight(true);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(0xECEFF3));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setForeground(DashboardBetreuer.TEXT_MUTED);

        // Renderer für Chips in Spalten Anmeldung/Abgabe
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(4).setCellRenderer(new ChipRenderer());
        table.getColumnModel().getColumn(5).setCellRenderer(new ChipRenderer());
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

        // Klick auf "Auge" (Spalte 7) -> Details
        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int r = table.rowAtPoint(e.getPoint());
                int c = table.columnAtPoint(e.getPoint());
                if (r >= 0 && c == 7) {
                    Row row = rows.get(r);
                    StudentenDetailView dlg = new StudentenDetailView(
                            parent,
                            row.name,
                            row.matrikel,
                            (row.name.toLowerCase().replace(" ", ".") + "@hft-stuttgart.de"),
                            row.thema,
                            row.betreuer,
                            row.anmeldung,
                            row.abgabe,
                            row.note
                    );
                    dlg.setVisible(true);
                }
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DashboardBetreuer.BORDER),
                new EmptyBorder(0, 0, 0, 0)
        ));

        JLabel bottom = new JLabel("4 von 4 Studenten");
        bottom.setForeground(DashboardBetreuer.TEXT_MUTED);
        bottom.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bottom.setBorder(new EmptyBorder(12, 2, 0, 0));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(18, 0, 0, 0));
        centerPanel.add(sp, BorderLayout.CENTER);
        centerPanel.add(bottom, BorderLayout.SOUTH);

        card.add(top, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);

        return card;
    }

    // ---- Model ----
    static class Row {
        final String matrikel, name, thema, betreuer, anmeldung, abgabe, note;
        Row(String matrikel, String name, String thema, String betreuer, String anmeldung, String abgabe, String note) {
            this.matrikel = matrikel;
            this.name = name;
            this.thema = thema;
            this.betreuer = betreuer;
            this.anmeldung = anmeldung;
            this.abgabe = abgabe;
            this.note = note;
        }
    }

    static class Model extends AbstractTableModel {
        private final List<Row> rows;
        private final String[] cols = {"Mat.-Nr.", "Name", "Thema", "Betreuer", "Anmeldung", "Abgabe", "Note", ""};

        Model(List<Row> rows) { this.rows = rows; }

        @Override public int getRowCount() { return rows.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int column) { return cols[column]; }

        @Override public Object getValueAt(int rowIndex, int columnIndex) {
            Row r = rows.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> r.matrikel;
                case 1 -> r.name;
                case 2 -> r.thema;
                case 3 -> r.betreuer;
                case 4 -> r.anmeldung;
                case 5 -> r.abgabe;
                case 6 -> r.note;
                case 7 -> "👁";
                default -> "";
            };
        }

        @Override public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

    static class ChipRenderer extends DefaultTableCellRenderer {
        @Override public Component getTableCellRendererComponent(JTable table, Object value,
                                                                boolean isSelected, boolean hasFocus,
                                                                int row, int column) {
            JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setFont(new Font("SansSerif", Font.BOLD, 12));
            l.setBorder(new EmptyBorder(6, 10, 6, 10));
            l.setOpaque(true);

            String s = String.valueOf(value);
            if ("Genehmigt".equalsIgnoreCase(s) || "In Bearbeitung".equalsIgnoreCase(s) || "Eingereicht".equalsIgnoreCase(s)) {
                l.setBackground(DashboardBetreuer.BLUE);
                l.setForeground(Color.WHITE);
            } else {
                l.setBackground(new Color(0xEEF2F7));
                l.setForeground(DashboardBetreuer.TEXT);
            }
            return l;
        }
    }
}

