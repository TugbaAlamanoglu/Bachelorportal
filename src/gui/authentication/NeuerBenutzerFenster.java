package gui.authentication;

import datenbank.UserDAO;
import gui.shared.RoundPasswordField;
import gui.shared.RoundTextField;
import util.UIColors;
import gui.student.StudentFenster;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NeuerBenutzerFenster extends JFrame {

    private static final int CARD_W = 560;
    private static final int FORM_W = 480;

    private RoundTextField tfVorname;
    private RoundTextField tfNachname;
    private RoundTextField tfEmail;
    private RoundPasswordField pfPasswort;
    private RoundPasswordField pfPasswortWdh;

    // Inline Fehlerlabels
    private JLabel errVorname;
    private JLabel errNachname;
    private JLabel errEmail;
    private JLabel errPw;
    private JLabel errPw2;

    // Passwort-Regeln (live)
    private JLabel ruleLen;
    private JLabel ruleUpper;
    private JLabel ruleLower;

    // Borders
    private Border defaultBorderText;
    private Border defaultBorderPw;
    private Border errorBorder;

    private JPanel scrollRoot;
    private ShadowCard card;

    public NeuerBenutzerFenster() {
        super("Bachelorarbeits-Portal");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);

        // ===== Scroll-Root =====
        scrollRoot = new JPanel();
        scrollRoot.setLayout(new BoxLayout(scrollRoot, BoxLayout.Y_AXIS));
        scrollRoot.setOpaque(true);
        scrollRoot.setBackground(Color.WHITE);
        scrollRoot.setBorder(new EmptyBorder(40, 0, 40, 0));

        // ===== HEADER =====
        scrollRoot.add(centered(makeLogo(86)));
        scrollRoot.add(Box.createVerticalStrut(14));

        JLabel uni = new JLabel("Hochschule für Technik Stuttgart");
        uni.setFont(new Font("SansSerif", Font.PLAIN, 15));
        uni.setForeground(UIColors.TEXT_MUTED);
        scrollRoot.add(centered(uni));

        scrollRoot.add(Box.createVerticalStrut(28));

        // ===== CARD =====
        card = new ShadowCard(18);
        card.setLayout(new BorderLayout());
        card.add(buildContent(), BorderLayout.CENTER);
        card.setMaximumSize(new Dimension(CARD_W, Integer.MAX_VALUE));

        JPanel cardCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        cardCenter.setOpaque(true);
        cardCenter.setBackground(Color.WHITE);
        cardCenter.add(card);

        scrollRoot.add(cardCenter);
        scrollRoot.add(Box.createVerticalStrut(10));

        // ===== ScrollPane =====
        JScrollPane scroll = new JScrollPane(scrollRoot);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(true);
        scroll.getViewport().setOpaque(true);
        scroll.getViewport().setBackground(Color.WHITE);

        setContentPane(scroll);
        getContentPane().setBackground(Color.WHITE);

        // Borders vorbereiten (nachdem Fields erstellt sind, setzen wir die richtigen Defaults)
        errorBorder = new LineBorder(new Color(0xE3, 0x06, 0x13), 2, true);

        // Live-Validierung aktivieren
        installLiveValidation();
        updatePasswordRules(""); // initial
    }

    private JPanel buildContent() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(10, 40, 16, 40));

        JLabel title = new JLabel("Neuen Benutzer erstellen");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(UIColors.TEXT_DARK);
        p.add(centered(title));
        p.add(Box.createVerticalStrut(40));

        JPanel formWrap = new JPanel();
        formWrap.setOpaque(false);
        formWrap.setLayout(new BoxLayout(formWrap, BoxLayout.Y_AXIS));
        formWrap.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Felder + Fehlerlabels
        JLabel[] out = new JLabel[1];

        tfVorname  = field(formWrap, "Vorname *", out);  errVorname  = out[0];
        tfNachname = field(formWrap, "Nachname *", out); errNachname = out[0];
        tfEmail    = field(formWrap, "E-Mail *", out);   errEmail    = out[0];

        pfPasswort = passwordField(formWrap, "Passwort *", out); errPw = out[0];

        // Default Borders merken (nach Erstellung)
        defaultBorderText = tfVorname.getBorder();
        defaultBorderPw = pfPasswort.getBorder();

        // Passwort-Regeln Panel (Design passt sich an, ohne Layout zu zerstören)
        formWrap.add(left(buildPasswordRulesPanel()));
        formWrap.add(Box.createVerticalStrut(14));

        pfPasswortWdh = passwordField(formWrap, "Passwort wiederholen *", out); errPw2 = out[0];

        p.add(formWrap);
        p.add(Box.createVerticalStrut(8));

        JButton btn = new JButton("Benutzer erstellen");
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(UIColors.PRIMARY);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        fix(btn, FORM_W, 50);
        p.add(centered(btn));

        btn.addActionListener(e -> onCreateStudent());

        p.add(Box.createVerticalStrut(18));

        JLabel back = new JLabel("<html><u>Bereits ein Konto? Zum Login</u></html>");
        back.setFont(new Font("SansSerif", Font.BOLD, 14));
        back.setForeground(UIColors.LINK);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new LoginFenster().setVisible(true);
                dispose();
            }
        });
        p.add(centered(back));

        return p;
    }

    // ✅ Registrierung: immer Student
    // ✅ Keine Popups bei Validierung – nur Inline
    // ✅ Bei Erfolg: direkt StudentFenster öffnen
    private void onCreateStudent() {
        // Erst alle Fehler zurücksetzen
        clearAllErrors();

        String vorname  = tfVorname.getText().trim();
        String nachname = tfNachname.getText().trim();
        String email    = tfEmail.getText().trim();
        String pw       = new String(pfPasswort.getPassword());
        String pw2      = new String(pfPasswortWdh.getPassword());

        boolean ok = true;

        if (vorname.isEmpty()) { setError(tfVorname, errVorname, "Vorname ist erforderlich"); ok = false; }
        if (nachname.isEmpty()) { setError(tfNachname, errNachname, "Nachname ist erforderlich"); ok = false; }

        if (email.isEmpty()) {
            setError(tfEmail, errEmail, "E-Mail ist erforderlich");
            ok = false;
        } else if (!isValidEmail(email)) {
            setError(tfEmail, errEmail, "Bitte eine gültige E-Mail eingeben");
            ok = false;
        }

        if (pw.isEmpty()) {
            setError(pfPasswort, errPw, "Passwort ist erforderlich");
            ok = false;
        } else if (!isValidPassword(pw)) {
            setError(pfPasswort, errPw, "Passwort erfüllt die Bedingungen nicht");
            ok = false;
        }

        if (pw2.isEmpty()) {
            setError(pfPasswortWdh, errPw2, "Bitte Passwort wiederholen");
            ok = false;
        } else if (!pw.equals(pw2)) {
            setError(pfPasswortWdh, errPw2, "Passwörter stimmen nicht überein");
            ok = false;
        }

        if (!ok) return;

        try {
            int neueMnr = UserDAO.registerStudent(vorname, nachname, email, pw);

            // optional: Erfolg kurz zeigen (kannst du auch komplett entfernen)
            JOptionPane.showMessageDialog(this,
                    "Student erfolgreich erstellt!\n(MNR: " + neueMnr + ")",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE);

            // ✅ Direkt ins StudentFenster
            String fullName = vorname + " " + nachname;
            new StudentFenster(neueMnr, fullName, email).setVisible(true);
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            // DB-Fehler als inline bei E-Mail zeigen (z.B. "E-Mail existiert schon")
            setError(tfEmail, errEmail, "Fehler beim Erstellen: " + ex.getMessage());
        }
    }

    // ---------------- Live Validation ----------------

    private void installLiveValidation() {
        if (tfVorname != null) {
            addDocListener(tfVorname, () -> {
                if (!tfVorname.getText().trim().isEmpty()) clearError(tfVorname, errVorname);
            });
        }

        if (tfNachname != null) {
            addDocListener(tfNachname, () -> {
                if (!tfNachname.getText().trim().isEmpty()) clearError(tfNachname, errNachname);
            });
        }

        if (tfEmail != null) {
            addDocListener(tfEmail, () -> {
                String mail = tfEmail.getText().trim();
                if (mail.isEmpty()) return;
                if (isValidEmail(mail)) clearError(tfEmail, errEmail);
            });
        }

        if (pfPasswort != null) {
            addDocListener(pfPasswort, () -> {
                String pw = new String(pfPasswort.getPassword());
                updatePasswordRules(pw);

                if (!pw.isEmpty() && isValidPassword(pw)) clearError(pfPasswort, errPw);

                // Match live prüfen, sobald pw2 nicht leer
                if (pfPasswortWdh != null) {
                    String pw2 = new String(pfPasswortWdh.getPassword());
                    if (!pw2.isEmpty()) updatePasswordMatch(pw, pw2);
                }
            });
        }

        if (pfPasswortWdh != null) {
            addDocListener(pfPasswortWdh, () -> {
                String pw = (pfPasswort == null) ? "" : new String(pfPasswort.getPassword());
                String pw2 = new String(pfPasswortWdh.getPassword());
                updatePasswordMatch(pw, pw2);
            });
        }
    }

    private void updatePasswordMatch(String pw, String pw2) {
        if (pw2.isEmpty()) {
            clearError(pfPasswortWdh, errPw2);
            return;
        }
        if (!pw.equals(pw2)) {
            setError(pfPasswortWdh, errPw2, "Passwörter stimmen nicht überein");
        } else {
            clearError(pfPasswortWdh, errPw2);
        }
    }

    private void addDocListener(JTextField field, Runnable onChange) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { onChange.run(); }
            @Override public void removeUpdate(DocumentEvent e) { onChange.run(); }
            @Override public void changedUpdate(DocumentEvent e) { onChange.run(); }
        });
    }

    // ---------------- Rules & Validation Helpers ----------------

    private JComponent buildPasswordRulesPanel() {
        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Passwortregeln:");
        title.setFont(new Font("SansSerif", Font.BOLD, 12));
        title.setForeground(UIColors.TEXT_MUTED);
        box.add(title);
        box.add(Box.createVerticalStrut(6));

        ruleLen = new JLabel("• Mindestens 6 Zeichen");
        ruleUpper = new JLabel("• Mindestens 1 Großbuchstabe");
        ruleLower = new JLabel("• Mindestens 1 Kleinbuchstabe");

        ruleLen.setFont(new Font("SansSerif", Font.PLAIN, 12));
        ruleUpper.setFont(new Font("SansSerif", Font.PLAIN, 12));
        ruleLower.setFont(new Font("SansSerif", Font.PLAIN, 12));

        ruleLen.setForeground(UIColors.TEXT_MUTED);
        ruleUpper.setForeground(UIColors.TEXT_MUTED);
        ruleLower.setForeground(UIColors.TEXT_MUTED);

        box.add(ruleLen);
        box.add(Box.createVerticalStrut(4));
        box.add(ruleUpper);
        box.add(Box.createVerticalStrut(4));
        box.add(ruleLower);

        return box;
    }

    private void updatePasswordRules(String pw) {
        boolean okLen = pw.length() >= 6;
        boolean okUpper = pw.matches(".*[A-Z].*");
        boolean okLower = pw.matches(".*[a-z].*");

        setRule(ruleLen, okLen);
        setRule(ruleUpper, okUpper);
        setRule(ruleLower, okLower);
    }

    private void setRule(JLabel label, boolean ok) {
        if (label == null) return;
        String raw = label.getText().replace("✓ ", "").replace("• ", "");
        label.setText((ok ? "✓ " : "• ") + raw);
        label.setForeground(ok ? new Color(0x1E, 0x88, 0x5A) : UIColors.TEXT_MUTED);
    }

    private boolean isValidPassword(String pw) {
        return pw.length() >= 6 && pw.matches(".*[A-Z].*") && pw.matches(".*[a-z].*");
    }

    private boolean isValidEmail(String email) {
        // simpel & ausreichend für Uni-Projekt
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // ---------------- Error UI ----------------

    private void clearAllErrors() {
        clearError(tfVorname, errVorname);
        clearError(tfNachname, errNachname);
        clearError(tfEmail, errEmail);
        clearError(pfPasswort, errPw);
        clearError(pfPasswortWdh, errPw2);
    }

    private void setError(JComponent field, JLabel errLabel, String msg) {
        if (errLabel != null) {
            errLabel.setText(msg);
            errLabel.setVisible(true);
        }
        if (field != null) {
            field.setBorder(errorBorder);
        }
        revalidate();
        repaint();
    }

    private void clearError(JComponent field, JLabel errLabel) {
        if (errLabel != null) {
            errLabel.setText(" ");
            errLabel.setVisible(false);
        }
        if (field != null) {
            // je nachdem ob Text oder Passwort – sonst neutrale Border
            if (field == pfPasswort || field == pfPasswortWdh) {
                field.setBorder(defaultBorderPw != null ? defaultBorderPw : UIManager.getBorder("TextField.border"));
            } else {
                field.setBorder(defaultBorderText != null ? defaultBorderText : UIManager.getBorder("TextField.border"));
            }
        }
        revalidate();
        repaint();
    }

    // ===== Helper =====

    private RoundTextField field(JPanel parent, String text, JLabel[] errOut) {
        parent.add(left(label(text)));
        parent.add(Box.createVerticalStrut(8));

        RoundTextField tf = new RoundTextField("");
        fix(tf, FORM_W, 46);
        parent.add(left(tf));

        JLabel err = new JLabel(" ");
        err.setFont(new Font("SansSerif", Font.PLAIN, 12));
        err.setForeground(new Color(0xE3, 0x06, 0x13));
        err.setVisible(false);
        parent.add(left(err));

        parent.add(Box.createVerticalStrut(18));

        if (errOut != null && errOut.length > 0) errOut[0] = err;
        return tf;
    }

    private RoundPasswordField passwordField(JPanel parent, String text, JLabel[] errOut) {
        parent.add(left(label(text)));
        parent.add(Box.createVerticalStrut(8));

        RoundPasswordField pf = new RoundPasswordField("");
        fix(pf, FORM_W, 46);
        parent.add(left(pf));

        JLabel err = new JLabel(" ");
        err.setFont(new Font("SansSerif", Font.PLAIN, 12));
        err.setForeground(new Color(0xE3, 0x06, 0x13));
        err.setVisible(false);
        parent.add(left(err));

        parent.add(Box.createVerticalStrut(18));

        if (errOut != null && errOut.length > 0) errOut[0] = err;
        return pf;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setForeground(UIColors.TEXT_DARK);
        return l;
    }

    private void fix(JComponent c, int w, int h) {
        Dimension d = new Dimension(w, h);
        c.setPreferredSize(d);
        c.setMinimumSize(d);
        c.setMaximumSize(d);
    }

    private JPanel left(JComponent c) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.add(c);
        return p;
    }

    private JPanel centered(JComponent c) {
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
                g2.setFont(new Font("SansSerif", Font.BOLD, 26));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("HFT",
                        (size - fm.stringWidth("HFT")) / 2,
                        (size + fm.getAscent()) / 2 - 2);
                g2.dispose();
            }
        };
    }

    private static class ShadowCard extends JPanel {
        private final int radius;
        private final int shadow = 18;

        ShadowCard(int radius) {
            this.radius = radius;
            setOpaque(false);
            setLayout(new BorderLayout());
            int pad = shadow / 2;
            setBorder(new EmptyBorder(pad, pad, pad, pad));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Insets in = getInsets();
            int x = in.left;
            int y = in.top;
            int w = getWidth() - in.left - in.right;
            int h = getHeight() - in.top - in.bottom;

            for (int i = 0; i < 10; i++) {
                int alpha = 20 - i * 2;
                g2.setColor(new Color(0, 0, 0, Math.max(alpha, 0)));
                g2.fillRoundRect(x + i, y + i, w - i * 2, h - i * 2, radius, radius);
            }

            g2.setColor(UIColors.CARD_BG);
            g2.fillRoundRect(x, y, w, h, radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
