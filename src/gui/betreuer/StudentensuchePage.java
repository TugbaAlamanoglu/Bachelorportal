package gui.betreuer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentensuchePage extends JPanel {

    private static final Color BG = BetreuerFenster.BG;
    private static final Color BORDER = BetreuerFenster.BORDER;
    private static final Color PRIMARY = BetreuerFenster.PRIMARY;
    private static final Color TEXT_DARK = BetreuerFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = BetreuerFenster.TEXT_MUTED;

    private final BetreuerFenster parent;

    public StudentensuchePage(BetreuerFenster parent, String name, String email) {
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
        JLabel h1 = new JLabel("Studentensuche");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h1);

        content.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Suche nach Studierenden im System");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h2);

        content.add(Box.createVerticalStrut(40));

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout(12, 0));
        searchPanel.setOpaque(false);
        searchPanel.setMaximumSize(new Dimension(600, 50));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Search field
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBackground(new Color(245, 246, 248));
        searchField.setBorder(new EmptyBorder(12, 12, 12, 12));
        searchField.setPreferredSize(new Dimension(0, 44));
        searchField.setMinimumSize(new Dimension(0, 44));

        // Search button
        JButton searchButton = new JButton("Suchen");
        searchButton.setFocusPainted(false);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(PRIMARY);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setPreferredSize(new Dimension(120, 44));
        searchButton.setMinimumSize(new Dimension(120, 44));
        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            if (query.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie einen Suchbegriff ein.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Suche nach: " + query + "\n(Implementierung folgt)", "Suche", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        content.add(searchPanel);
        content.add(Box.createVerticalStrut(20));

        // Hinweis
        JLabel hint = new JLabel("<html><div style='color: #666666; font-size: 13px;'>Suchen Sie nach Namen, Matrikelnummern oder Themen. Geben Sie mindestens 3 Zeichen ein.</div></html>");
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(hint);

        content.add(Box.createVerticalGlue());
        
        outerWrapper.add(content, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(outerWrapper);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(BG);
        return sp;
    }
}