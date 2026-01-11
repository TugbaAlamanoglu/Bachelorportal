package gui.student;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;

public class AbgabeBachelorarbeit extends JPanel {

    private static final Color BG = StudentFenster.BG;
    private static final Color BORDER = StudentFenster.BORDER;
    private static final Color PRIMARY = StudentFenster.PRIMARY;
    private static final Color ACCENT_RED = StudentFenster.ACCENT_RED;
    private static final Color TEXT_DARK = StudentFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudentFenster.TEXT_MUTED;

    private static final int CONTENT_MAX_W = 1080;

    // --- Persistenz (Demo: bleibt im laufenden Programm erhalten) ---
    private static boolean submitted = false;
    private static File submittedFile = null;
    private static String submittedAbstract = "";

    // --- UI ---
    private JPanel dropArea;
    private JLabel dropTitle;
    private JLabel dropSub;

    private JPanel selectedRow;
    private JLabel selectedName;
    private JLabel selectedSize;

    private JTextArea taAbstract;
    private JLabel leftHint;
    private JLabel rightCount;

    private JButton btnSubmit;

    // State
    private File selectedFile;

    public AbgabeBachelorarbeit(StudentFenster parent, int mnr, String name, String email) {
        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildCenterScroll(), BorderLayout.CENTER);

        // wenn schon abgegeben -> UI direkt zeigen
        if (submittedFile != null) {
            selectedFile = submittedFile;
        }
        applySubmittedState();
        updateSelectedUI();
        updateAbstractUI();
        updateButtonState();
    }

    // ------------------------------------------------------------
    // Scroll + Fixed Width
    // ------------------------------------------------------------

    private JComponent buildCenterScroll() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);

        JPanel inner = new JPanel();
        inner.setBackground(BG);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBorder(new EmptyBorder(28, 28, 24, 28));
        inner.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Header
        JLabel h1 = new JLabel("Endgültige Abgabe der Bachelorarbeit");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(h1);

        inner.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Einreichung der finalen Version");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(h2);

        inner.add(Box.createVerticalStrut(18));

        // Hinweis (rot)
        inner.add(hintCard());
        inner.add(Box.createVerticalStrut(16));

        // Upload Card
        inner.add(uploadCard());
        inner.add(Box.createVerticalStrut(16));

        // Abstract Card
        inner.add(abstractCard());
        inner.add(Box.createVerticalStrut(16));

        // Checklist Card
        inner.add(checklistCard());
        inner.add(Box.createVerticalStrut(18));

        // Submit Button
        inner.add(submitButton());

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

    // ------------------------------------------------------------
    // Cards
    // ------------------------------------------------------------

    private JComponent hintCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(255, 245, 245));
        card.setBorder(new RoundedBorder(16, new Color(255, 200, 200)));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(16, 16, 16, 16));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel t = new JLabel("⚠  Wichtiger Hinweis");
        t.setFont(new Font("SansSerif", Font.BOLD, 15));
        t.setForeground(TEXT_DARK);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);

        body.add(t);
        body.add(Box.createVerticalStrut(10));
        body.add(bullet("Die endgültige Abgabe kann nur ", "einmal", " durchgeführt werden"));
        body.add(Box.createVerticalStrut(6));
        body.add(bullet("Nach der Abgabe können keine Änderungen mehr vorgenommen werden", null, null));
        body.add(Box.createVerticalStrut(6));
        body.add(bullet("Stellen Sie sicher, dass Sie die richtige Datei hochladen", null, null));
        body.add(Box.createVerticalStrut(6));
        body.add(bullet("Abgabefrist: ", "—", "")); // kannst du später dynamisch setzen

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JComponent uploadCard() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        JLabel t = new JLabel("Finale Arbeit hochladen");
        t.setFont(new Font("SansSerif", Font.BOLD, 18));
        t.setForeground(TEXT_DARK);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(t);

        body.add(Box.createVerticalStrut(6));

        JLabel sub = new JLabel("Akzeptierte Formate: PDF, DOCX, JPG (max. 50MB)");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(sub);

        body.add(Box.createVerticalStrut(18));

        JLabel l = new JLabel("Bachelorarbeit");
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(TEXT_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(l);

        body.add(Box.createVerticalStrut(10));

        dropArea = buildDropArea();
        body.add(dropArea);

        body.add(Box.createVerticalStrut(12));

        selectedRow = buildSelectedRow();
        body.add(selectedRow);

        return card;
    }

    private JComponent abstractCard() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        JLabel t = new JLabel("Kurzfassung (Abstract)");
        t.setFont(new Font("SansSerif", Font.BOLD, 18));
        t.setForeground(TEXT_DARK);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(t);

        body.add(Box.createVerticalStrut(6));

        JLabel sub = new JLabel("Zusammenfassung Ihrer Arbeit (mindestens 100 Zeichen)");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(sub);

        body.add(Box.createVerticalStrut(14));

        JLabel l = new JLabel("Kurzfassung in Deutsch oder Englisch");
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(TEXT_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(l);

        body.add(Box.createVerticalStrut(10));

        taAbstract = new JTextArea();
        taAbstract.setLineWrap(true);
        taAbstract.setWrapStyleWord(true);
        taAbstract.setFont(new Font("SansSerif", Font.PLAIN, 13));
        taAbstract.setForeground(TEXT_DARK);
        taAbstract.setBorder(new EmptyBorder(14, 14, 14, 14));
        taAbstract.setBackground(new Color(245, 246, 248));
        taAbstract.setText(submittedAbstract == null ? "" : submittedAbstract);
        taAbstract.setEditable(!submitted);

        JScrollPane sp = new JScrollPane(taAbstract);
        sp.setBorder(null);
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);
        sp.setPreferredSize(new Dimension(0, 180));
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.getVerticalScrollBar().setUnitIncrement(14);

        taAbstract.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                updateAbstractUI();
                updateButtonState();
            }
        });

        body.add(sp);

        body.add(Box.createVerticalStrut(8));

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftHint = new JLabel("Mindestens 100 Zeichen erforderlich");
        leftHint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        leftHint.setForeground(TEXT_MUTED);

        rightCount = new JLabel("0 Zeichen");
        rightCount.setFont(new Font("SansSerif", Font.BOLD, 12));
        rightCount.setForeground(new Color(240, 120, 80)); // leicht orange wie Screenshot

        bottom.add(leftHint, BorderLayout.WEST);
        bottom.add(rightCount, BorderLayout.EAST);

        body.add(bottom);

        return card;
    }

    private JComponent checklistCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(255, 248, 235));
        card.setBorder(new RoundedBorder(16, new Color(255, 210, 150)));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(16, 16, 16, 16));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel t = new JLabel("⚠  Checkliste vor der Abgabe");
        t.setFont(new Font("SansSerif", Font.BOLD, 15));
        t.setForeground(TEXT_DARK);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(t);

        body.add(Box.createVerticalStrut(10));
        body.add(bulletPlain("Haben Sie die Arbeit Korrektur gelesen?"));
        body.add(Box.createVerticalStrut(6));
        body.add(bulletPlain("Sind alle Quellen korrekt zitiert?"));
        body.add(Box.createVerticalStrut(6));
        body.add(bulletPlain("Entspricht die Formatierung den Vorgaben?"));
        body.add(Box.createVerticalStrut(6));
        body.add(bulletPlain("Ist die Kurzfassung aussagekräftig?"));
        body.add(Box.createVerticalStrut(6));
        body.add(bulletPlain("Haben Sie die richtige Datei ausgewählt?"));

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JComponent submitButton() {
        btnSubmit = new JButton("⬆  Abgabe hochladen (endgültig!)");
        btnSubmit.setFocusPainted(false);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setOpaque(true);
        btnSubmit.setBorderPainted(false);
        btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSubmit.setPreferredSize(new Dimension(0, 52));
        btnSubmit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnSubmit.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnSubmit.addActionListener(e -> submit());

        updateButtonState();
        return btnSubmit;
    }

    // ------------------------------------------------------------
    // Upload UI (Drop + Selected Row wie im Portal)
    // ------------------------------------------------------------

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

        dropTitle = new JLabel("Finale Arbeit hochladen");
        dropTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        dropTitle.setForeground(TEXT_DARK);
        dropTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        dropSub = new JLabel("PDF, DOCX, JPG – max. 50MB");
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
            @Override public void mouseClicked(MouseEvent e) {
                if (!submitted) chooseFile();
            }
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

    private void chooseFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Finale Arbeit auswählen (PDF/DOCX/JPG)");
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            submittedFile = selectedFile; // Demo-Persistenz innerhalb der App
            updateSelectedUI();
            updateButtonState();
        }
    }

    private void updateSelectedUI() {
        boolean has = (submittedFile != null);

        if (has) {
            selectedRow.setVisible(true);
            selectedName.setText(submittedFile.getName());
            selectedSize.setText(humanBytes(submittedFile.length()));
        } else {
            selectedRow.setVisible(false);
        }

        revalidate();
        repaint();
    }

    // ------------------------------------------------------------
    // Abstract UI
    // ------------------------------------------------------------

    private void updateAbstractUI() {
        int len = taAbstract.getText() == null ? 0 : taAbstract.getText().length();
        rightCount.setText(len + " Zeichen");
    }

    // ------------------------------------------------------------
    // Submit Logic
    // ------------------------------------------------------------

    private void updateButtonState() {
        if (btnSubmit == null) return;

        if (submitted) {
            btnSubmit.setText("Abgabe wurde bereits eingereicht");
            btnSubmit.setEnabled(false);
            btnSubmit.setBackground(new Color(220, 140, 140));
            return;
        }

        boolean ok = (submittedFile != null) && (taAbstract.getText() != null) && (taAbstract.getText().length() >= 100);
        btnSubmit.setText("⬆  Abgabe hochladen (endgültig!)");
        btnSubmit.setEnabled(ok);
        btnSubmit.setBackground(ok ? new Color(240, 120, 120) : new Color(240, 180, 180));
    }

    private void submit() {
        if (submitted) return;

        int res = JOptionPane.showConfirmDialog(
                this,
                "Möchten Sie die Bachelorarbeit endgültig abgeben?\nEine Änderung ist danach nicht mehr möglich.",
                "Endgültige Abgabe",
                JOptionPane.YES_NO_OPTION
        );

        if (res == JOptionPane.YES_OPTION) {
            submitted = true;
            submittedAbstract = taAbstract.getText() == null ? "" : taAbstract.getText();

            applySubmittedState();
            updateButtonState();

            JOptionPane.showMessageDialog(
                    this,
                    "Abgabe wurde erfolgreich eingereicht.",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void applySubmittedState() {
        if (taAbstract != null) {
            taAbstract.setEditable(!submitted);
        }
        if (dropArea != null) {
            dropArea.setCursor(submitted ? Cursor.getDefaultCursor() : Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    // ------------------------------------------------------------
    // Public helper for Dashboard (keine direkten Felder!)
    // ------------------------------------------------------------

    public static boolean isSubmitted() {
        return submitted;
    }

    // ------------------------------------------------------------
    // UI Helpers (wie deine anderen Seiten)
    // ------------------------------------------------------------

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

    private JLabel bulletPlain(String text) {
        JLabel l = new JLabel("• " + text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        l.setForeground(TEXT_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel bullet(String before, String bold, String after) {
        String html;
        if (bold == null) {
            html = "<html>• " + escape(before) + "</html>";
        } else {
            html = "<html>• " + escape(before) + "<b>" + escape(bold) + "</b>" + escape(after) + "</html>";
        }
        JLabel l = new JLabel(html);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        l.setForeground(TEXT_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private String humanBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        double kb = bytes / 1024.0;
        if (kb < 1024) return new DecimalFormat("#0").format(kb) + " KB";
        double mb = kb / 1024.0;
        return new DecimalFormat("#0.0").format(mb) + " MB";
    }

    private static class RoundedBorder extends AbstractBorder {
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
}