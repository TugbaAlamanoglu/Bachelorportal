package gui.betreuer;

import datenbank.Bachelorarbeit;
import datenbank.BachelorarbeitDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AntraegePage extends JPanel {

   private final BetreuerFenster parent;

   public AntraegePage(BetreuerFenster parent, String name, String email) {
       this.parent = parent;

       setLayout(new BorderLayout());
       setBackground(BetreuerFenster.BG);

       add(buildContent(), BorderLayout.CENTER);
   }

   private JComponent buildContent() {
       JPanel content = new JPanel();
       content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
       content.setBackground(BetreuerFenster.BG);
       content.setBorder(new EmptyBorder(48, 32, 64, 64)); // Gleiches Padding wie Dashboard

       // Überschrift linksbündig wie Dashboard
       JLabel h1 = new JLabel("Anträge");
       h1.setFont(new Font("SansSerif", Font.BOLD, 22)); // Schriftgröße 22 wie Dashboard
       h1.setForeground(BetreuerFenster.TEXT_DARK);
       h1.setAlignmentX(Component.LEFT_ALIGNMENT); // Linksbündig
       content.add(h1);

       content.add(Box.createVerticalStrut(6));

       // Untertitel linksbündig
       JLabel h2 = new JLabel("Offene Anträge zur Bearbeitung");
       h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
       h2.setForeground(BetreuerFenster.TEXT_MUTED);
       h2.setAlignmentX(Component.LEFT_ALIGNMENT); // Linksbündig
       content.add(h2);

       content.add(Box.createVerticalStrut(18)); // Gleicher Abstand wie Dashboard

       int betreuerId = parent.getBetreuerId();

       try {
           List<Bachelorarbeit> antraege = BachelorarbeitDAO.findForBetreuer(betreuerId);

           if (antraege.isEmpty()) {
               JPanel centeredPanel = new JPanel(new GridBagLayout());
               centeredPanel.setOpaque(false);
               centeredPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
               centeredPanel.setMinimumSize(new Dimension(0, 400));

               JPanel emptyContent = new JPanel();
               emptyContent.setLayout(new BoxLayout(emptyContent, BoxLayout.Y_AXIS));
               emptyContent.setOpaque(false);
               emptyContent.setAlignmentX(Component.CENTER_ALIGNMENT);

               JLabel docIcon = new JLabel("📄");
               docIcon.setFont(new Font("SansSerif", Font.PLAIN, 80));
               docIcon.setForeground(BetreuerFenster.TEXT_MUTED);
               docIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
               emptyContent.add(docIcon);

               emptyContent.add(Box.createVerticalStrut(20));

               JLabel mainMessage = new JLabel("Keine offenen Anträge");
               mainMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
               mainMessage.setForeground(BetreuerFenster.TEXT_DARK);
               mainMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
               emptyContent.add(mainMessage);

               emptyContent.add(Box.createVerticalStrut(10));

               JLabel subMessage = new JLabel("<html><div style='text-align: center;'>Derzeit liegen keine Anträge zur Bearbeitung vor.<br>Neue Anträge von Studierenden werden hier angezeigt.</div></html>");
               subMessage.setFont(new Font("SansSerif", Font.PLAIN, 14));
               subMessage.setForeground(BetreuerFenster.TEXT_MUTED);
               subMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
               emptyContent.add(subMessage);

               emptyContent.add(Box.createVerticalStrut(40));

               JButton backButton = new JButton("←  Zurück zum Dashboard");
               backButton.setFocusPainted(false);
               backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
               backButton.setPreferredSize(new Dimension(200, 44));
               backButton.setMaximumSize(new Dimension(200, 44));
               backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
               backButton.addActionListener(e -> parent.showPage(BetreuerFenster.PAGE_DASHBOARD));
               emptyContent.add(backButton);

               centeredPanel.add(emptyContent);
               content.add(centeredPanel);
           } else {
               for (Bachelorarbeit b : antraege) {
                   JPanel antragCard = makeAntragCard(b);
                   antragCard.setAlignmentX(Component.LEFT_ALIGNMENT); // Karten linksbündig
                   content.add(antragCard);
                   content.add(Box.createVerticalStrut(18));
               }
           }

       } catch (Exception e) {
           e.printStackTrace();
           JLabel errorLabel = new JLabel("Fehler beim Laden der Anträge");
           errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
           errorLabel.setForeground(BetreuerFenster.TEXT_MUTED);
           errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
           content.add(errorLabel);
       }

       content.add(Box.createVerticalGlue());

       JScrollPane sp = new JScrollPane(content);
       sp.setBorder(null);
       sp.getVerticalScrollBar().setUnitIncrement(16);
       sp.getViewport().setBackground(BetreuerFenster.BG);
       return sp;
   }

   private JPanel makeAntragCard(Bachelorarbeit b) {
       JPanel card = new JPanel(new BorderLayout());
       card.setBackground(Color.WHITE);
       card.setBorder(new EmptyBorder(20, 20, 20, 20));
       card.setBorder(new DashboardBetreuer.RoundedBorder(12, BetreuerFenster.BORDER));
       card.setMaximumSize(new Dimension(1000, Integer.MAX_VALUE)); // Maximale Breite für bessere Lesbarkeit

       JPanel infoPanel = new JPanel();
       infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
       infoPanel.setOpaque(false);

       JLabel title = new JLabel("Antrag von: " + b.getStudentVorname() + " " + b.getStudentNachname());
       title.setFont(new Font("SansSerif", Font.BOLD, 16));
       title.setForeground(BetreuerFenster.TEXT_DARK);

       JLabel themaLabel = new JLabel("Thema: " + b.getThema());
       themaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
       themaLabel.setForeground(BetreuerFenster.TEXT_DARK);

       JLabel unternehmenLabel = new JLabel("Unternehmen: " + b.getUnternehmen() + ", " + b.getOrt());
       unternehmenLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
       unternehmenLabel.setForeground(BetreuerFenster.TEXT_MUTED);

       JLabel zeitraumLabel = new JLabel("Zeitraum: " + b.getZeitraumVon() + " bis " + b.getZeitraumBis());
       zeitraumLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
       zeitraumLabel.setForeground(BetreuerFenster.TEXT_MUTED);

       JLabel betreuerLabel = new JLabel("Betreuer im Unternehmen: " + b.getBetreuerUnternehmen());
       betreuerLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
       betreuerLabel.setForeground(BetreuerFenster.TEXT_MUTED);

       JLabel ndaLabel = new JLabel("NDA: " + (b.isNda() ? "Ja" : "Nein"));
       ndaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
       ndaLabel.setForeground(BetreuerFenster.TEXT_MUTED);

       infoPanel.add(title);
       infoPanel.add(Box.createVerticalStrut(12));
       infoPanel.add(themaLabel);
       infoPanel.add(Box.createVerticalStrut(6));
       infoPanel.add(unternehmenLabel);
       infoPanel.add(Box.createVerticalStrut(6));
       infoPanel.add(zeitraumLabel);
       infoPanel.add(Box.createVerticalStrut(6));
       infoPanel.add(betreuerLabel);
       infoPanel.add(Box.createVerticalStrut(6));
       infoPanel.add(ndaLabel);

       card.add(infoPanel, BorderLayout.CENTER);

       JPanel actions = new JPanel();
       actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
       actions.setOpaque(false);

       JTextArea begruendungField = new JTextArea(3, 40);
       begruendungField.setLineWrap(true);
       begruendungField.setWrapStyleWord(true);
       begruendungField.setFont(new Font("SansSerif", Font.PLAIN, 12));
       begruendungField.setBorder(BorderFactory.createCompoundBorder(
           BorderFactory.createLineBorder(BetreuerFenster.BORDER),
           BorderFactory.createEmptyBorder(8, 8, 8, 8)
       ));
       begruendungField.setText("");

       JScrollPane scrollPane = new JScrollPane(begruendungField);
       scrollPane.setBorder(null);
       scrollPane.setMaximumSize(new Dimension(800, 80));

       JLabel begruendungLabel = new JLabel("Begründung (optional):");
       begruendungLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
       begruendungLabel.setForeground(BetreuerFenster.TEXT_MUTED);

       JPanel begruendungPanel = new JPanel(new BorderLayout());
       begruendungPanel.setOpaque(false);
       begruendungPanel.add(begruendungLabel, BorderLayout.NORTH);
       begruendungPanel.add(Box.createVerticalStrut(4), BorderLayout.CENTER);
       begruendungPanel.add(scrollPane, BorderLayout.SOUTH);

       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
       buttonPanel.setOpaque(false);

       JButton deny = new JButton("Ablehnen");
       deny.setBackground(new Color(255, 230, 230));
       deny.setForeground(new Color(200, 0, 0));
       deny.setFocusPainted(false);
       deny.setBorderPainted(false);
       deny.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       deny.setPreferredSize(new Dimension(120, 36));
       deny.addActionListener(e -> {
           String begruendung = begruendungField.getText().trim();
           try {
               BachelorarbeitDAO.updateStatus(b.getId(), "abgelehnt");
               JOptionPane.showMessageDialog(this, 
                   "Antrag abgelehnt." + 
                   (begruendung.isEmpty() ? "" : "\nBegründung: " + begruendung));
               parent.showPage(BetreuerFenster.PAGE_DASHBOARD);
           } catch (Exception ex) {
               ex.printStackTrace();
               JOptionPane.showMessageDialog(this, "Fehler beim Ablehnen: " + ex.getMessage());
           }
       });

       JButton approve = new JButton("Genehmigen");
       approve.setBackground(new Color(230, 245, 230));
       approve.setForeground(new Color(0, 150, 0));
       approve.setFocusPainted(false);
       approve.setBorderPainted(false);
       approve.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       approve.setPreferredSize(new Dimension(120, 36));
       approve.addActionListener(e -> {
           try {
               BachelorarbeitDAO.updateStatus(b.getId(), "genehmigt");
               JOptionPane.showMessageDialog(this, "Antrag genehmigt.");
               parent.showPage(BetreuerFenster.PAGE_DASHBOARD);
           } catch (Exception ex) {
               ex.printStackTrace();
               JOptionPane.showMessageDialog(this, "Fehler beim Genehmigen: " + ex.getMessage());
           }
       });

       buttonPanel.add(deny);
       buttonPanel.add(approve);

       actions.add(begruendungPanel);
       actions.add(Box.createVerticalStrut(12));
       actions.add(buttonPanel);

       card.add(actions, BorderLayout.SOUTH);

       return card;
   }
}
