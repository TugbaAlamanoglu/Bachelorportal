package gui.student;

import gui.shared.RoundPanel;
import gui.shared.RoundTextField;
import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AnmeldungBachelorarbeitStudent extends JPanel {

    // gleiche Content-Breite wie bei AllgemeineInformationenStudent
    private static final int CONTENT_MAX_W = 1080;

    private final StudentFenster parent;
    private final int mnr;
    private final String name;
    private final String email;

    private JCheckBox cbAccept;
    private JButton btnSubmit;
    private JButton btnBack;

    // Startzustand: noch NICHT eingereicht
    private boolean submitted = false;

    public AnmeldungBachelorarbeitStudent(StudentFenster parent, int mnr, String name, String email) {
        this.parent = parent;
        this.mnr = mnr;
        this.name = name;
        this.email = email;

        buildUI();
        applyState();
    }

    // optional: falls irgendwo noch ohne Parameter erstellt wird
    public AnmeldungBachelorarbeitStudent() {
        this(null, 0, "", "");
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBackground(UIColors.BG_APP);

        add(buildScrollContent(), BorderLayout.CENTER);
        add(buildBottomBar(), BorderLayout.SOUTH);
    }

    // ===== Scroll Content bleibt 1:1 wie bei dir (ohne Footer-Button im Scroll) =====
    private JComponent buildScrollContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(28, 36, 28, 36)); // schöne Außen-Padding wie im Mockup

        // ===== Page Header (links bündig) =====
        JLabel title = new JLabel("Anmeldung der Bachelorarbeit");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(UIColors.TEXT_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(title);

        content.add(Box.createVerticalStrut(6));

        JLabel subtitle = new JLabel("Offizielle Anmeldung beim Prüfungsamt");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setForeground(UIColors.TEXT_MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(subtitle);

        content.add(Box.createVerticalStrut(18));

        // ===== Startzustand Banner (ohne Genehmigt/Grün) =====
        content.add(buildBannerStart());
        content.add(Box.createVerticalStrut(18));

        // ===== Veröffentlichung Card =====
        content.add(buildVeröffentlichungCard());
        content.add(Box.createVerticalStrut(18));

        // ===== Bedingungen Card =====
        content.add(buildBedingungenCard());
        content.add(Box.createVerticalStrut(18));

        // ===== Anmeldedatum Card =====
        content.add(buildAnmeldedatumCard());
        content.add(Box.createVerticalStrut(22));

        // WICHTIG: kein Button mehr im Scroll!
        // Button kommt unten in die Bottom-Bar (wie Allgemeine Informationen)

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(true);
        scroll.getViewport().setBackground(UIColors.BG_APP);
        scroll.setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        return scroll;
    }

    // ===== Bottom Bar wie AllgemeineInformationenStudent (Zurück links, großer Button rechts) =====
    private JComponent buildBottomBar() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(UIColors.BG_APP);
        outer.setBorder(new EmptyBorder(10, 0, 18, 0));

        JPanel inner = new JPanel(new BorderLayout(12, 0));
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(0, 28, 0, 28));
        inner.setPreferredSize(new Dimension(CONTENT_MAX_W, 56));
        inner.setMaximumSize(new Dimension(CONTENT_MAX_W, 56));

        // kleiner Zurück-Button links
        btnBack = new JButton("←  Zurück");
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setPreferredSize(new Dimension(180, 48));
        btnBack.addActionListener(e -> {
            // wie bei Allgemeine Informationen: zurück zum Dashboard
            if (parent != null) parent.showPage(StudentFenster.PAGE_DASHBOARD);
        });

        // großer Einreichen-Button rechts (Design wie dein ursprünglicher btnSubmit)
        btnSubmit = new JButton("Anmeldung einreichen");
        btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBackground(UIColors.PRIMARY);
        btnSubmit.setBorderPainted(false);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setOpaque(true);
        btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSubmit.setPreferredSize(new Dimension(520, 48));
        btnSubmit.addActionListener(e -> onSubmit());

        inner.add(btnBack, BorderLayout.WEST);
        inner.add(btnSubmit, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        outer.add(inner, gbc);

        return outer;
    }

    // ================== Cards ==================

    private JComponent buildBannerStart() {
        RoundPanel banner = new RoundPanel(14);
        banner.setFill(new Color(245, 246, 248));
        banner.setLayout(new BorderLayout());
        banner.setBorder(new EmptyBorder(14, 16, 14, 16));
        banner.setAlignmentX(Component.LEFT_ALIGNMENT);

        // links: Icon + Text
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));

        JLabel icon = new JLabel("\u23F3"); // ⏳
        icon.setFont(new Font("SansSerif", Font.PLAIN, 20));
        icon.setForeground(new Color(120, 120, 120));
        left.add(icon);
        left.add(Box.createHorizontalStrut(10));

        JPanel texts = new JPanel();
        texts.setOpaque(false);
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));

        JLabel t1 = new JLabel("Anmeldung noch nicht eingereicht");
        t1.setFont(new Font("SansSerif", Font.BOLD, 14));
        t1.setForeground(UIColors.TEXT_DARK);

        JLabel t2 = new JLabel("Bitte Formular ausfüllen und am Ende einreichen.");
        t2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        t2.setForeground(UIColors.TEXT_MUTED);

        texts.add(t1);
        texts.add(Box.createVerticalStrut(3));
        texts.add(t2);

        left.add(texts);

        // rechts: Badge
        JLabel badge = new JLabel("Nicht eingereicht");
        badge.setOpaque(true);
        badge.setBackground(new Color(235, 236, 240));
        badge.setForeground(new Color(90, 90, 90));
        badge.setBorder(new EmptyBorder(6, 10, 6, 10));
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(badge);

        banner.add(left, BorderLayout.WEST);
        banner.add(right, BorderLayout.EAST);

        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        banner.setPreferredSize(new Dimension(980, 72));

        return banner;
    }

    private JComponent buildVeröffentlichungCard() {
        RoundPanel card = card();
        card.add(sectionTitle("Veröffentlichung"));
        card.add(Box.createVerticalStrut(4));
        card.add(sectionSub("Einwilligung zur Veröffentlichung von Titel und Name"));
        card.add(Box.createVerticalStrut(14));

        JLabel q = new JLabel("Darf die HFT Stuttgart Ihren Namen und den Titel der Arbeit veröffentlichen?");
        q.setFont(new Font("SansSerif", Font.BOLD, 14));
        q.setForeground(UIColors.TEXT_DARK);
        q.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(q);

        card.add(Box.createVerticalStrut(12));

        JRadioButton rbYes = new JRadioButton("Ja, ich erlaube die Veröffentlichung");
        JRadioButton rbNo  = new JRadioButton("Nein, ich möchte nicht veröffentlicht werden");

        ButtonGroup g = new ButtonGroup();
        g.add(rbYes);
        g.add(rbNo);
        rbYes.setSelected(true);

        styleRadio(rbYes);
        styleRadio(rbNo);

        card.add(rbYes);
        card.add(Box.createVerticalStrut(8));
        card.add(rbNo);

        card.add(Box.createVerticalStrut(14));

        RoundPanel info = new RoundPanel(12);
        info.setFill(new Color(235, 243, 255));
        info.setLayout(new BorderLayout());
        info.setBorder(new EmptyBorder(12, 12, 12, 12));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoText = new JLabel(
                "<html><b>Hinweis:</b> Die Veröffentlichung umfasst lediglich Ihren Namen und den Titel der Arbeit in der Hochschulbibliothek. " +
                        "Der Volltext wird nur mit separater Zustimmung veröffentlicht.</html>"
        );
        infoText.setForeground(new Color(30, 80, 160));
        infoText.setFont(new Font("SansSerif", Font.PLAIN, 13));
        info.add(infoText, BorderLayout.CENTER);

        card.add(info);

        return cardWrap(card);
    }

    private JComponent buildBedingungenCard() {
        RoundPanel card = card();
        card.add(sectionTitle("Bedingungen der Bachelorarbeit"));
        card.add(Box.createVerticalStrut(4));
        card.add(sectionSub("Bitte lesen Sie die folgenden Bedingungen sorgfältig durch"));
        card.add(Box.createVerticalStrut(14));

        JTextArea ta = new JTextArea();
        ta.setText("""
BEDINGUNGEN FÜR DIE BACHELORARBEIT
Hochschule für Technik Stuttgart

§ 1 Anmeldung und Bearbeitungszeit
(1) Die Bachelorarbeit wird nach erfolgreicher Anmeldung beim Prüfungsamt ausgegeben.
(2) Die Bearbeitungszeit beträgt drei Monate und beginnt mit dem Ausgabedatum.
(3) Eine Verlängerung der Bearbeitungszeit ist nur in begründeten Ausnahmefällen möglich.

§ 2 Thema und Betreuung
(1) Das Thema wird in Absprache mit dem betreuenden Professor festgelegt.
(2) Der Betreuer steht während der Bearbeitungszeit für fachliche Fragen zur Verfügung.
(3) Ein Themenwechsel ist nach der Anmeldung nicht mehr möglich.
""");
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 13));
        ta.setBackground(new Color(248, 249, 251));
        ta.setBorder(new EmptyBorder(12, 12, 12, 12));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        JScrollPane sp = new JScrollPane(ta);
        sp.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230)));
        sp.setPreferredSize(new Dimension(920, 230));
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(sp);
        card.add(Box.createVerticalStrut(14));

        cbAccept = new JCheckBox("Ich habe die Bedingungen gelesen und akzeptiert");
        cbAccept.setBackground(UIColors.CARD_BG);
        cbAccept.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbAccept.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbAccept.addActionListener(e -> applyState());
        card.add(cbAccept);

        return cardWrap(card);
    }

    private JComponent buildAnmeldedatumCard() {
        RoundPanel card = card();
        card.add(sectionTitle("Anmeldedatum"));
        card.add(Box.createVerticalStrut(4));
        card.add(sectionSub("Datum der Anmeldung"));
        card.add(Box.createVerticalStrut(14));

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd. MM. yyyy"));
        RoundTextField tfDate = new RoundTextField(today);
        tfDate.setEnabled(false);
        tfDate.setAlignmentX(Component.LEFT_ALIGNMENT);
        tfDate.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        tfDate.setPreferredSize(new Dimension(920, 50));

        card.add(tfDate);

        return cardWrap(card);
    }

    // ================== State ==================

    private void applyState() {
        if (btnSubmit == null) return;

        if (submitted) {
            btnSubmit.setEnabled(false);
            if (cbAccept != null) cbAccept.setEnabled(false);
            return;
        }

        boolean ok = cbAccept != null && cbAccept.isSelected();
        btnSubmit.setEnabled(ok);
    }

    private void onSubmit() {
        if (submitted) return;

        if (cbAccept == null || !cbAccept.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bitte zuerst die Bedingungen akzeptieren.");
            return;
        }

        submitted = true;
        applyState();

        JOptionPane.showMessageDialog(this,
                "Anmeldung wurde eingereicht (Startzustand → eingereicht).\nGenehmigung folgt später durch Betreuer/Prüfungsamt.",
                "Erfolg", JOptionPane.INFORMATION_MESSAGE);
    }

    // ================== UI Helpers ==================

    private RoundPanel card() {
        RoundPanel card = new RoundPanel(18);
        card.setFill(UIColors.CARD_BG);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(22, 22, 22, 22));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        return card;
    }

    private JComponent cardWrap(JComponent c) {
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
        c.setPreferredSize(new Dimension(980, c.getPreferredSize().height));
        return c;
    }

    private JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 18));
        l.setForeground(UIColors.TEXT_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel sectionSub(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 14));
        l.setForeground(UIColors.TEXT_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private void styleRadio(JRadioButton rb) {
        rb.setBackground(UIColors.CARD_BG);
        rb.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rb.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}