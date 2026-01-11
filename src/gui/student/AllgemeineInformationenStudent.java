package gui.student;

import datenbank.BachelorarbeitDAO;
import datenbank.BetreuerDAO;
import datenbank.UserLoginResult;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AllgemeineInformationenStudent extends JPanel {

    private final int studentId;

    private JTextField themaField;
    private JTextField unternehmenField;
    private JTextField zeitraumField;
    private JComboBox<UserLoginResult> betreuerBox;

    public AllgemeineInformationenStudent(StudentFenster parent, int mnr, String name, String email) {
        this.studentId = mnr;

        setLayout(new BorderLayout());
        setBackground(StudentFenster.BG);

        add(buildContent(parent), BorderLayout.CENTER);
    }

    private JComponent buildContent(StudentFenster parent) {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(StudentFenster.BG);
        content.setBorder(new EmptyBorder(32, 32, 32, 32));

        JLabel h1 = new JLabel("Allgemeine Informationen");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(StudentFenster.TEXT_DARK);

        content.add(h1);
        content.add(Box.createVerticalStrut(20));

        themaField = new JTextField();
        unternehmenField = new JTextField();
        zeitraumField = new JTextField();

        content.add(labeled("Thema der Bachelorarbeit", themaField));
        content.add(labeled("Unternehmen", unternehmenField));
        content.add(labeled("Zeitraum", zeitraumField));

        content.add(Box.createVerticalStrut(10));
        content.add(new JLabel("Betreuer auswählen"));

        betreuerBox = loadBetreuer();
        content.add(betreuerBox);

        content.add(Box.createVerticalStrut(30));

        JButton submit = new JButton("Antrag einreichen");
        submit.setBackground(StudentFenster.PRIMARY);
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setMaximumSize(new Dimension(220, 44));

        submit.addActionListener(e -> submitAntrag(parent));

        content.add(submit);

        return new JScrollPane(content);
    }

    private JComboBox<UserLoginResult> loadBetreuer() {
        try {
            List<UserLoginResult> list = BetreuerDAO.findAllBetreuer();
            JComboBox<UserLoginResult> box =
                    new JComboBox<>(list.toArray(new UserLoginResult[0]));

            box.setRenderer((list1, value, index, isSelected, cellHasFocus) -> {
                JLabel l = new JLabel(value.getFullName());
                l.setOpaque(true);
                if (isSelected) l.setBackground(new Color(230, 240, 255));
                return l;
            });
            return box;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Betreuer");
            return new JComboBox<>();
        }
    }

    private void submitAntrag(StudentFenster parent) {
        try {
            UserLoginResult betreuer = (UserLoginResult) betreuerBox.getSelectedItem();

            if (betreuer == null) {
                JOptionPane.showMessageDialog(this, "Bitte Betreuer auswählen");
                return;
            }

            BachelorarbeitDAO.createAntrag(
                    studentId,
                    betreuer.getMnr(),
                    themaField.getText(),
                    unternehmenField.getText(),
                    zeitraumField.getText()
            );

            JOptionPane.showMessageDialog(this, "Antrag erfolgreich eingereicht");
            parent.showPage(StudentFenster.PAGE_DASHBOARD);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler beim Einreichen");
        }
    }

    private JComponent labeled(String label, JTextField field) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(6, 0, 6, 0));

        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));

        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        p.add(l);
        p.add(field);
        return p;
    }
}
