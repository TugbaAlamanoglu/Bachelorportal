package gui.dekan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudiendekanFenster extends JFrame {

    // Pages
    public static final String PAGE_DASHBOARD = "dashboard";
    public static final String PAGE_GENEHMIGUNGEN = "genehmigungen";
    public static final String PAGE_STUDENTENSUCHE = "studentensuche";
    public static final String PAGE_BACHELORSEMINAR = "bachelorseminar";
    public static final String PAGE_NOTENVERGABE = "notenvergabe";
    public static final String PAGE_UEBERSICHT = "uebersicht";

    // ---- Farben (wie Student und Betreuer) ----
    static final Color BG = new Color(246, 248, 252);
    static final Color SIDEBAR_BG = Color.WHITE;
    static final Color BORDER = new Color(233, 236, 242);
    static final Color PRIMARY = new Color(0x00, 0x66, 0xB3);    // #0066B3
    static final Color ACCENT_RED = new Color(0xE3, 0x06, 0x13); // #E30613
    static final Color TEXT_DARK = new Color(34, 34, 34);
    static final Color TEXT_MUTED = new Color(120, 120, 120);

    private final String name;
    private final String email;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel content = new JPanel(cardLayout);

    // Sidebar Buttons
    private NavButton btnDashboard;
    private NavButton btnGenehmigungen;
    private NavButton btnStudentensuche;
    private NavButton btnBachelorseminar;
    private NavButton btnNotenvergabe;
    private NavButton btnUebersicht;

    public StudiendekanFenster(String name, String email) {
        super("Bachelorarbeits-Portal (Studiendekan)");
        this.name = name;
        this.email = email;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 900);
        setMinimumSize(new Dimension(1200, 800));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        root.add(buildSidebar(), BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(BG);
        right.add(buildTopbar(), BorderLayout.NORTH);

        // Pages
        content.setBackground(BG);
        content.add(new DashboardStudiendekan(this, name, email), PAGE_DASHBOARD);
        content.add(new GenehmigungenPage(this, name, email), PAGE_GENEHMIGUNGEN);
        content.add(new StudentensuchePage(this, name, email), PAGE_STUDENTENSUCHE);
        content.add(new BachelorseminarPage(this, name, email), PAGE_BACHELORSEMINAR);
        content.add(new NotenvergabePage(this, name, email), PAGE_NOTENVERGABE);
        content.add(new UebersichtPage(this, name, email), PAGE_UEBERSICHT);

        right.add(content, BorderLayout.CENTER);
        root.add(right, BorderLayout.CENTER);
        setContentPane(root);

        showPage(PAGE_DASHBOARD);
    }

    // ---------- Navigation ----------
    public void showPage(String page) {
        cardLayout.show(content, page);

        btnDashboard.setSelected(PAGE_DASHBOARD.equals(page));
        btnGenehmigungen.setSelected(PAGE_GENEHMIGUNGEN.equals(page));
        btnStudentensuche.setSelected(PAGE_STUDENTENSUCHE.equals(page));
        btnBachelorseminar.setSelected(PAGE_BACHELORSEMINAR.equals(page));
        btnNotenvergabe.setSelected(PAGE_NOTENVERGABE.equals(page));
        btnUebersicht.setSelected(PAGE_UEBERSICHT.equals(page));
    }

    // ---------- Sidebar ----------
    private JComponent buildSidebar() {
        JPanel side = new JPanel(new BorderLayout());
        side.setBackground(SIDEBAR_BG);
        side.setPreferredSize(new Dimension(320, 0));
        side.setBorder(BorderFactory.createLineBorder(BORDER, 1));

        // Brand oben
        JPanel brand = new JPanel();
        brand.setBackground(SIDEBAR_BG);
        brand.setBorder(new EmptyBorder(22, 22, 18, 22));
        brand.setLayout(new BoxLayout(brand, BoxLayout.X_AXIS));

        brand.add(makeHftLogo(44));
        brand.add(Box.createHorizontalStrut(12));

        JPanel brandText = new JPanel();
        brandText.setOpaque(false);
        brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("HFT Stuttgart");
        l1.setFont(new Font("SansSerif", Font.BOLD, 16));
        l1.setForeground(TEXT_DARK);

        JLabel l2 = new JLabel("Studiendekan-Portal");
        l2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        l2.setForeground(TEXT_MUTED);

        brandText.add(l1);
        brandText.add(Box.createVerticalStrut(3));
        brandText.add(l2);

        brand.add(brandText);
        brand.add(Box.createHorizontalGlue());

        side.add(brand, BorderLayout.NORTH);

        // Menu
        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setBorder(new EmptyBorder(12, 16, 12, 16));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        btnDashboard = new NavButton("Dashboard", true);
        btnGenehmigungen = new NavButton("Genehmigungen", false);
        btnStudentensuche = new NavButton("Studentensuche", false);
        btnBachelorseminar = new NavButton("Bachelorseminar", false);
        btnNotenvergabe = new NavButton("Notenvergabe", false);
        btnUebersicht = new NavButton("Übersicht Abschlussarbeiten", false);

        btnDashboard.onClick(() -> showPage(PAGE_DASHBOARD));
        btnGenehmigungen.onClick(() -> showPage(PAGE_GENEHMIGUNGEN));
        btnStudentensuche.onClick(() -> showPage(PAGE_STUDENTENSUCHE));
        btnBachelorseminar.onClick(() -> showPage(PAGE_BACHELORSEMINAR));
        btnNotenvergabe.onClick(() -> showPage(PAGE_NOTENVERGABE));
        btnUebersicht.onClick(() -> showPage(PAGE_UEBERSICHT));

        menu.add(btnDashboard);     menu.add(Box.createVerticalStrut(6));
        menu.add(btnGenehmigungen); menu.add(Box.createVerticalStrut(6));
        menu.add(btnStudentensuche); menu.add(Box.createVerticalStrut(6));
        menu.add(btnBachelorseminar); menu.add(Box.createVerticalStrut(6));
        menu.add(btnNotenvergabe);  menu.add(Box.createVerticalStrut(6));
        menu.add(btnUebersicht);

        menu.add(Box.createVerticalGlue());
        side.add(menu, BorderLayout.CENTER);

        // Bottom Profile + Logout
        JPanel bottom = new JPanel();
        bottom.setBackground(SIDEBAR_BG);
        bottom.setBorder(new EmptyBorder(18, 18, 18, 18));
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JLabel n = new JLabel(name);
        n.setFont(new Font("SansSerif", Font.BOLD, 14));
        n.setForeground(TEXT_DARK);

        JLabel m = new JLabel(email);
        m.setFont(new Font("SansSerif", Font.PLAIN, 12));
        m.setForeground(TEXT_MUTED);

        JLabel role = new JLabel("Rolle: Studiendekan");
        role.setFont(new Font("SansSerif", Font.PLAIN, 12));
        role.setForeground(TEXT_MUTED);

        bottom.add(n);
        bottom.add(Box.createVerticalStrut(4));
        bottom.add(m);
        bottom.add(Box.createVerticalStrut(4));
        bottom.add(role);

        bottom.add(Box.createVerticalStrut(12));

        JButton logout = new JButton("Abmelden");
        logout.setFont(new Font("SansSerif", Font.BOLD, 14));
        logout.setFocusPainted(false);
        logout.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        logout.setBackground(new Color(247, 248, 250));
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        logout.addActionListener(e -> {
            new gui.authentication.LoginFenster().setVisible(true);
            dispose();
        });

        bottom.add(logout);
        side.add(bottom, BorderLayout.SOUTH);

        return side;
    }

    // ---------- Topbar ----------
    private JComponent buildTopbar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createLineBorder(BORDER, 1));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setBorder(new EmptyBorder(18, 28, 18, 28));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel t1 = new JLabel("Bachelorarbeits-Portal (Studiendekan)");
        t1.setFont(new Font("SansSerif", Font.BOLD, 26));
        t1.setForeground(TEXT_DARK);

        JLabel t2 = new JLabel(name);
        t2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t2.setForeground(TEXT_MUTED);

        left.add(t1);
        left.add(Box.createVerticalStrut(4));
        left.add(t2);

        top.add(left, BorderLayout.WEST);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setBorder(new EmptyBorder(18, 20, 18, 24));
        right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
        right.add(Box.createHorizontalGlue());
        right.add(makeBellWithBadge(0));
        top.add(right, BorderLayout.EAST);

        return top;
    }

    // ---------- UI Helpers ----------
    private JComponent makeHftLogo(int size) {
        return new JComponent() {
            @Override public Dimension getPreferredSize() { return new Dimension(size, size); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int d = Math.min(getWidth(), getHeight());
                int x = (getWidth() - d) / 2;
                int y = (getHeight() - d) / 2;

                g2.setColor(PRIMARY);
                g2.fillOval(x, y, d, d);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, d / 3)));
                FontMetrics fm = g2.getFontMetrics();
                String t = "HFT";
                int tx = x + (d - fm.stringWidth(t)) / 2;
                int ty = y + (d - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(t, tx, ty);

                g2.dispose();
            }
        };
    }

    private JComponent makeBellWithBadge(int count) {
        JPanel wrap = new JPanel(null);
        wrap.setOpaque(false);
        wrap.setPreferredSize(new Dimension(48, 48));

        JButton bell = new JButton("🔔");
        bell.setBounds(0, 0, 48, 48);
        bell.setFocusPainted(false);
        bell.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        bell.setBackground(new Color(247, 248, 250));
        bell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bell.addActionListener(e -> JOptionPane.showMessageDialog(this, "Benachrichtigungen (Demo)"));
        wrap.add(bell);

        if (count > 0) {
            JLabel badge = new JLabel(String.valueOf(count), SwingConstants.CENTER);
            badge.setOpaque(true);
            badge.setBackground(ACCENT_RED);
            badge.setForeground(Color.WHITE);
            badge.setFont(new Font("SansSerif", Font.BOLD, 12));
            badge.setBounds(32, 2, 18, 18);
            badge.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
            wrap.add(badge);
        }
        return wrap;
    }

    // ---------- Sidebar Button ----------
    private class NavButton extends JPanel {
        private final JPanel pill = new JPanel(new BorderLayout());
        private final JLabel label;
        private Runnable onClick;
        private boolean selected;

        NavButton(String text, boolean selected) {
            this.selected = selected;

            setOpaque(false);
            setLayout(new BorderLayout());
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            pill.setBorder(new EmptyBorder(12, 14, 12, 14));
            pill.setOpaque(true);

            label = new JLabel("<html>" + text.replace("\n", "<br>") + "</html>");
            label.setFont(new Font("SansSerif", Font.BOLD, 14));

            pill.add(label, BorderLayout.CENTER);
            add(pill, BorderLayout.CENTER);

            refresh();

            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    if (onClick != null) onClick.run();
                }
                @Override public void mouseEntered(MouseEvent e) {
                    if (!NavButton.this.selected) {
                        pill.setBackground(new Color(247, 248, 250));
                        pill.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
                    }
                }
                @Override public void mouseExited(MouseEvent e) {
                    if (!NavButton.this.selected) refresh();
                }
            });
        }

        void onClick(Runnable r) { this.onClick = r; }

        void setSelected(boolean selected) {
            this.selected = selected;
            refresh();
        }

        private void refresh() {
            pill.setBackground(selected ? PRIMARY : SIDEBAR_BG);
            pill.setBorder(BorderFactory.createLineBorder(selected ? PRIMARY : SIDEBAR_BG, 1, true));
            label.setForeground(selected ? Color.WHITE : TEXT_DARK);
        }
    }
}