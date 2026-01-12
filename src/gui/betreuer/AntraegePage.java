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
        content.setBorder(new EmptyBorder(28, 28, 28, 28));

        JLabel h1 = new JLabel("Anträge");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(BetreuerFenster.TEXT_DARK);

        content.add(h1);
        content.add(Box.createVerticalStrut(20));

        int betreuerId = parent.getBetreuerId();

        try {
            List<Bachelorarbeit> antraege = BachelorarbeitDAO.findForBetreuer(betreuerId);

            if (antraege.isEmpty()) {
                JLabel emptyLabel = new JLabel("Keine Anträge vorhanden");
                emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                emptyLabel.setForeground(BetreuerFenster.TEXT_MUTED);
                content.add(emptyLabel);
            } else {
                for (Bachelorarbeit b : antraege) {
                    content.add(makeAntragCard(b));
                    content.add(Box.createVerticalStrut(10));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            content.add(new JLabel("Fehler beim Laden der Anträge"));
        }

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getViewport().setBackground(BetreuerFenster.BG);
        return sp;
    }

    private JComponent makeAntragCard(Bachelorarbeit b) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        card.setBorder(new DashboardBetreuer.RoundedBorder(12, BetreuerFenster.BORDER));

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
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(themaLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(unternehmenLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(zeitraumLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(betreuerLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(ndaLabel);

        card.add(infoPanel, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        actions.setOpaque(false);

        JTextArea begruendungField = new JTextArea(3, 30);
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
        actions.add(Box.createVerticalStrut(10));
        actions.add(buttonPanel);

        card.add(actions, BorderLayout.SOUTH);

        return card;
    }
}