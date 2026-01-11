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

        int betreuerId = parent.getBetreuerId(); // ✅ HIER DER FIX

        try {
            List<Bachelorarbeit> antraege =
                    BachelorarbeitDAO.findForBetreuer(betreuerId);

            if (antraege.isEmpty()) {
                content.add(new JLabel("Keine Anträge vorhanden"));
            }

            for (Bachelorarbeit b : antraege) {
                content.add(makeAntragCard(b));
                content.add(Box.createVerticalStrut(10));
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

        JLabel title = new JLabel("Thema: " + b.getThema());
        title.setFont(new Font("SansSerif", Font.BOLD, 15));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton approve = new JButton("Genehmigen");
        JButton deny = new JButton("Ablehnen");

        actions.add(deny);
        actions.add(approve);

        card.add(title, BorderLayout.NORTH);
        card.add(actions, BorderLayout.SOUTH);

        return card;
    }
}
