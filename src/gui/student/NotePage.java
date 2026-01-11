package gui.student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NotePage extends JPanel {

    private static final Color BG = StudentFenster.BG;
    private static final Color BORDER = StudentFenster.BORDER;
    private static final Color PRIMARY = StudentFenster.PRIMARY;
    private static final Color TEXT_DARK = StudentFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudentFenster.TEXT_MUTED;

    private final StudentFenster parent;

    public NotePage(StudentFenster parent, int mnr, String name, String email) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildContent(), BorderLayout.CENTER);
    }

    private JComponent buildContent() {
        JPanel content = new JPanel();
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(28, 28, 28, 28));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Header
        JLabel h1 = new JLabel("Note");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h1);

        content.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Bewertung Ihrer Bachelorarbeit");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h2);

        content.add(Box.createVerticalStrut(40));

        // Clock Icon
        JLabel clockIcon = new JLabel("⏰");
        clockIcon.setFont(new Font("SansSerif", Font.PLAIN, 80));
        clockIcon.setForeground(TEXT_MUTED);
        clockIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(clockIcon);

        content.add(Box.createVerticalStrut(20));

        // Main Message
        JLabel mainMessage = new JLabel("Noch keine Note vorhanden");
        mainMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainMessage.setForeground(TEXT_DARK);
        mainMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(mainMessage);

        content.add(Box.createVerticalStrut(10));

        // Sub Message
        JLabel subMessage = new JLabel("<html><center>Ihre Bachelorarbeit wird derzeit bewertet.<br>Sie erhalten eine Benachrichtigung, sobald die Note verfügbar ist.</center></html>");
        subMessage.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subMessage.setForeground(TEXT_MUTED);
        subMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(subMessage);

        content.add(Box.createVerticalStrut(40));

        // Button to go back
        JButton backButton = new JButton("←  Zurück zum Dashboard");
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(200, 44));
        backButton.setMaximumSize(new Dimension(200, 44));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> parent.showPage(StudentFenster.PAGE_DASHBOARD));
        content.add(backButton);

        content.add(Box.createVerticalGlue());

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(BG);
        return sp;
    }
}