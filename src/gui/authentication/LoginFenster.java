package gui.authentication;

import datenbank.UserDAO;
import datenbank.UserLoginResult;
import gui.betreuer.DashboardBetreuer;
import gui.dekan.StudiendekanPortalFrame;
import gui.student.StudentFenster;
import gui.shared.RoundPanel;
import gui.shared.RoundPasswordField;
import gui.shared.RoundTextField;
import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFenster extends JFrame {

    private static final int CARD_W = 520;
    private static final int CARD_H = 620;

    private static final int FIELD_W = 420;
    private static final int FIELD_H = 48;
    private static final int BTN_H   = 52;

    private RoundTextField tfEmail;
    private RoundPasswordField pfPasswort;

    private JPanel errorBox;
    private JLabel errorLabel;

    public LoginFenster() {
        super("Bachelorarbeits-Portal");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(UIColors.BG_APP);

        RoundPanel card = new RoundPanel(20);
        card.setFill(UIColors.CARD_BG);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(CARD_W, CARD_H));
        card.setMinimumSize(new Dimension(CARD_W, CARD_H));
        card.setMaximumSize(new Dimension(CARD_W, CARD_H));

        card.add(buildContent(), BorderLayout.CENTER);

        root.add(card);
        setContentPane(root);
    }

    private JPanel buildContent() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(36, 50, 36, 50));

        // Logo
        p.add(center(makeLogo(80)));
        p.add(Box.createVerticalStrut(16));

        JLabel uni = new JLabel("Hochschule für Technik Stuttgart");
        uni.setFont(new Font("SansSerif", Font.BOLD, 16));
        uni.setForeground(UIColors.TEXT_DARK);
        p.add(center(uni));

        p.add(Box.createVerticalStrut(4));

        JLabel sub = new JLabel("Bachelorarbeits–Portal");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(UIColors.TEXT_MUTED);
        p.add(center(sub));

        p.add(Box.createVerticalStrut(28));

        JLabel title = new JLabel("Bachelorarbeits-Portal");
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(UIColors.TEXT_DARK);
        p.add(center(title));

        p.add(Box.createVerticalStrut(30));

        // --- E-Mail ---
        p.add(left(label("E-Mail *")));
        p.add(Box.createVerticalStrut(8));

        tfEmail = new RoundTextField("");
        fixSize(tfEmail, FIELD_W, FIELD_H);
        p.add(left(tfEmail));

        p.add(Box.createVerticalStrut(18));

        // --- Passwort ---
        p.add(left(label("Passwort *")));
        p.add(Box.createVerticalStrut(8));

        pfPasswort = new RoundPasswordField("");
        fixSize(pfPasswort, FIELD_W, FIELD_H);
        p.add(left(pfPasswort));

        p.add(Box.createVerticalStrut(18));

        // --- Fehlerbox ---
        errorBox = new JPanel(new BorderLayout());
        errorBox.setBackground(new Color(0xFDECEC));
        errorBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xF5C2C7)),
                new EmptyBorder(10, 12, 10, 12)
        ));
        errorBox.setVisible(false);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        errorLabel.setForeground(new Color(0xDC2626));
        errorBox.add(errorLabel, BorderLayout.CENTER);

        fixSize(errorBox, FIELD_W, 44);
        p.add(center(errorBox));

        p.add(Box.createVerticalStrut(18));

        // --- Button ---
        JButton btnLogin = new JButton("Einloggen");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(UIColors.PRIMARY);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setOpaque(true);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        fixSize(btnLogin, FIELD_W, BTN_H);
        btnLogin.addActionListener(e -> onLogin());
        p.add(center(btnLogin));

        p.add(Box.createVerticalStrut(18));

        JLabel link = new JLabel("<html><u>Neuer Benutzer? Jetzt registrieren</u></html>");
        link.setFont(new Font("SansSerif", Font.BOLD, 14));
        link.setForeground(UIColors.LINK);
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new NeuerBenutzerFenster().setVisible(true);
                dispose();
            }
        });
        p.add(center(link));

        p.add(Box.createVerticalGlue());
        return p;
    }

    private void onLogin() {
        clearError();

        String email = tfEmail.getRealText();
        String pass = pfPasswort.getRealPassword();

        if (email.isEmpty() || pass.isEmpty()) {
            showError("Bitte füllen Sie alle Felder aus.");
            return;
        }

        try {
            UserLoginResult result = UserDAO.login(email, pass);
            if (result == null) {
                showError("E-Mail oder Passwort ist falsch.");
                return;
            }

            String rolle = result.getRolle().toLowerCase();
            String name = result.getFullName();
            String mail = result.getEmail();

            switch (rolle) {
                case "student" -> new StudentFenster(result.getMnr(), name, mail).setVisible(true);
                case "betreuer" -> new DashboardBetreuer(name, mail).setVisible(true);
                case "dekan" -> new StudiendekanPortalFrame(name, mail).setVisible(true);
                default -> showError("Unbekannte Rolle.");
            }

            dispose();

        } catch (Exception ex) {
            showError("Datenbankfehler.");
            ex.printStackTrace();
        }
    }

    // ---------- Helper ----------

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorBox.setVisible(true);
    }

    private void clearError() {
        errorLabel.setText(" ");
        errorBox.setVisible(false);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setForeground(UIColors.TEXT_DARK);
        return l;
    }

    private void fixSize(JComponent c, int w, int h) {
        Dimension d = new Dimension(w, h);
        c.setPreferredSize(d);
        c.setMinimumSize(d);
        c.setMaximumSize(d);
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JPanel left(JComponent c) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.add(c);
        return p;
    }

    private JPanel center(JComponent c) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.setOpaque(false);
        p.add(c);
        return p;
    }

    private JComponent makeLogo(int size) {
        return new JComponent() {
            @Override public Dimension getPreferredSize() {
                return new Dimension(size, size);
            }

            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(UIColors.PRIMARY);
                g2.fillOval(0, 0, size, size);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, size / 3));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("HFT",
                        (size - fm.stringWidth("HFT")) / 2,
                        (size + fm.getAscent()) / 2 - 2);

                g2.dispose();
            }
        };
    }
}
