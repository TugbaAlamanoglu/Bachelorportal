package gui.student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AnmeldungBachelorarbeitStudent extends JPanel {

    private static final Color BG = StudentFenster.BG;
    private static final Color TEXT_DARK = StudentFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudentFenster.TEXT_MUTED;

    private final StudentFenster parent;

    public AnmeldungBachelorarbeitStudent(StudentFenster parent, int mnr, String name, String email) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildContent(), BorderLayout.CENTER); 
    }

    private JComponent buildContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG);

        // 🔹 GLEICHES PRINZIP WIE BEIM BETREUER
        content.setBorder(new EmptyBorder(48, 32, 64, 64));

        // ===== ÜBERSCHRIFT LINKS (WIE BETREUER) =====
        JLabel h1 = new JLabel("Anmeldung Bachelorarbeit");
        h1.setFont(new Font("SansSerif", Font.BOLD, 22));
        h1.setForeground(TEXT_DARK);
        h1.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h1);

        content.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Offizielle Anmeldung beim Prüfungsamt");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        h2.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(h2);

        content.add(Box.createVerticalStrut(18));

        // ===== ZENTRIERTER INHALT (WIE BEIM BETREUER-EMPTY-STATE) =====
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.setOpaque(false);
        centeredPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centeredPanel.setMinimumSize(new Dimension(0, 400));

        JPanel emptyContent = new JPanel();
        emptyContent.setLayout(new BoxLayout(emptyContent, BoxLayout.Y_AXIS));
        emptyContent.setOpaque(false);
        emptyContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lockIcon = new JLabel("🔒");
        lockIcon.setFont(new Font("SansSerif", Font.PLAIN, 80));
        lockIcon.setForeground(TEXT_MUTED);
        lockIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyContent.add(lockIcon);

        
        emptyContent.add(Box.createVerticalStrut(20));

        JLabel mainMessage = new JLabel("Anmeldung noch nicht möglich");
        mainMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainMessage.setForeground(TEXT_DARK);
        mainMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyContent.add(mainMessage);

        emptyContent.add(Box.createVerticalStrut(10));

        JLabel subMessage = new JLabel(
            "<html><div style='text-align: center;'>"
            + "Die Anmeldung zur Bachelorarbeit ist erst nach Genehmigung Ihres Antrags möglich."
            + "</div></html>"
        );
        subMessage.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subMessage.setForeground(TEXT_MUTED);
        subMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptyContent.add(subMessage);

        emptyContent.add(Box.createVerticalStrut(40));

        JButton backButton = new JButton("←  Zurück zum Dashboard");
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(200, 44));
        backButton.setMaximumSize(new Dimension(200, 44));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> parent.showPage(StudentFenster.PAGE_DASHBOARD));
        emptyContent.add(backButton);

        centeredPanel.add(emptyContent);
        content.add(centeredPanel);

        content.add(Box.createVerticalGlue());

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.getViewport().setBackground(BG);

        return sp;
    }
}
