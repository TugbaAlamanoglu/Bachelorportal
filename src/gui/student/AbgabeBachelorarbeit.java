package gui.student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AbgabeBachelorarbeit extends JPanel {

    private static final Color BG = StudentFenster.BG;
    private static final Color BORDER = StudentFenster.BORDER;
    private static final Color PRIMARY = StudentFenster.PRIMARY;
    private static final Color TEXT_DARK = StudentFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudentFenster.TEXT_MUTED;

    // --- Persistenz (Demo: bleibt im laufenden Programm erhalten) ---
    private static boolean submitted = false;

    private final StudentFenster parent;

    public AbgabeBachelorarbeit(StudentFenster parent, int mnr, String name, String email) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildLockedContent(), BorderLayout.CENTER);
    }

    private JComponent buildLockedContent() {
        JPanel content = new JPanel();
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(28, 28, 28, 28));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Header LINKSBÜNDIG
        JLabel h1 = new JLabel("Endgültige Abgabe");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h1);

        content.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Einreichung der finalen Version");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h2);

        content.add(Box.createVerticalStrut(40));

        // Zentrierter Inhalt
        JPanel centeredContent = new JPanel();
        centeredContent.setLayout(new BoxLayout(centeredContent, BoxLayout.Y_AXIS));
        centeredContent.setOpaque(false);
        centeredContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Lock Icon
        JLabel lockIcon = new JLabel("🔒");
        lockIcon.setFont(new Font("SansSerif", Font.PLAIN, 80));
        lockIcon.setForeground(TEXT_MUTED);
        lockIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        centeredContent.add(lockIcon);

        centeredContent.add(Box.createVerticalStrut(20));

        // Main Message
        JLabel mainMessage = new JLabel("Abgabe noch nicht möglich");
        mainMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainMessage.setForeground(TEXT_DARK);
        mainMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        centeredContent.add(mainMessage);

        centeredContent.add(Box.createVerticalStrut(10));

        // Sub Message
        JLabel subMessage = new JLabel("Die endgültige Abgabe ist erst nach der Anmeldung Ihrer Bachelorarbeit möglich.");
        subMessage.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subMessage.setForeground(TEXT_MUTED);
        subMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        centeredContent.add(subMessage);

        centeredContent.add(Box.createVerticalStrut(40));

        // Button to go back
        JButton backButton = new JButton("←  Zurück zum Dashboard");
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(200, 44));
        backButton.setMaximumSize(new Dimension(200, 44));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> parent.showPage(StudentFenster.PAGE_DASHBOARD));
        centeredContent.add(backButton);

        // Zentrierten Content in das Hauptpanel einfügen
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(centeredContent);
        
        content.add(wrapper);
        content.add(Box.createVerticalGlue());

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(BG);
        return sp;
    }

    // ------------------------------------------------------------
    // Public helper for Dashboard (keine direkten Felder!)
    // ------------------------------------------------------------

    public static boolean isSubmitted() {
        return submitted;
    }
}