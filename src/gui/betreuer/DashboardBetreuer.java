package gui.betreuer;

import gui.authentication.LoginFenster;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DashboardBetreuer extends JFrame {
	

    // --- Farben (aus Screenshot abgeleitet/ähnlich) ---
    public static final Color BG = new Color(0xF5F6F8);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color BORDER = new Color(0xE3E5EA);
    public static final Color TEXT_MUTED = new Color(0x6B7280);
    public static final Color TEXT = new Color(0x111827);
    public static final Color BLUE = new Color(0x05, 0x65, 0xB0); // #0565B0
    public static final Color BLUE_DARK = new Color(0x04, 0x57, 0x98);
    public static final Color CHIP_BG = new Color(0xEEF2F7);
    public static final Color HOVER_RED = new Color(0xE02424);

    private final CardLayout contentCards = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentCards);

    private final PillTabButton tabGenehmigungen;
    private final PillTabButton tabSuche;
    private final PillTabButton tabNoten;

    private final String betreuerName;
    private final String betreuerEmail;

    // Dummy Daten wie in Screens
    private final List<Application> pendingApps = List.of(
            new Application("Anna Müller", "123456", "anna.mueller@hft-stuttgart.de",
                    "Entwicklung eines KI-gestützten Chatbots für Kundenservice",
                    "TechCorp GmbH, Stuttgart", "1.10.2024 - 31.1.2025",
                    "Dr. Meyer", "NDA vorhanden", "Erlaubt", "Ausstehend"),
            new Application("Max Weber", "234567", "max.weber@hft-stuttgart.de",
                    "Blockchain-basierte Lösung für Supply Chain Management",
                    "LogiChain AG, Esslingen", "1.11.2024 - 28.2.2025",
                    "Prof. Dr. Schmidt", "Kein NDA", "Erlaubt", "Ausstehend")
    );

    // ✅ NEU: echter Login-User
    
    public DashboardBetreuer(String betreuerName, String betreuerEmail) {
        super("Bachelorarbeits-Portal");
        this.betreuerName = (betreuerName == null || betreuerName.isBlank()) ? "Betreuer" : betreuerName;
        this.betreuerEmail = (betreuerEmail == null) ? "" : betreuerEmail;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1280, 780));
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        JPanel header = buildHeader();
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(22, 28, 28, 28));

        // Statistik-Karten (oben)
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 22, 0));
        statsRow.setOpaque(false);
        statsRow.add(buildStatCard("Ausstehende Genehmigungen", "2", "Anmeldungen zur Prüfung", "⏱"));
        statsRow.add(buildStatCard("Aktive Betreuungen", "3", "Laufende Bachelorarbeiten", "👥"));
        statsRow.add(buildStatCard("Abgeschlossen", "5", "Bewertete Arbeiten", "🏅"));

        // Tabs (Pills)
        JPanel tabsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 18));
        tabsRow.setOpaque(false);

        tabGenehmigungen = new PillTabButton("Genehmigungen (2)", "✓");
        tabSuche = new PillTabButton("Studenten-Suche", "🔍");
        tabNoten = new PillTabButton("Notenliste", "🏷");

        tabsRow.add(tabGenehmigungen);
        tabsRow.add(tabSuche);
        tabsRow.add(tabNoten);

        // Content Cards
        contentPanel.setOpaque(false);
        contentPanel.add(buildGenehmigungenView(), "GENEHMIGUNGEN");
        contentPanel.add(new StudentenSucheView(this), "SUCHE");
        contentPanel.add(new Noteneingabe(this), "NOTEN");

        // initial selection
        setActiveTab(tabGenehmigungen);
        contentCards.show(contentPanel, "GENEHMIGUNGEN");

        // Tab actions
        tabGenehmigungen.addActionListener(e -> {
            setActiveTab(tabGenehmigungen);
            contentCards.show(contentPanel, "GENEHMIGUNGEN");
        });
        tabSuche.addActionListener(e -> {
            setActiveTab(tabSuche);
            contentCards.show(contentPanel, "SUCHE");
        });
        tabNoten.addActionListener(e -> {
            setActiveTab(tabNoten);
            contentCards.show(contentPanel, "NOTEN");
        });

        JPanel topStack = new JPanel();
        topStack.setOpaque(false);
        topStack.setLayout(new BoxLayout(topStack, BoxLayout.Y_AXIS));
        topStack.add(statsRow);
        topStack.add(tabsRow);

        center.add(topStack, BorderLayout.NORTH);
        center.add(contentPanel, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    // optional für schnellen Test
    public DashboardBetreuer() {
        this("Betreuer", "betreuer@demo.de");
    }

    private void setActiveTab(PillTabButton active) {
        tabGenehmigungen.setActive(active == tabGenehmigungen);
        tabSuche.setActive(active == tabSuche);
        tabNoten.setActive(active == tabNoten);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        // Left: Logo + Titel + Betreuer
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));

        LogoCircle logo = new LogoCircle("HFT");
        left.add(logo);
        left.add(Box.createHorizontalStrut(14));

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Bachelorarbeits-Portal");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        // ✅ dynamisch statt "talamanoglu"
        JLabel sub = new JLabel("Betreuer: " + betreuerName);
        sub.setForeground(TEXT_MUTED);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));

        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(4));
        titleBox.add(sub);

        left.add(titleBox);

        // Right: Notification + Abmelden
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        right.setOpaque(false);

        NotificationBell bell = new NotificationBell("2");
        right.add(bell);

        HoverLogoutButton logout = new HoverLogoutButton("Abmelden");
        logout.addActionListener(e -> {
            new LoginFenster().setVisible(true);
            dispose();
        });
        right.add(logout);

        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xE9EBEF)),
                new EmptyBorder(16, 22, 16, 22)
        ));
        return header;
    }

    private RoundedPanel buildStatCard(String title, String bigNumber, String caption, String icon) {
        RoundedPanel card = new RoundedPanel(18);
        card.setBackground(CARD_BG);
        card.setBorder(new EmptyBorder(18, 20, 18, 20));
        card.setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel t = new JLabel(title);
        t.setForeground(TEXT);
        t.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel ic = new JLabel(icon);
        ic.setFont(new Font("SansSerif", Font.PLAIN, 18));
        ic.setForeground(new Color(0x9CA3AF));

        top.add(t, BorderLayout.WEST);
        top.add(ic, BorderLayout.EAST);

        JPanel mid = new JPanel();
        mid.setOpaque(false);
        mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
        mid.setBorder(new EmptyBorder(22, 0, 0, 0));

        JLabel num = new JLabel(bigNumber);
        num.setForeground(TEXT);
        num.setFont(new Font("SansSerif", Font.PLAIN, 42));

        JLabel cap = new JLabel(caption);
        cap.setForeground(TEXT_MUTED);
        cap.setFont(new Font("SansSerif", Font.PLAIN, 14));

        mid.add(num);
        mid.add(Box.createVerticalStrut(6));
        mid.add(cap);

        card.add(top, BorderLayout.NORTH);
        card.add(mid, BorderLayout.CENTER);

        return card;
    }

    private JComponent buildGenehmigungenView() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        for (Application app : pendingApps) {
            list.add(buildApprovalCard(app));
            list.add(Box.createVerticalStrut(16));
        }

        JScrollPane sp = new JScrollPane(list);
        sp.setBorder(null);
        sp.getViewport().setOpaque(false);
        sp.setOpaque(false);
        sp.getVerticalScrollBar().setUnitIncrement(16);

        wrapper.add(sp, BorderLayout.CENTER);
        return wrapper;
    }

    private RoundedPanel buildApprovalCard(Application app) {
        RoundedPanel card = new RoundedPanel(18);
        card.setBackground(CARD_BG);
        card.setBorder(new EmptyBorder(18, 20, 18, 20));
        card.setLayout(new BorderLayout());

        JPanel head = new JPanel(new BorderLayout());
        head.setOpaque(false);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(app.studentName);
        name.setForeground(TEXT);
        name.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel meta = new JLabel("Mat.-Nr.: " + app.matrikel + " | " + app.email);
        meta.setForeground(TEXT_MUTED);
        meta.setFont(new Font("SansSerif", Font.PLAIN, 15));

        left.add(name);
        left.add(Box.createVerticalStrut(4));
        left.add(meta);

        JLabel status = chip(app.status, CHIP_BG, TEXT);
        status.setBorder(new EmptyBorder(6, 12, 6, 12));

        head.add(left, BorderLayout.WEST);
        head.add(status, BorderLayout.EAST);

        JPanel themaBox = new JPanel();
        themaBox.setOpaque(false);
        themaBox.setLayout(new BoxLayout(themaBox, BoxLayout.Y_AXIS));
        themaBox.setBorder(new EmptyBorder(16, 0, 0, 0));

        JLabel themaLabel = new JLabel("Thema der Bachelorarbeit");
        themaLabel.setForeground(TEXT);
        themaLabel.setFont(new Font("SansSerif", Font.BOLD, 15));

        JTextField themaField = new JTextField(app.thema);
        themaField.setEditable(false);
        themaField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(12, 12, 12, 12)
        ));
        themaField.setBackground(new Color(0xFBFBFC));
        themaField.setFont(new Font("SansSerif", Font.PLAIN, 15));

        themaBox.add(themaLabel);
        themaBox.add(Box.createVerticalStrut(10));
        themaBox.add(themaField);

        JPanel infoGrid = new JPanel(new GridLayout(2, 2, 40, 14));
        infoGrid.setOpaque(false);
        infoGrid.setBorder(new EmptyBorder(18, 0, 0, 0));

        infoGrid.add(infoItem("🏢", "Unternehmen", app.unternehmen));
        infoGrid.add(infoItem("", "Betreuer im Unternehmen", app.unternehmensBetreuer));
        infoGrid.add(infoItem("📅", "Zeitraum", app.zeitraum));
        infoGrid.add(buildNdaAndPublish(app));

        JButton btn = new JButton("Genehmigung prüfen");
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBackground(BLUE);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBorder(new EmptyBorder(14, 18, 14, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(BLUE_DARK); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(BLUE); }
        });
        btn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Dialog (Demo)."));

        JPanel btnWrap = new JPanel(new BorderLayout());
        btnWrap.setOpaque(false);
        btnWrap.setBorder(new EmptyBorder(18, 0, 0, 0));
        btnWrap.add(btn, BorderLayout.CENTER);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(head);
        body.add(themaBox);
        body.add(infoGrid);
        body.add(btnWrap);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel infoItem(String icon, String label, String value) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        if (icon != null && !icon.isBlank()) {
            JLabel ic = new JLabel(icon);
            ic.setFont(new Font("SansSerif", Font.PLAIN, 16));
            ic.setForeground(TEXT_MUTED);
            p.add(ic);
            p.add(Box.createHorizontalStrut(10));
        } else {
            p.add(Box.createHorizontalStrut(26));
        }

        JPanel v = new JPanel();
        v.setOpaque(false);
        v.setLayout(new BoxLayout(v, BoxLayout.Y_AXIS));

        JLabel l = new JLabel(label);
        l.setForeground(TEXT_MUTED);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel val = new JLabel(value);
        val.setForeground(TEXT);
        val.setFont(new Font("SansSerif", Font.BOLD, 15));

        v.add(l);
        v.add(Box.createVerticalStrut(3));
        v.add(val);

        p.add(v);
        return p;
    }

    private JPanel buildNdaAndPublish(Application app) {
        JPanel wrap = new JPanel();
        wrap.setOpaque(false);
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));

        JLabel ndaLabel = new JLabel("NDA-Status");
        ndaLabel.setForeground(TEXT_MUTED);
        ndaLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setOpaque(false);

        JLabel ndaChip;
        if ("NDA vorhanden".equalsIgnoreCase(app.ndaStatus)) {
            ndaChip = chip("NDA vorhanden", BLUE, Color.WHITE);
        } else {
            ndaChip = chip("Kein NDA", CHIP_BG, TEXT);
        }
        ndaChip.setBorder(new EmptyBorder(6, 12, 6, 12));

        JLabel download = new JLabel("⬇  Download");
        download.setForeground(TEXT);
        download.setFont(new Font("SansSerif", Font.BOLD, 14));
        download.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        download.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(DashboardBetreuer.this, "Download (Demo).");
            }
        });

        row.add(ndaChip);
        row.add(download);

        JLabel pubLabel = new JLabel("Veröffentlichung");
        pubLabel.setForeground(TEXT_MUTED);
        pubLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel pubVal = new JLabel(app.veroeffentlichung);
        pubVal.setForeground(TEXT);
        pubVal.setFont(new Font("SansSerif", Font.BOLD, 15));

        wrap.add(ndaLabel);
        wrap.add(Box.createVerticalStrut(6));
        wrap.add(row);
        wrap.add(Box.createVerticalStrut(12));
        wrap.add(pubLabel);
        wrap.add(Box.createVerticalStrut(3));
        wrap.add(pubVal);

        return wrap;
    }

    private JLabel chip(String text, Color bg, Color fg) {
        JLabel l = new JLabel(text);
        l.setOpaque(true);
        l.setBackground(bg);
        l.setForeground(fg);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setBorder(new EmptyBorder(6, 12, 6, 12));
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }

    // ---------- UI Helper Components ----------
    static class RoundedPanel extends JPanel {
        private final int radius;

        RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            g2.setColor(new Color(0x000000, true));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.03f));
            g2.fillRoundRect(2, 3, w - 4, h - 5, radius, radius);

            g2.setComposite(AlphaComposite.SrcOver);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, w, h, radius, radius);

            g2.setColor(BORDER);
            g2.drawRoundRect(0, 0, w - 1, h - 1, radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class LogoCircle extends JComponent {
        private final String text;
        LogoCircle(String text) {
            this.text = text;
            setPreferredSize(new Dimension(44, 44));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0x0A6FB5));
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(text, x, y);
            g2.dispose();
        }
    }

    static class NotificationBell extends JPanel {
        NotificationBell(String badgeText) {
            setOpaque(false);
            setLayout(null);
            setPreferredSize(new Dimension(54, 34));

            RoundedPanel box = new RoundedPanel(10);
            box.setBackground(new Color(0xF8FAFC));
            box.setBounds(0, 0, 44, 34);
            box.setLayout(new BorderLayout());
            JLabel bell = new JLabel("🔔", SwingConstants.CENTER);
            bell.setFont(new Font("SansSerif", Font.PLAIN, 16));
            box.add(bell, BorderLayout.CENTER);

            add(box);

            JComponent badge = new JComponent() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(HOVER_RED);
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                    FontMetrics fm = g2.getFontMetrics();
                    int x = (getWidth() - fm.stringWidth(badgeText)) / 2;
                    int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g2.drawString(badgeText, x, y);
                    g2.dispose();
                }
            };
            badge.setBounds(30, -4, 18, 18);
            add(badge);
        }
    }

    static class HoverLogoutButton extends JButton {
        private final Color normalBg = new Color(0xF8FAFC);
        private final Color normalFg = new Color(0x111827);

        HoverLogoutButton(String text) {
            super("⎋  " + text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setBackground(normalBg);
            setForeground(normalFg);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER),
                    new EmptyBorder(8, 14, 8, 14)
            ));
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {
                    setBackground(HOVER_RED);
                    setForeground(Color.WHITE);
                }
                @Override public void mouseExited(MouseEvent e) {
                    setBackground(normalBg);
                    setForeground(normalFg);
                }
            });
        }
    }

    static class PillTabButton extends JButton {
        PillTabButton(String text, String icon) {
            super(icon + "  " + text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setBorderPainted(false);
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(10, 16, 10, 16));
            setBackground(new Color(0xECEFF3));
            setForeground(TEXT);
        }

        void setActive(boolean active) {
            if (active) {
                setBackground(Color.WHITE);
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(10, 16, 10, 16)
                ));
            } else {
                setBorder(new EmptyBorder(10, 16, 10, 16));
                setBackground(new Color(0xECEFF3));
            }
            repaint();
        }
    }

    // ---------- Dummy Datenmodell ----------
    public static class Application {
        public final String studentName;
        public final String matrikel;
        public final String email;
        public final String thema;
        public final String unternehmen;
        public final String zeitraum;
        public final String unternehmensBetreuer;
        public final String ndaStatus;
        public final String veroeffentlichung;
        public final String status;

        public Application(String studentName, String matrikel, String email, String thema,
                           String unternehmen, String zeitraum, String unternehmensBetreuer,
                           String ndaStatus, String veroeffentlichung, String status) {
            this.studentName = studentName;
            this.matrikel = matrikel;
            this.email = email;
            this.thema = thema;
            this.unternehmen = unternehmen;
            this.zeitraum = zeitraum;
            this.unternehmensBetreuer = unternehmensBetreuer;
            this.ndaStatus = ndaStatus;
            this.veroeffentlichung = veroeffentlichung;
            this.status = status;
        }
    }
}
