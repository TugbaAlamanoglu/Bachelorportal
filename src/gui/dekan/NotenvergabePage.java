package gui.dekan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NotenvergabePage extends JPanel {

    private static final Color BG = StudiendekanFenster.BG;
    private static final Color BORDER = StudiendekanFenster.BORDER;
    private static final Color PRIMARY = StudiendekanFenster.PRIMARY;
    private static final Color TEXT_DARK = StudiendekanFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudiendekanFenster.TEXT_MUTED;

    private final StudiendekanFenster parent;

    public NotenvergabePage(StudiendekanFenster parent, String name, String email) {
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
        JLabel h1 = new JLabel("Notenvergabe (Zweitprüfer)");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h1);

        content.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Bewertung der Bachelorarbeiten als Zweitprüfer");
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

        // Star Icon
        JLabel starIcon = new JLabel("⭐");
        starIcon.setFont(new Font("SansSerif", Font.PLAIN, 80));
        starIcon.setForeground(TEXT_MUTED);
        starIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        centeredContent.add(starIcon);

        centeredContent.add(Box.createVerticalStrut(20));

        // Main Message
        JLabel mainMessage = new JLabel("Keine ausstehenden Bewertungen");
        mainMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainMessage.setForeground(TEXT_DARK);
        mainMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        centeredContent.add(mainMessage);

        centeredContent.add(Box.createVerticalStrut(10));

        // Sub Message
        JLabel subMessage = new JLabel("<html><div style='text-align: center;'>Momentan liegen keine Bachelorarbeiten zur Zweitbewertung vor.<br>Sobald Betreuer Arbeiten zur Zweitprüfung freigeben, erscheinen diese hier.</div></html>");
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
        backButton.addActionListener(e -> parent.showPage(StudiendekanFenster.PAGE_DASHBOARD));
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