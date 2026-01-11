package gui.authentication;

import datenbank.UserDAO;
import datenbank.UserLoginResult;
import gui.betreuer.BetreuerFenster;
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

        card.add(buildContent(), BorderLayout.CENTER);
        root.add(card);

        setContentPane(root);
    }

    private JPanel buildContent() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(36, 50, 36, 50));

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

        p.add(left(label("E-Mail *")));
        p.add(Box.createVerticalStrut(8));

        tfEmail = new RoundTextField("");
        fixSize(tfEmail, FIELD_W, FIELD_H);
        p.add(left(tfEmail));

        p.add(Box.createVerticalStrut(18));

        p.add(left(label("Passwort *")));
        p.add(Box.createVerticalStrut(8));

        pfPasswort = new RoundPasswordField("");
        fixSize(pfPasswort, FIELD_W, FIELD_H);
        p.add(left(pfPasswort));

        p.add(Box.createVerticalStrut(18));

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
        errorBox.add(errorLabel);

        fixSize(errorBox, FIELD_W, 44);
        p.add(center(errorBox));

        p.add(Box.createVerticalStrut(18));

        JButton btnLogin = new JButton("Einloggen");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(UIColors.PRIMARY);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        fixSize(btnLogin, FIELD_W, BTN_H);
        btnLogin.addActionListener(e -> onLogin());
        p.add(center(btnLogin));

        p.add(Box.createVerticalGlue());
        return p;
    }

    private void onLogin() {
        clearError();

        try {
            UserLoginResult result = UserDAO.login(
                    tfEmail.getRealText(),
                    pfPasswort.getRealPassword()
            );

            if (result == null) {
                showError("E-Mail oder Passwort ist falsch.");
                return;
            }

            String name = result.getFullName();
            String mail = result.getEmail();

            switch (result.getRolle()) {
                case "student" -> new StudentFenster(result.getMnr(), name, mail).setVisible(true);
                case "betreuer" -> new BetreuerFenster(result.getMnr(), name, mail).setVisible(true);
                case "dekan" -> new StudiendekanPortalFrame(name, mail).setVisible(true);
                default -> showError("Unbekannte Rolle.");
            }

            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Datenbankfehler.");
        }
    }

    // ---------- Helper (unverändert) ----------
    private void showError(String msg) { errorLabel.setText(msg); errorBox.setVisible(true); }
    private void clearError() { errorBox.setVisible(false); }

    private JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setForeground(UIColors.TEXT_DARK);
        return l;
    }

    private void fixSize(JComponent c, int w, int h) {
        Dimension d = new Dimension(w, h);
        c.setPreferredSize(d);
        c.setMaximumSize(d);
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
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(UIColors.PRIMARY);
                g2.fillOval(0, 0, size, size);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, size / 3));
                g2.drawString("HFT", size / 4, size / 2 + 6);
            }
        };
    }
}
