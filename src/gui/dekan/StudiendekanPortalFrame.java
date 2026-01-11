package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class StudiendekanPortalFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel content = new JPanel(cardLayout);

    private final Map<String, NavButton> navButtons = new LinkedHashMap<>();

    // ✅ dynamisch aus Login
    private final String dekanName;
    private final String dekanEmail;

    public static final String VIEW_DASHBOARD = "dashboard";
    public static final String VIEW_GENEHMIGUNGEN = "genehmigungen";
    public static final String VIEW_STUDENTENSUCHE = "studentensuche";
    public static final String VIEW_NOTEN = "noten";
    public static final String VIEW_UEBERSICHT = "uebersicht";

    // ✅ Konstruktor mit Name + Email (aus Login)
    public StudiendekanPortalFrame(String name, String email) {
        super("Bachelorarbeits-Portal – Studiendekan");

        this.dekanName  = (name == null || name.isBlank()) ? "Studiendekan" : name.trim();
        this.dekanEmail = (email == null) ? "" : email.trim();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIColors.BG_APP);

        // Sidebar
        JPanel sidebar = buildSidebar();
        sidebar.setPreferredSize(new Dimension(280, 0));
        root.add(sidebar, BorderLayout.WEST);

        // Content + Topbar Container
        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(true);
        right.setBackground(UIColors.BG_APP);

        JPanel topbar = buildTopbar();
        right.add(topbar, BorderLayout.NORTH);

        content.setOpaque(true);
        content.setBackground(UIColors.BG_APP);
        content.setBorder(new EmptyBorder(24, 24, 24, 24));
        right.add(content, BorderLayout.CENTER);

        // Views
        content.add(new DashboardStudiendekan(), VIEW_DASHBOARD);
        content.add(new GenehmigungBachelorarbeit(), VIEW_GENEHMIGUNGEN);
        content.add(new StudentensucheStudiendekan(), VIEW_STUDENTENSUCHE);
        content.add(new NotenvergabeStudiendekan(), VIEW_NOTEN);
        content.add(new UebersichtAbschlussarbeiten(), VIEW_UEBERSICHT);

        root.add(right, BorderLayout.CENTER);

        setContentPane(root);

        // Default view
        select(VIEW_DASHBOARD);
    }

    // ✅ OPTIONAL: damit alte Aufrufe ohne Parameter nicht kaputt gehen
    public StudiendekanPortalFrame() {
        this("Studiendekan", "");
    }

    private JPanel buildTopbar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(true);
        bar.setBackground(Color.WHITE);
        bar.setBorder(new EmptyBorder(16, 24, 16, 24));

        // Left title group
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Bachelorarbeits-Portal");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(UIColors.TEXT_DARK);

        JLabel role = new JLabel("Studiendekan");
        role.setFont(new Font("SansSerif", Font.PLAIN, 13));
        role.setForeground(UIColors.TEXT_MUTED);

        left.add(title);
        left.add(Box.createVerticalStrut(2));
        left.add(role);

        // Right icons group (Bell + Badge)
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        right.setOpaque(false);

        BellWithBadge bell = new BellWithBadge(1);
        right.add(bell);

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);

        return bar;
    }

    private JPanel buildSidebar() {
        JPanel side = new JPanel();
        side.setOpaque(true);
        side.setBackground(Color.WHITE);
        side.setLayout(new BorderLayout());

        // Top (logo + app name)
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(20, 18, 14, 18));
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));

        CircleLogo logo = new CircleLogo(42, "HFT");
        top.add(logo);
        top.add(Box.createHorizontalStrut(12));

        JPanel nameWrap = new JPanel();
        nameWrap.setOpaque(false);
        nameWrap.setLayout(new BoxLayout(nameWrap, BoxLayout.Y_AXIS));

        JLabel app = new JLabel("BA-Portal");
        app.setFont(new Font("SansSerif", Font.BOLD, 16));
        app.setForeground(UIColors.TEXT_DARK);

        JLabel uni = new JLabel("HFT Stuttgart");
        uni.setFont(new Font("SansSerif", Font.PLAIN, 12));
        uni.setForeground(UIColors.TEXT_MUTED);

        nameWrap.add(app);
        nameWrap.add(Box.createVerticalStrut(2));
        nameWrap.add(uni);

        top.add(nameWrap);

        side.add(top, BorderLayout.NORTH);

        // Center navigation
        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setBorder(new EmptyBorder(8, 12, 8, 12));
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));

        nav.add(makeNavItem("Dashboard", VIEW_DASHBOARD));
        nav.add(Box.createVerticalStrut(6));
        nav.add(makeNavItem("Genehmigungen", VIEW_GENEHMIGUNGEN));
        nav.add(Box.createVerticalStrut(6));
        nav.add(makeNavItem("Studentensuche", VIEW_STUDENTENSUCHE));
        nav.add(Box.createVerticalStrut(6));
        nav.add(makeNavItem("Notenvergabe", VIEW_NOTEN));
        nav.add(Box.createVerticalStrut(6));
        nav.add(makeNavItem("Übersicht Abschlussarbeiten", VIEW_UEBERSICHT));

        side.add(nav, BorderLayout.CENTER);

        // Bottom user card + logout
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(12, 12, 18, 12));
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        ShadowCardPanel userCard = new ShadowCardPanel(16);
        userCard.setOpaque(false);
        userCard.setBackground(Color.WHITE);
        userCard.setBorder(new EmptyBorder(14, 14, 14, 14));
        userCard.setLayout(new BoxLayout(userCard, BoxLayout.Y_AXIS));

        // ✅ dynamisch statt fest
        JLabel userName = new JLabel(dekanName);
        userName.setFont(new Font("SansSerif", Font.BOLD, 13));
        userName.setForeground(UIColors.TEXT_DARK);

        JLabel email = new JLabel(dekanEmail);
        email.setFont(new Font("SansSerif", Font.PLAIN, 12));
        email.setForeground(UIColors.TEXT_MUTED);

        JLabel role = new JLabel("Studiendekan");
        role.setFont(new Font("SansSerif", Font.BOLD, 12));
        role.setForeground(UIColors.LINK);

        userCard.add(userName);
        userCard.add(Box.createVerticalStrut(4));
        userCard.add(email);
        userCard.add(Box.createVerticalStrut(6));
        userCard.add(role);

        JButton logout = new JButton("Abmelden");
        logout.setFont(new Font("SansSerif", Font.BOLD, 14));
        logout.setFocusPainted(false);
        logout.setBorderPainted(false);
        logout.setBackground(new Color(245, 246, 248));
        logout.setForeground(UIColors.TEXT_DARK);
        logout.setPreferredSize(new Dimension(200, 44));
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        logout.addActionListener(e -> {
            new gui.authentication.LoginFenster().setVisible(true);
            dispose();
        });

        bottom.add(userCard);
        bottom.add(Box.createVerticalStrut(12));
        bottom.add(logout);

        side.add(bottom, BorderLayout.SOUTH);

        return side;
    }

    private JComponent makeNavItem(String text, String key) {
        NavButton btn = new NavButton(text);
        btn.addActionListener(e -> select(key));
        navButtons.put(key, btn);
        return btn;
    }

    private void select(String key) {
        cardLayout.show(content, key);
        navButtons.forEach((k, b) -> b.setActive(k.equals(key)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            // ✅ Test
            new StudiendekanPortalFrame("Prof. Andreas Wagner", "andreas.wagner@hft-stuttgart.de").setVisible(true);
        });
    }
}

