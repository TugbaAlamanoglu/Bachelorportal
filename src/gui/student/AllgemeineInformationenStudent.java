package gui.student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * Allgemeine Informationen (Panel) – Layout wie Web/Figma:
 * - Content-Container zentriert mit fester Max-Breite
 * - Alle Inhalte linksbündig
 * - Cards sauber untereinander
 */
public class AllgemeineInformationenStudent extends JPanel {

    private static final Color BG = StudentFenster.BG;
    private static final Color BORDER = StudentFenster.BORDER;
    private static final Color PRIMARY = StudentFenster.PRIMARY;
    private static final Color TEXT_DARK = StudentFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudentFenster.TEXT_MUTED;

    // Content-Breite wie Web (zentriert im Fenster)
    private static final int CONTENT_MAX_W = 1080;

    private final StudentFenster parent;

    // Felder (später DB)
    private JTextField tfTitel;
    private JTextField tfUnternehmen;
    private JTextField tfOrt;
    private JTextField tfVon;
    private JTextField tfBis;
    private JTextField tfBetreuerHft;
    private JTextField tfBetreuerU;

    private JRadioButton rbNdaJa;
    private JRadioButton rbNdaNein;
    private JLabel lblNdaFile;

    public AllgemeineInformationenStudent(StudentFenster parent, int mnr, String name, String email) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildCenterScroll(), BorderLayout.CENTER);
        add(buildBottomBar(), BorderLayout.SOUTH);
    }

    // ---------------- Center Scroll ----------------

    private JComponent buildCenterScroll() {
        // Outer: nimmt volle Breite ein (Scrollpane)
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);

        // Inner: feste max Breite, linksbündiger Content
        JPanel inner = new JPanel();
        inner.setBackground(BG);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBorder(new EmptyBorder(28, 28, 24, 28));

        // WICHTIG: alles links ausrichten
        inner.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Header
        JLabel h1 = new JLabel("Allgemeine Informationen");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(h1);

        inner.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Grunddaten zu Ihrer Bachelorarbeit");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(h2);

        inner.add(Box.createVerticalStrut(18));

        // Cards
        inner.add(cardThema());
        inner.add(Box.createVerticalStrut(16));

        inner.add(cardUnternehmenOrt());
        inner.add(Box.createVerticalStrut(16));

        inner.add(cardZeitraum());
        inner.add(Box.createVerticalStrut(16));

        inner.add(cardBetreuer());
        inner.add(Box.createVerticalStrut(16));

        inner.add(cardNda());
        inner.add(Box.createVerticalStrut(12));

        // Inner in fixed width wrapper
        JPanel fixed = wrapFixedWidth(inner);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;      // oben beginnen
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        outer.add(fixed, gbc);

        JScrollPane sp = new JScrollPane(outer);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(BG);
        return sp;
    }

    private JPanel wrapFixedWidth(JComponent inner) {
        JPanel fixed = new JPanel(new GridBagLayout());
        fixed.setBackground(BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH; // oben
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Wrapper mit Max-Breite
        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(BG);
        box.add(inner, BorderLayout.CENTER);

        box.setPreferredSize(new Dimension(CONTENT_MAX_W, box.getPreferredSize().height));
        box.setMaximumSize(new Dimension(CONTENT_MAX_W, Integer.MAX_VALUE));

        fixed.add(box, gbc);
        return fixed;
    }

    // ---------------- Cards ----------------

    private JComponent cardThema() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("Thema der Bachelorarbeit"));
        body.add(Box.createVerticalStrut(14));

        body.add(fieldLabel("Titel / Thema"));
        body.add(Box.createVerticalStrut(8));

        tfTitel = input("");
        body.add(tfTitel);

        return card;
    }

    private JComponent cardUnternehmenOrt() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("Unternehmen & Ort"));
        body.add(Box.createVerticalStrut(14));

        JPanel grid = twoColGrid();
        GridBagConstraints g = baseGbc();

        // Labels row
        g.gridy = 0;

        g.gridx = 0;
        grid.add(fieldLabel("Unternehmen"), g);

        g.gridx = 1;
        grid.add(fieldLabel("Ort"), g);

        // Fields row
        g.gridy = 1;

        tfUnternehmen = input("");
        tfOrt = input("");

        g.gridx = 0;
        grid.add(tfUnternehmen, g);

        g.gridx = 1;
        grid.add(tfOrt, g);

        body.add(grid);
        return card;
    }

    private JComponent cardZeitraum() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("Zeitraum"));
        body.add(Box.createVerticalStrut(14));

        JPanel grid = twoColGrid();
        GridBagConstraints g = baseGbc();

        g.gridy = 0;

        g.gridx = 0;
        grid.add(fieldLabel("Von"), g);

        g.gridx = 1;
        grid.add(fieldLabel("Bis"), g);

        g.gridy = 1;

        tfVon = input("");
        tfBis = input("");

        g.gridx = 0;
        grid.add(tfVon, g);

        g.gridx = 1;
        grid.add(tfBis, g);

        body.add(grid);
        return card;
    }

    private JComponent cardBetreuer() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("Betreuer"));
        body.add(Box.createVerticalStrut(14));

        body.add(fieldLabel("Betreuer an der HFT"));
        body.add(Box.createVerticalStrut(8));
        tfBetreuerHft = input("");
        body.add(tfBetreuerHft);

        body.add(Box.createVerticalStrut(14));

        body.add(fieldLabel("Betreuer im Unternehmen"));
        body.add(Box.createVerticalStrut(8));
        tfBetreuerU = input("");
        body.add(tfBetreuerU);

        return card;
    }

    private JComponent cardNda() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("NDA-Status"));
        body.add(Box.createVerticalStrut(6));

        JLabel sub = new JLabel("Geheimhaltungsvereinbarung (Non-Disclosure Agreement)");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(sub);

        body.add(Box.createVerticalStrut(16));

        body.add(fieldLabel("Liegt eine NDA vor?"));
        body.add(Box.createVerticalStrut(10));

        rbNdaJa = new JRadioButton("Ja");
        rbNdaNein = new JRadioButton("Nein");
        rbNdaJa.setOpaque(false);
        rbNdaNein.setOpaque(false);
        rbNdaJa.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rbNdaNein.setFont(new Font("SansSerif", Font.PLAIN, 13));

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbNdaJa);
        bg.add(rbNdaNein);

        // Startzustand: Keine Auswahl
        rbNdaJa.setSelected(false);
        rbNdaNein.setSelected(false);

        JPanel radios = new JPanel();
        radios.setOpaque(false);
        radios.setLayout(new BoxLayout(radios, BoxLayout.Y_AXIS));
        radios.setAlignmentX(Component.LEFT_ALIGNMENT);
        radios.add(rbNdaJa);
        radios.add(Box.createVerticalStrut(6));
        radios.add(rbNdaNein);

        body.add(radios);

        body.add(Box.createVerticalStrut(16));

        body.add(fieldLabel("NDA-Dokument (PDF)"));
        body.add(Box.createVerticalStrut(10));

        JPanel drop = new JPanel(new BorderLayout());
        drop.setOpaque(true);
        drop.setBackground(Color.WHITE);
        drop.setBorder(BorderFactory.createDashedBorder(new Color(210, 214, 220), 6f, 6f));
        drop.setPreferredSize(new Dimension(0, 150));
        drop.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        drop.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel center = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "<div style='font-size:24px; color:#777; margin-bottom:6px;'>⬆</div>"
                        + "<div style='font-size:15px; font-weight:700; color:#222;'>NDA-Dokument hochladen</div>"
                        + "<div style='font-size:12px; color:#777;'>PDF, max. 10MB</div>"
                        + "</div></html>",
                SwingConstants.CENTER
        );
        drop.add(center, BorderLayout.CENTER);

        JButton choose = new JButton("Datei auswählen");
        choose.setFocusPainted(false);
        choose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        choose.addActionListener(e -> choosePdf());

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 12));
        south.setOpaque(false);
        south.add(choose);
        drop.add(south, BorderLayout.SOUTH);

        body.add(drop);

        body.add(Box.createVerticalStrut(10));
        lblNdaFile = new JLabel("Keine Datei ausgewählt");
        lblNdaFile.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblNdaFile.setForeground(TEXT_MUTED);
        lblNdaFile.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(lblNdaFile);

        return card;
    }

    // ---------------- Bottom Bar ----------------

    private JComponent buildBottomBar() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);
        outer.setBorder(new EmptyBorder(10, 0, 18, 0));

        JPanel inner = new JPanel(new BorderLayout(12, 0));
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(0, 28, 0, 28));
        inner.setPreferredSize(new Dimension(CONTENT_MAX_W, 56));
        inner.setMaximumSize(new Dimension(CONTENT_MAX_W, 56));

        JButton btnBack = new JButton("←  Zurück");
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setPreferredSize(new Dimension(180, 48));
        btnBack.addActionListener(e -> parent.showPage(StudentFenster.PAGE_DASHBOARD));

        JButton btnSave = new JButton("Antrag einreichen");
        btnSave.setFocusPainted(false);
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(PRIMARY);
        btnSave.setOpaque(true);
        btnSave.setBorderPainted(false);
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSave.setPreferredSize(new Dimension(520, 48));
        btnSave.addActionListener(e -> onSave());

        inner.add(btnBack, BorderLayout.WEST);
        inner.add(btnSave, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        outer.add(inner, gbc);

        return outer;
    }

    // ---------------- Actions ----------------

    private void choosePdf() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("NDA PDF auswählen");
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            lblNdaFile.setText("Ausgewählt: " + f.getName());
        }
    }

    private void onSave() {
        JOptionPane.showMessageDialog(this, "Antrag eingereicht (Demo).");
    }

    // ---------------- UI Helpers ----------------

    private JPanel cardBase() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new DashboardStudent.RoundedBorder(16, BORDER));
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

    private JLabel cardTitle(String s) {
        JLabel l = new JLabel(s);
        l.setFont(new Font("SansSerif", Font.BOLD, 18));
        l.setForeground(TEXT_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel fieldLabel(String s) {
        JLabel l = new JLabel(s);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(TEXT_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField input(String value) {
        JTextField tf = new JTextField(value);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBackground(new Color(245, 246, 248));
        tf.setBorder(new EmptyBorder(12, 12, 12, 12));
        tf.setPreferredSize(new Dimension(0, 44));
        tf.setMinimumSize(new Dimension(0, 44));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return tf;
    }

    private JPanel twoColGrid() {
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);
        return grid;
    }

    private GridBagConstraints baseGbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(0, 0, 8, 16);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        return g;
    }
}