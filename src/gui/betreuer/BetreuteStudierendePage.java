


package gui.betreuer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BetreuteStudierendePage extends JPanel {

   private static final Color BG = BetreuerFenster.BG;
   private static final Color BORDER = BetreuerFenster.BORDER;
   private static final Color PRIMARY = BetreuerFenster.PRIMARY;
   private static final Color TEXT_DARK = BetreuerFenster.TEXT_DARK;
   private static final Color TEXT_MUTED = BetreuerFenster.TEXT_MUTED;

   private final BetreuerFenster parent;

   public BetreuteStudierendePage(BetreuerFenster parent, String name, String email) {
       this.parent = parent;

       setLayout(new BorderLayout());
       setBackground(BG);

       add(buildContent(), BorderLayout.CENTER);
   }

   private JComponent buildContent() {
       // Main content panel
       JPanel content = new JPanel();
       content.setBackground(BG);
       content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
       content.setBorder(new EmptyBorder(48, 32, 64, 64)); // Gleiches Padding wie Dashboard

       // Header LINKSBÜNDIG (wie Dashboard)
       JLabel h1 = new JLabel("Betreute Studierende");
       h1.setFont(new Font("SansSerif", Font.BOLD, 22)); // Gleiche Größe wie Dashboard (22 statt 26)
       h1.setForeground(TEXT_DARK);
       h1.setAlignmentX(Component.LEFT_ALIGNMENT);
       content.add(h1);

       content.add(Box.createVerticalStrut(6));

       JLabel h2 = new JLabel("Übersicht Ihrer aktiven Betreuungen");
       h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
       h2.setForeground(TEXT_MUTED);
       h2.setAlignmentX(Component.LEFT_ALIGNMENT);
       content.add(h2);

       content.add(Box.createVerticalStrut(18)); // Gleicher Abstand wie Dashboard

       // Centered content area
       JPanel centeredPanel = new JPanel(new GridBagLayout());
       centeredPanel.setOpaque(false);
       centeredPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
       centeredPanel.setMinimumSize(new Dimension(0, 400));
       centeredPanel.setPreferredSize(new Dimension(content.getWidth(), 400));

       JPanel centeredContent = new JPanel();
       centeredContent.setLayout(new BoxLayout(centeredContent, BoxLayout.Y_AXIS));
       centeredContent.setOpaque(false);
       centeredContent.setAlignmentX(Component.CENTER_ALIGNMENT);

       // Document Icon
       JLabel docIcon = new JLabel("👥");
       docIcon.setFont(new Font("SansSerif", Font.PLAIN, 80));
       docIcon.setForeground(TEXT_MUTED);
       docIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
       centeredContent.add(docIcon);

       centeredContent.add(Box.createVerticalStrut(20));

       // Main Message (wie im Screenshot)
       JLabel mainMessage = new JLabel("Keine aktiven Betreuungen");
       mainMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
       mainMessage.setForeground(TEXT_DARK);
       mainMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
       centeredContent.add(mainMessage);

       centeredContent.add(Box.createVerticalStrut(10));

       // Sub Message (wie im Screenshot)
       JLabel subMessage = new JLabel("<html><div style='text-align: center;'>Derzeit betreuen Sie keine Studierenden.<br>Sobald ein Antrag endgültig genehmigt wurde und sich der Student<br>zur Bachelorarbeit angemeldet hat, erscheint er hier.</div></html>");
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

       // Add centered panel to main content (aber linksbündig im Gesamtlayout)
       content.add(centeredPanel);
       content.add(Box.createVerticalGlue());

       // Scroll Pane
       JScrollPane sp = new JScrollPane(content);
       sp.setBorder(null);
       sp.getVerticalScrollBar().setUnitIncrement(16);
       sp.getViewport().setBackground(BG);

       // Wrapper für konsistentes Layout
       JPanel wrapper = new JPanel(new BorderLayout());
       wrapper.setBackground(BG);
       wrapper.add(sp, BorderLayout.CENTER);
       return wrapper;
   }
}

