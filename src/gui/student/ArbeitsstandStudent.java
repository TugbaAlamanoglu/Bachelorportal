package gui.student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ArbeitsstandStudent extends JPanel {

    private static final Color BG = StudentFenster.BG;
    private static final Color BORDER = StudentFenster.BORDER;
    private static final Color PRIMARY = StudentFenster.PRIMARY;
    private static final Color TEXT_DARK = StudentFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudentFenster.TEXT_MUTED;

    private static final int CONTENT_MAX_W = 1080;

    private final StudentFenster parent;

    // UI
    private JLabel dropTitle;
    private JLabel dropSub;
    private JPanel selectedRow;
    private JLabel selectedName;
    private JLabel selectedSize;
    private JTextArea taComment;
    private JButton btnUpload;

    private JPanel historyList;

    // State
    private File selectedFile;
    private final java.util.List<UploadEntry> uploads = new ArrayList<>();

    public ArbeitsstandStudent(StudentFenster parent, int mnr, String name, String email) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildCenterScroll(), BorderLayout.CENTER);

        seedDemoHistory();
        rebuildHistory();
        updateSelectedUI();
    }

    // ---------------- Center ----------------

    private JComponent buildCenterScroll() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);

        JPanel inner = new JPanel();
        inner.setBackground(BG);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBorder(new EmptyBorder(28, 28, 24, 28));
        inner.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel h1 = new JLabel("Arbeitsstand & Uploads");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(h1);

        inner.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Teilen Sie Zwischenversionen mit Ihrem Betreuer");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(h2);

        inner.add(Box.createVerticalStrut(18));

        inner.add(cardUpload());
        inner.add(Box.createVerticalStrut(16));

        inner.add(cardHistory());
        inner.add(Box.createVerticalStrut(24));

        JPanel fixed = wrapFixedWidth(inner);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        outer.add(fixed, gbc);

        JScrollPane sp = new JScrollPane(outer);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(BG);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return sp;
    }

    private JPanel wrapFixedWidth(JComponent inner) {
        JPanel fixed = new JPanel(new GridBagLayout());
        fixed.setBackground(BG);

        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(BG);
        box.add(inner, BorderLayout.CENTER);
        box.setMaximumSize(new Dimension(CONTENT_MAX_W, Integer.MAX_VALUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        fixed.add(box, gbc);
        return fixed;
    }

    // ---------------- Cards ----------------

    private JComponent cardUpload() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        JLabel t = new JLabel("Neue Version hochladen");
        t.setFont(new Font("SansSerif", Font.BOLD, 18));
        t.setForeground(TEXT_DARK);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(t);

        body.add(Box.createVerticalStrut(6));

        JLabel sub = new JLabel("Laden Sie Zwischenversionen, Kapitel oder Entwürfe für Feedback hoch");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(sub);

        body.add(Box.createVerticalStrut(18));

        JLabel lPick = new JLabel("Datei auswählen");
        lPick.setFont(new Font("SansSerif", Font.BOLD, 13));
        lPick.setForeground(TEXT_DARK);
        lPick.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(lPick);

        body.add(Box.createVerticalStrut(10));

        JPanel drop = buildDropArea();
        body.add(drop);

        body.add(Box.createVerticalStrut(12));

        selectedRow = buildSelectedRow();
        body.add(selectedRow);

        body.add(Box.createVerticalStrut(16));

        JLabel lComment = new JLabel("Kommentar");
        lComment.setFont(new Font("SansSerif", Font.BOLD, 13));
        lComment.setForeground(TEXT_DARK);
        lComment.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(lComment);

        body.add(Box.createVerticalStrut(10));

        taComment = new JTextArea();
        taComment.setLineWrap(true);
        taComment.setWrapStyleWord(true);
        taComment.setFont(new Font("SansSerif", Font.PLAIN, 13));
        taComment.setForeground(TEXT_DARK);
        taComment.setBorder(new EmptyBorder(14, 14, 14, 14));
        taComment.setBackground(new Color(245, 246, 248));
        taComment.setRows(6);

        JScrollPane sp = new JScrollPane(taComment);
        sp.setBorder(null);
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);
        sp.setPreferredSize(new Dimension(0, 180));
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.getVerticalScrollBar().setUnitIncrement(14);

        taComment.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                if ("Beschreiben Sie, was sich in dieser Version geändert hat oder worauf der Betreuer achten soll...".equals(taComment.getText())) {
                    taComment.setText("");
                    taComment.setForeground(TEXT_DARK);
                }
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                if (taComment.getText().trim().isEmpty()) {
                    taComment.setText("Beschreiben Sie, was sich in dieser Version geändert hat oder worauf der Betreuer achten soll...");
                    taComment.setForeground(TEXT_MUTED);
                }
            }
        });
        taComment.setText("Beschreiben Sie, was sich in dieser Version geändert hat oder worauf der Betreuer achten soll...");
        taComment.setForeground(TEXT_MUTED);

        body.add(sp);
        body.add(Box.createVerticalStrut(16));

        btnUpload = new JButton("  Version hochladen");
        btnUpload.setFocusPainted(false);
        btnUpload.setForeground(Color.WHITE);
        btnUpload.setBackground(new Color(140, 178, 214));
        btnUpload.setOpaque(true);
        btnUpload.setBorderPainted(false);
        btnUpload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnUpload.setPreferredSize(new Dimension(0, 48));
        btnUpload.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        btnUpload.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnUpload.addActionListener(e -> doUpload());

        body.add(btnUpload);

        return card;
    }

    private JPanel buildDropArea() {
        JPanel drop = new JPanel(new BorderLayout());
        drop.setOpaque(true);
        drop.setBackground(Color.WHITE);
        drop.setBorder(BorderFactory.createDashedBorder(new Color(210, 214, 220), 6f, 6f));
        drop.setPreferredSize(new Dimension(0, 150));
        drop.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        drop.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("⬆");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 28));
        icon.setForeground(new Color(120, 120, 120));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        dropTitle = new JLabel("Datei hochladen");
        dropTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        dropTitle.setForeground(TEXT_DARK);
        dropTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        dropSub = new JLabel("PDF, DOCX – max. 25MB");
        dropSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dropSub.setForeground(TEXT_MUTED);
        dropSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(Box.createVerticalGlue());
        center.add(icon);
        center.add(Box.createVerticalStrut(6));
        center.add(dropTitle);
        center.add(Box.createVerticalStrut(4));
        center.add(dropSub);
        center.add(Box.createVerticalGlue());

        drop.add(center, BorderLayout.CENTER);

        drop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        drop.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { chooseFile(); }
        });

        return drop;
    }

    private JPanel buildSelectedRow() {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setOpaque(true);
        row.setBackground(new Color(234, 244, 255));
        row.setBorder(new LineBorder(new Color(198, 224, 255), 1, true));
        row.setPreferredSize(new Dimension(0, 46));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel("📄");
        icon.setBorder(new EmptyBorder(0, 12, 0, 0));
        icon.setFont(new Font("SansSerif", Font.PLAIN, 16));
        row.add(icon, BorderLayout.WEST);

        selectedName = new JLabel("Keine Datei ausgewählt");
        selectedName.setFont(new Font("SansSerif", Font.BOLD, 13));
        selectedName.setForeground(new Color(20, 70, 140));

        JPanel mid = new JPanel(new BorderLayout());
        mid.setOpaque(false);
        mid.add(selectedName, BorderLayout.CENTER);
        row.add(mid, BorderLayout.CENTER);

        selectedSize = new JLabel("");
        selectedSize.setOpaque(true);
        selectedSize.setBackground(new Color(240, 240, 240));
        selectedSize.setForeground(TEXT_DARK);
        selectedSize.setFont(new Font("SansSerif", Font.BOLD, 12));
        selectedSize.setBorder(new EmptyBorder(6, 10, 6, 10));

        JPanel east = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        east.setOpaque(false);
        east.add(selectedSize);

        row.add(east, BorderLayout.EAST);

        row.setVisible(false);
        return row;
    }

    private JComponent cardHistory() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        JLabel t = new JLabel("Upload-Verlauf");
        t.setFont(new Font("SansSerif", Font.BOLD, 18));
        t.setForeground(TEXT_DARK);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(t);

        body.add(Box.createVerticalStrut(6));

        JLabel sub = new JLabel("Alle hochgeladenen Versionen und Feedback des Betreuers");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(sub);

        body.add(Box.createVerticalStrut(16));

        historyList = new JPanel();
        historyList.setOpaque(false);
        historyList.setLayout(new BoxLayout(historyList, BoxLayout.Y_AXIS));
        historyList.setAlignmentX(Component.LEFT_ALIGNMENT);

        body.add(historyList);
        return card;
    }

    // ---------------- Actions ----------------

    private void chooseFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Datei auswählen (PDF/DOCX)");
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            updateSelectedUI();
        }
    }

    private void doUpload() {
        if (selectedFile == null) return;

        String comment = taComment.getText().trim();
        if ("Beschreiben Sie, was sich in dieser Version geändert hat oder worauf der Betreuer achten soll...".equals(comment)) {
            comment = "";
        }

        UploadEntry entry = new UploadEntry(selectedFile, LocalDateTime.now(), comment, null);

        uploads.add(0, entry);
        rebuildHistory();

        JOptionPane.showMessageDialog(this,
                "Version hochgeladen: " + selectedFile.getName() + "\n(Demo: Betreuer wurde benachrichtigt)",
                "Upload erfolgreich",
                JOptionPane.INFORMATION_MESSAGE);

        selectedFile = null;
        updateSelectedUI();
    }

    // ---------------- History UI ----------------

    private void rebuildHistory() {
        historyList.removeAll();

        if (uploads.isEmpty()) {
            JLabel empty = new JLabel("Noch keine Uploads vorhanden.");
            empty.setFont(new Font("SansSerif", Font.PLAIN, 13));
            empty.setForeground(TEXT_MUTED);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            historyList.add(empty);
        } else {
            for (UploadEntry e : uploads) {
                historyList.add(historyRow(e));
                historyList.add(Box.createVerticalStrut(14));
            }
        }

        historyList.revalidate();
        historyList.repaint();
    }

    // ✅ NUR Breite erzwingen, Höhe NICHT begrenzen (sonst kollabiert "top")
    private static void leftFullWidth(JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    private JComponent historyRow(UploadEntry e) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(true);
        row.setBackground(Color.WHITE);
        row.setBorder(new RoundedBorder(14, BORDER));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(14, 14, 14, 14));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- TOP: Dateiname + Download (niemals height-fixen!) ---
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));

        JLabel fileIcon = new JLabel("📄");
        fileIcon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        left.add(fileIcon);
        left.add(Box.createHorizontalStrut(10));

        JLabel name = new JLabel(e.file.getName());
        name.setFont(new Font("SansSerif", Font.BOLD, 14));
        name.setForeground(TEXT_DARK);
        left.add(name);

        top.add(left, BorderLayout.WEST);

        JButton dl = new JButton("⬇");
        dl.setFocusPainted(false);
        dl.setBorderPainted(false);
        dl.setContentAreaFilled(false);
        dl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dl.setToolTipText("Datei öffnen");
        dl.addActionListener(ev -> openFile(e.file));
        top.add(dl, BorderLayout.EAST);

        // ✅ top links verankern
        top.setAlignmentX(Component.LEFT_ALIGNMENT);

        body.add(top);
        body.add(Box.createVerticalStrut(6));

        // --- Datum: links direkt darunter ---
        JLabel meta = new JLabel("📅 " + e.time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")));
        meta.setFont(new Font("SansSerif", Font.PLAIN, 12));
        meta.setForeground(TEXT_MUTED);
        meta.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(meta);

        // --- Kommentarbox: voller Block links ---
        if (e.comment != null && !e.comment.isBlank()) {
            body.add(Box.createVerticalStrut(10));

            JPanel c = new JPanel(new BorderLayout());
            c.setOpaque(true);
            c.setBackground(new Color(245, 246, 248));
            c.setBorder(new EmptyBorder(10, 10, 10, 10));
            leftFullWidth(c);

            JLabel ct = new JLabel(e.comment);
            ct.setFont(new Font("SansSerif", Font.PLAIN, 13));
            ct.setForeground(TEXT_DARK);
            c.add(ct, BorderLayout.CENTER);

            body.add(c);
        }

        // --- Feedback: links unter Kommentar (wie Screenshot) ---
        if (e.supervisorFeedback != null && !e.supervisorFeedback.isBlank()) {
            body.add(Box.createVerticalStrut(12));

            JPanel fb = new JPanel();
            fb.setOpaque(true);
            fb.setBackground(new Color(234, 244, 255));
            fb.setBorder(new LineBorder(new Color(198, 224, 255), 1, true));
            fb.setLayout(new BoxLayout(fb, BoxLayout.Y_AXIS));
            fb.setBorder(new EmptyBorder(12, 12, 12, 12));
            leftFullWidth(fb);

            JLabel title = new JLabel("💬  Feedback vom Betreuer");
            title.setFont(new Font("SansSerif", Font.BOLD, 13));
            title.setForeground(new Color(20, 70, 140));
            title.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel txt = new JLabel(e.supervisorFeedback);
            txt.setFont(new Font("SansSerif", Font.PLAIN, 13));
            txt.setForeground(new Color(20, 70, 140));
            txt.setBorder(new EmptyBorder(6, 0, 0, 0));
            txt.setAlignmentX(Component.LEFT_ALIGNMENT);

            fb.add(title);
            fb.add(txt);

            body.add(fb);
        }

        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent me) { openFile(e.file); }
        });

        row.add(body, BorderLayout.CENTER);
        return row;
    }

    private void openFile(File f) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(f);
            } else {
                JOptionPane.showMessageDialog(this, "Desktop.open wird auf diesem System nicht unterstützt.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Konnte Datei nicht öffnen:\n" + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- UI state ----------------

    private void updateSelectedUI() {
        boolean has = (selectedFile != null);

        if (has) {
            dropTitle.setText(selectedFile.getName());
            dropTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
            dropSub.setText("PDF, DOCX – max. 25MB");

            selectedRow.setVisible(true);
            selectedName.setText(selectedFile.getName());
            selectedSize.setText(humanBytes(selectedFile.length()));

            btnUpload.setEnabled(true);
            btnUpload.setBackground(PRIMARY);
        } else {
            dropTitle.setText("Datei hochladen");
            dropTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
            dropSub.setText("PDF, DOCX – max. 25MB");

            selectedRow.setVisible(false);

            btnUpload.setEnabled(false);
            btnUpload.setBackground(new Color(140, 178, 214));
        }

        revalidate();
        repaint();
    }

    private String humanBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        double kb = bytes / 1024.0;
        if (kb < 1024) return new DecimalFormat("#0").format(kb) + " KB";
        double mb = kb / 1024.0;
        return new DecimalFormat("#0.0").format(mb) + " MB";
    }

    // ---------------- Demo Seed ----------------

    private void seedDemoHistory() {
        File fake1 = new File(System.getProperty("user.home"), "Kapitel_1_Einleitung.pdf");
        uploads.add(new UploadEntry(
                fake1,
                LocalDateTime.now().minusDays(8).withHour(14).withMinute(12).withSecond(12),
                "Erste Version der Einleitung",
                "Guter Anfang! Bitte die Forschungsfrage noch präziser formulieren."
        ));

        File fake2 = new File(System.getProperty("user.home"), "Literaturrecherche_Stand.pdf");
        uploads.add(new UploadEntry(
                fake2,
                LocalDateTime.now().minusDays(15).withHour(14).withMinute(12).withSecond(12),
                "Übersicht der relevanten Quellen",
                null
        ));
    }

    // ---------------- UI Helpers ----------------

    private JPanel cardBase() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new RoundedBorder(16, BORDER));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        return card;
    }

    private JPanel cardBody(JPanel card) {
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(18, 18, 18, 18));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(body, BorderLayout.CENTER);
        return body;
    }

    private static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }
    }

    // ---------------- Model ----------------

    private static class UploadEntry {
        final File file;
        final LocalDateTime time;
        final String comment;
        final String supervisorFeedback;

        UploadEntry(File file, LocalDateTime time, String comment, String supervisorFeedback) {
            this.file = file;
            this.time = time;
            this.comment = comment;
            this.supervisorFeedback = supervisorFeedback;
        }
    }
}
