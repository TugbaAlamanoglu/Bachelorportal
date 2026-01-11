package gui.betreuer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NotenvergabePage extends JPanel {

    private static final Color BG = BetreuerFenster.BG;
    private static final Color BORDER = BetreuerFenster.BORDER;
    private static final Color PRIMARY = BetreuerFenster.PRIMARY;
    private static final Color TEXT_DARK = BetreuerFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = BetreuerFenster.TEXT_MUTED;

    private final BetreuerFenster parent;

    public NotenvergabePage(BetreuerFenster parent, String name, String email) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildContent(), BorderLayout.CENTER);
    }

    private JComponent buildContent() {
        // Outer wrapper
        JPanel outerWrapper = new JPanel(new BorderLayout());
        outerWrapper.setBackground(BG);
        
        // Main content panel
        JPanel content = new JPanel();
        content.setBackground(BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(28, 28, 28, 28));

        // Header LINKSBÜNDIG
        JLabel h1 = new JLabel("Notenvergabe");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h1);

        content.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Bewertung der Bachelorarbeiten");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h2);

        content.add(Box.createVerticalStrut(40));

        // Centered content area
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.setOpaque(false);
        
        JPanel centeredContent = new JPanel();
        centeredContent.setLayout(new BoxLayout(centeredContent, BoxLayout.Y_AXIS));
        centeredContent.setOpaque(false);
        centeredContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Lock Icon (wie im Screenshot)
        JLabel lockIcon = new JLabel("🔒");
        lockIcon.setFont(new Font("SansSerif", Font.PLAIN, 80));
        lockIcon.setForeground(TEXT_MUTED);
        lockIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        centeredContent.add(lockIcon);

        centeredContent.add(Box.createVerticalStrut(20));

        // Main Message (wie im Screenshot)
        JLabel mainMessage = new JLabel("Notenvergabe gesperrt");
        mainMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainMessage.setForeground(TEXT_DARK);
        mainMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        centeredContent.add(mainMessage);

        centeredContent.add(Box.createVerticalStrut(10));

        // Sub Message (wie im Screenshot)
        JLabel subMessage = new JLabel("<html><div style='text-align: center;'>Die Notenvergabe ist erst nach der finalen Abgabe der Bachelorarbeit möglich.<br><br>Sobald ein Student seine Arbeit eingereicht hat, können Sie hier Ihre Bewertung abgeben.</div></html>");
        subMessage.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subMessage.setForeground(TEXT_MUTED);
        subMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        centeredContent.add(subMessage);

        centeredContent.add(Box.createVerticalStrut(40));

        // Button to go back to dashboard
        JButton backButton = new JButton("←  Zurück zum Dashboard");
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(200, 44));
        backButton.setMaximumSize(new Dimension(200, 44));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> parent.showPage(BetreuerFenster.PAGE_DASHBOARD));
        centeredContent.add(backButton);

        // Add centered content to panel
        centeredPanel.add(centeredContent);
        
        // Add everything to main content
        content.add(centeredPanel);
        content.add(Box.createVerticalGlue());
        
        outerWrapper.add(content, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(outerWrapper);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(BG);
        return sp;
    }
}