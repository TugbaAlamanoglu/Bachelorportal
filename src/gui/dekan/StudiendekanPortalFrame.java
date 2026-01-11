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

    private final String dekanName;
    private final String dekanEmail;

    public static final String VIEW_DASHBOARD = "dashboard";
    public static final String VIEW_GENEHMIGUNGEN = "genehmigungen";
    public static final String VIEW_STUDENTENSUCHE = "studentensuche";
    public static final String VIEW_BACHELORSEMINAR = "bachelorseminar";
    public static final String VIEW_NOTEN = "noten";
    public static final String VIEW_UEBERSICHT = "uebersicht";

    public StudiendekanPortalFrame(String name, String email) {
        super("Bachelorarbeits-Portal – Studiendekan");

        this.dekanName = name;
        this.dekanEmail = email;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIColors.BG_APP);

        root.add(buildSidebar(), BorderLayout.WEST);
        root.add(buildRight(), BorderLayout.CENTER);

        setContentPane(root);
        select(VIEW_DASHBOARD);
    }

    public StudiendekanPortalFrame() {
        this("Studiendekan", "");
    }

    private JPanel buildRight() {
        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(UIColors.BG_APP);

        right.add(buildTopbar(), BorderLayout.NORTH);

        content.setBackground(UIColors.BG_APP);
        right.add(content, BorderLayout.CENTER);

        content.add(new GenehmigungAntrag(), VIEW_GENEHMIGUNGEN);
        content.add(new DashboardStudiendekan(), VIEW_DASHBOARD);
        content.add(new StudentensucheStudiendekan(), VIEW_STUDENTENSUCHE);
        content.add(new BachelorseminarView(), VIEW_BACHELORSEMINAR);
        content.add(new NotenvergabeStudiendekan(), VIEW_NOTEN);
        content.add(new UebersichtAbschlussarbeiten(), VIEW_UEBERSICHT);

        return right;
    }

    private JPanel buildTopbar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Color.WHITE);
        bar.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel title = new JLabel("Bachelorarbeits-Portal – Studiendekan");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(UIColors.TEXT_DARK);

        bar.add(title, BorderLayout.WEST);
        return bar;
    }

    private JPanel buildSidebar() {
        JPanel side = new JPanel(new BorderLayout());
        side.setPreferredSize(new Dimension(280, 0));
        side.setBackground(Color.WHITE);

        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setBorder(new EmptyBorder(24, 12, 24, 12));
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));

        nav.add(makeNavItem("Dashboard", VIEW_DASHBOARD));
        nav.add(Box.createVerticalStrut(6));
        nav.add(makeNavItem("Genehmigungen", VIEW_GENEHMIGUNGEN));
        nav.add(Box.createVerticalStrut(6));
        nav.add(makeNavItem("Studentensuche", VIEW_STUDENTENSUCHE));
        nav.add(Box.createVerticalStrut(6));
        nav.add(makeNavItem("Bachelorseminar", VIEW_BACHELORSEMINAR));
        nav.add(Box.createVerticalStrut(6));
        nav.add(makeNavItem("Notenvergabe", VIEW_NOTEN));
        nav.add(Box.createVerticalStrut(6));
        nav.add(makeNavItem("Übersicht Abschlussarbeiten", VIEW_UEBERSICHT));

        side.add(nav, BorderLayout.CENTER);
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
}
