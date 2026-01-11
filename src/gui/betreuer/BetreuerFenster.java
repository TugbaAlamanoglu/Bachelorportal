package gui.betreuer;

import gui.authentication.LoginFenster;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BetreuerFenster extends JFrame {

    // ===== Pages =====
    public static final String PAGE_DASHBOARD = "dashboard";
    public static final String PAGE_ANTRAEGE = "antraege";
    public static final String PAGE_BETREUTE_STUDIERENDE = "betreute_studierende";
    public static final String PAGE_STUDENTENSUCHE = "studentensuche";
    public static final String PAGE_ARBEITSSTAND = "arbeitsstand";
    public static final String PAGE_FINALE_ABGABEN = "finale_abgaben";
    public static final String PAGE_BACHELORSEMINAR = "bachelorseminar";
    public static final String PAGE_NOTENVERGABE = "notenvergabe";

    // ===== Farben =====
    static final Color BG = new Color(246, 248, 252);
    static final Color SIDEBAR_BG = Color.WHITE;
    static final Color BORDER = new Color(233, 236, 242);
    static final Color PRIMARY = new Color(0x00, 0x66, 0xB3);
    static final Color ACCENT_RED = new Color(0xE3, 0x06, 0x13);
    static final Color TEXT_DARK = new Color(34, 34, 34);
    static final Color TEXT_MUTED = new Color(120, 120, 120);

    private final int betreuerId;
    private final String name;
    private final String email;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel content = new JPanel(cardLayout);

    // Sidebar Buttons
    private NavButton btnDashboard;
    private NavButton btnAntraege;
    private NavButton btnBetreuteStudierende;
    private NavButton btnStudentensuche;
    private NavButton btnArbeitsstand;
    private NavButton btnFinaleAbgaben;
    private NavButton btnBachelorseminar;
    private NavButton btnNotenvergabe;

    // ===== KONSTRUKTOR (WICHTIG) =====
    public BetreuerFenster(int betreuerId, String name, String email) {
        super("Bachelorarbeits-Portal (Betreuer)");

        this.betreuerId = betreuerId;
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
        content.add(new DashboardBetreuer(this, name, email), PAGE_DASHBOARD);
        content.add(new AntraegePage(this, name, email), PAGE_ANTRAEGE);
        content.add(new BetreuteStudierendePage(this, name, email), PAGE_BETREUTE_STUDIERENDE);
        content.add(new StudentensuchePage(this, name, email), PAGE_STUDENTENSUCHE);
        content.add(new ArbeitsstandBetreuer(this, name, email), PAGE_ARBEITSSTAND);
        content.add(new FinaleAbgabenPage(this, name, email), PAGE_FINALE_ABGABEN);
        content.add(new BachelorseminarBetreuer(this, name, email), PAGE_BACHELORSEMINAR);
        content.add(new NotenvergabePage(this, name, email), PAGE_NOTENVERGABE);

        right.add(content, BorderLayout.CENTER);
        root.add(right, BorderLayout.CENTER);
        setContentPane(root);

        showPage(PAGE_DASHBOARD);
    }

    // ===== Navigation =====
    public void showPage(String page) {
        cardLayout.show(content, page);

        btnDashboard.setSelected(PAGE_DASHBOARD.equals(page));
        btnAntraege.setSelected(PAGE_ANTRAEGE.equals(page));
        btnBetreuteStudierende.setSelected(PAGE_BETREUTE_STUDIERENDE.equals(page));
        btnStudentensuche.setSelected(PAGE_STUDENTENSUCHE.equals(page));
        btnArbeitsstand.setSelected(PAGE_ARBEITSSTAND.equals(page));
        btnFinaleAbgaben.setSelected(PAGE_FINALE_ABGABEN.equals(page));
        btnBachelorseminar.setSelected(PAGE_BACHELORSEMINAR.equals(page));
        btnNotenvergabe.setSelected(PAGE_NOTENVERGABE.equals(page));
    }

    public int getBetreuerId() {
        return betreuerId;
    }

    // ===== Sidebar =====
    private JComponent buildSidebar() {
        JPanel side = new JPanel(new BorderLayout());
        side.setBackground(SIDEBAR_BG);
        side.setPreferredSize(new Dimension(320, 0));
        side.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setBorder(new EmptyBorder(16, 16, 16, 16));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        btnDashboard = new NavButton("Dashboard", true);
        btnAntraege = new NavButton("Anträge", false);
        btnBetreuteStudierende = new NavButton("Betreute Studierende", false);
        btnStudentensuche = new NavButton("Studentensuche", false);
        btnArbeitsstand = new NavButton("Arbeitsstand & Uploads", false);
        btnFinaleAbgaben = new NavButton("Finale Abgaben", false);
        btnBachelorseminar = new NavButton("Bachelorseminar", false);
        btnNotenvergabe = new NavButton("Notenvergabe", false);

        btnDashboard.onClick(() -> showPage(PAGE_DASHBOARD));
        btnAntraege.onClick(() -> showPage(PAGE_ANTRAEGE));
        btnBetreuteStudierende.onClick(() -> showPage(PAGE_BETREUTE_STUDIERENDE));
        btnStudentensuche.onClick(() -> showPage(PAGE_STUDENTENSUCHE));
        btnArbeitsstand.onClick(() -> showPage(PAGE_ARBEITSSTAND));
        btnFinaleAbgaben.onClick(() -> showPage(PAGE_FINALE_ABGABEN));
        btnBachelorseminar.onClick(() -> showPage(PAGE_BACHELORSEMINAR));
        btnNotenvergabe.onClick(() -> showPage(PAGE_NOTENVERGABE));

        menu.add(btnDashboard);
        menu.add(Box.createVerticalStrut(6));
        menu.add(btnAntraege);
        menu.add(Box.createVerticalStrut(6));
        menu.add(btnBetreuteStudierende);
        menu.add(Box.createVerticalStrut(6));
        menu.add(btnStudentensuche);
        menu.add(Box.createVerticalStrut(6));
        menu.add(btnArbeitsstand);
        menu.add(Box.createVerticalStrut(6));
        menu.add(btnFinaleAbgaben);
        menu.add(Box.createVerticalStrut(6));
        menu.add(btnBachelorseminar);
        menu.add(Box.createVerticalStrut(6));
        menu.add(btnNotenvergabe);

        side.add(menu, BorderLayout.CENTER);

        JButton logout = new JButton("Abmelden");
        logout.addActionListener(e -> {
            new LoginFenster().setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(new EmptyBorder(12, 16, 12, 16));
        bottom.add(logout, BorderLayout.CENTER);

        side.add(bottom, BorderLayout.SOUTH);
        return side;
    }

    // ===== Topbar =====
    private JComponent buildTopbar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createLineBorder(BORDER));

        JLabel t = new JLabel("Betreuer: " + name);
        t.setBorder(new EmptyBorder(16, 24, 16, 24));
        t.setFont(new Font("SansSerif", Font.BOLD, 18));
        top.add(t, BorderLayout.WEST);

        return top;
    }

    // ===== Sidebar Button =====
    private class NavButton extends JPanel {
        private final JPanel pill = new JPanel(new BorderLayout());
        private final JLabel label;
        private Runnable onClick;
        private boolean selected;

        NavButton(String text, boolean selected) {
            this.selected = selected;
            setLayout(new BorderLayout());
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            pill.setBorder(new EmptyBorder(12, 14, 12, 14));
            label = new JLabel(text);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            pill.add(label, BorderLayout.CENTER);

            add(pill);
            refresh();

            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    if (onClick != null) onClick.run();
                }
            });
        }

        void onClick(Runnable r) {
            this.onClick = r;
        }

        void setSelected(boolean selected) {
            this.selected = selected;
            refresh();
        }

        private void refresh() {
            pill.setBackground(selected ? PRIMARY : SIDEBAR_BG);
            label.setForeground(selected ? Color.WHITE : TEXT_DARK);
        }
    }
}
