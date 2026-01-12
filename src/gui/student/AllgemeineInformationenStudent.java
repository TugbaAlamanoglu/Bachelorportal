package gui.student;

import datenbank.BachelorarbeitDAO;
import datenbank.BetreuerDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllgemeineInformationenStudent extends JPanel {

    private static final Color BG = StudentFenster.BG;
    private static final Color BORDER = StudentFenster.BORDER;
    private static final Color PRIMARY = StudentFenster.PRIMARY;
    private static final Color TEXT_DARK = StudentFenster.TEXT_DARK;
    private static final Color TEXT_MUTED = StudentFenster.TEXT_MUTED;

    private static final int CONTENT_MAX_W = 1080;

    private final StudentFenster parent;
    private final int mnr;
    private final String name;

    private JTextField tfTitel;
    private JTextField tfUnternehmen;
    private JTextField tfOrt;
    private JTextField tfVon;
    private JTextField tfBis;
    private JTextField tfBetreuerU;

    private JComboBox<String> cbBetreuer;
    private List<Integer> betreuerIds = new ArrayList<>();

    private JRadioButton rbNdaJa;
    private JRadioButton rbNdaNein;
    private JLabel lblNdaFile;
    
    private boolean antragEingereicht = false;
    private File ndaFile = null;

    public AllgemeineInformationenStudent(StudentFenster parent, int mnr, String name, String email) {
        this.parent = parent;
        this.mnr = mnr;
        this.name = name;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildCenterScroll(), BorderLayout.CENTER);
        add(buildBottomBar(), BorderLayout.SOUTH);
    }

    private void loadExistingAntrag() {
        try {
            datenbank.Bachelorarbeit ba = BachelorarbeitDAO.findForStudent(mnr);
            if (ba != null) {
                tfTitel.setText(ba.getThema());
                tfUnternehmen.setText(ba.getUnternehmen());
                tfOrt.setText(ba.getOrt());
                tfVon.setText(ba.getZeitraumVon());
                tfBis.setText(ba.getZeitraumBis());
                tfBetreuerU.setText(ba.getBetreuerUnternehmen());
                
                if (cbBetreuer != null) {
                    for (int i = 0; i < betreuerIds.size(); i++) {
                        if (betreuerIds.get(i) == ba.getBetreuerId()) {
                            cbBetreuer.setSelectedIndex(i + 1);
                            break;
                        }
                    }
                }
                
                if (rbNdaJa != null && rbNdaNein != null) {
                    if (ba.isNda()) {
                        rbNdaJa.setSelected(true);
                    } else {
                        rbNdaNein.setSelected(true);
                    }
                }
                
                antragEingereicht = true;
                if (tfTitel != null) setFieldsEditable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFieldsEditable(boolean editable) {
        tfTitel.setEditable(editable);
        tfUnternehmen.setEditable(editable);
        tfOrt.setEditable(editable);
        tfVon.setEditable(editable);
        tfBis.setEditable(editable);
        tfBetreuerU.setEditable(editable);
        cbBetreuer.setEnabled(editable);
        rbNdaJa.setEnabled(editable);
        rbNdaNein.setEnabled(editable);
    }

    private JComponent buildCenterScroll() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);

        JPanel inner = new JPanel();
        inner.setBackground(BG);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBorder(new EmptyBorder(28, 28, 24, 28));
        inner.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel h1 = new JLabel("Allgemeine Informationen");
        h1.setFont(new Font("SansSerif", Font.BOLD, 26));
        h1.setForeground(TEXT_DARK);
        inner.add(h1);

        inner.add(Box.createVerticalStrut(6));

        JLabel h2 = new JLabel("Grunddaten zu Ihrer Bachelorarbeit");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        h2.setForeground(TEXT_MUTED);
        inner.add(h2);

        inner.add(Box.createVerticalStrut(18));

        inner.add(cardThema());
        inner.add(Box.createVerticalStrut(16));
        inner.add(cardUnternehmenOrt());
        inner.add(Box.createVerticalStrut(16));
        inner.add(cardZeitraum());
        inner.add(Box.createVerticalStrut(16));
        inner.add(cardBetreuer());
        inner.add(Box.createVerticalStrut(16));
        inner.add(cardNda());

        JPanel fixed = new JPanel(new BorderLayout());
        fixed.setBackground(BG);
        fixed.add(inner, BorderLayout.CENTER);
        fixed.setMaximumSize(new Dimension(CONTENT_MAX_W, Integer.MAX_VALUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        outer.add(fixed, gbc);

        JScrollPane sp = new JScrollPane(outer);
        sp.setBorder(null);
        sp.getViewport().setBackground(BG);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        
        SwingUtilities.invokeLater(this::loadExistingAntrag);
        
        return sp;
    }

    private JComponent cardThema() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("Thema der Bachelorarbeit"));
        body.add(Box.createVerticalStrut(14));
        body.add(fieldLabel("Titel / Thema"));
        body.add(Box.createVerticalStrut(8));

        tfTitel = input("");
        body.add(tfTitel);
        return card;
    }

    private JComponent cardUnternehmenOrt() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("Unternehmen & Ort"));
        body.add(Box.createVerticalStrut(14));

        JPanel grid = twoColGrid();
        GridBagConstraints g = baseGbc();

        g.gridx = 0; grid.add(fieldLabel("Unternehmen"), g);
        g.gridx = 1; grid.add(fieldLabel("Ort"), g);

        g.gridy = 1;
        tfUnternehmen = input("");
        tfOrt = input("");
        g.gridx = 0; grid.add(tfUnternehmen, g);
        g.gridx = 1; grid.add(tfOrt, g);

        body.add(grid);
        return card;
    }

    private JComponent cardZeitraum() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("Zeitraum"));
        body.add(Box.createVerticalStrut(14));

        JPanel grid = twoColGrid();
        GridBagConstraints g = baseGbc();

        g.gridx = 0; grid.add(fieldLabel("Von"), g);
        g.gridx = 1; grid.add(fieldLabel("Bis"), g);

        g.gridy = 1;
        tfVon = input("");
        tfBis = input("");
        g.gridx = 0; grid.add(tfVon, g);
        g.gridx = 1; grid.add(tfBis, g);

        body.add(grid);
        return card;
    }

    private JComponent cardBetreuer() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("Betreuer"));
        body.add(Box.createVerticalStrut(14));
        body.add(fieldLabel("Betreuer an der HFT"));
        body.add(Box.createVerticalStrut(8));

        cbBetreuer = new JComboBox<>();
        cbBetreuer.addItem("Bitte wählen...");

        try {
            Map<Integer, String> map = BetreuerDAO.findAllBetreuer();
            map.forEach((id, name) -> {
                betreuerIds.add(id);
                cbBetreuer.addItem(name);
            });
        } catch (Exception e) {
            e.printStackTrace();
            cbBetreuer.addItem("Fehler beim Laden");
        }

        cbBetreuer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        body.add(cbBetreuer);

        body.add(Box.createVerticalStrut(14));
        body.add(fieldLabel("Betreuer im Unternehmen"));
        body.add(Box.createVerticalStrut(8));

        tfBetreuerU = input("");
        body.add(tfBetreuerU);

        return card;
    }

    private JComponent cardNda() {
        JPanel card = cardBase();
        JPanel body = cardBody(card);

        body.add(cardTitle("Geheimhaltungsvereinbarung (NDA)"));
        body.add(Box.createVerticalStrut(10));

        rbNdaJa = new JRadioButton("Ja");
        rbNdaNein = new JRadioButton("Nein");
        rbNdaJa.setOpaque(false);
        rbNdaNein.setOpaque(false);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbNdaJa);
        bg.add(rbNdaNein);

        body.add(rbNdaJa);
        body.add(rbNdaNein);

        body.add(Box.createVerticalStrut(10));

        JButton choose = new JButton("Datei auswählen");
        choose.addActionListener(e -> choosePdf());
        body.add(choose);

        lblNdaFile = new JLabel("Keine Datei ausgewählt");
        lblNdaFile.setForeground(TEXT_MUTED);
        body.add(lblNdaFile);

        return card;
    }

    private JComponent buildBottomBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG);
        bar.setBorder(new EmptyBorder(12, 28, 12, 28));

        JButton back = new JButton("← Zurück");
        back.addActionListener(e -> parent.showPage(StudentFenster.PAGE_DASHBOARD));

        JButton submit = new JButton(antragEingereicht ? "Antrag bereits eingereicht" : "Antrag einreichen");
        submit.setBackground(antragEingereicht ? Color.GRAY : PRIMARY);
        submit.setForeground(Color.WHITE);
        submit.setBorderPainted(false);
        submit.setEnabled(!antragEingereicht);
        submit.addActionListener(e -> onSave());

        bar.add(back, BorderLayout.WEST);
        bar.add(submit, BorderLayout.CENTER);
        return bar;
    }

    private void choosePdf() {
        if (antragEingereicht) {
            JOptionPane.showMessageDialog(this, "Antrag bereits eingereicht - keine Änderungen möglich.");
            return;
        }
        
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ndaFile = fc.getSelectedFile();
            lblNdaFile.setText("Ausgewählt: " + ndaFile.getName());
        }
    }

    private void onSave() {
        if (antragEingereicht) {
            JOptionPane.showMessageDialog(this, "Antrag bereits eingereicht.");
            return;
        }
        
        if (cbBetreuer.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Bitte Betreuer auswählen.");
            return;
        }
        
        if (tfTitel.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte Thema eingeben.");
            return;
        }

        int betreuerId = betreuerIds.get(cbBetreuer.getSelectedIndex() - 1);
        boolean nda = rbNdaJa.isSelected();

        try {
            BachelorarbeitDAO.createAntrag(
                mnr,
                betreuerId,
                tfTitel.getText().trim(),
                tfUnternehmen.getText().trim(),
                tfOrt.getText().trim(),
                tfVon.getText().trim(),
                tfBis.getText().trim(),
                tfBetreuerU.getText().trim(),
                nda
            );
            
            antragEingereicht = true;
            setFieldsEditable(false);
            
            JButton submitBtn = (JButton) ((JPanel) getComponent(1)).getComponent(1);
            submitBtn.setText("Antrag bereits eingereicht");
            submitBtn.setBackground(Color.GRAY);
            submitBtn.setEnabled(false);
            
            JOptionPane.showMessageDialog(this, 
                "Antrag erfolgreich eingereicht!\n" +
                "Status: Wartet auf Genehmigung durch " + cbBetreuer.getSelectedItem());
            
            if (parent != null) {
                parent.showPage(StudentFenster.PAGE_DASHBOARD);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Fehler beim Speichern: " + e.getMessage(), 
                "Fehler", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel cardBase() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new DashboardStudent.RoundedBorder(16, BORDER));
        return card;
    }

    private JPanel cardBody(JPanel card) {
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(18, 18, 18, 18));
        card.add(body);
        return body;
    }

    private JLabel cardTitle(String s) {
        JLabel l = new JLabel(s);
        l.setFont(new Font("SansSerif", Font.BOLD, 18));
        l.setForeground(TEXT_DARK);
        return l;
    }

    private JLabel fieldLabel(String s) {
        JLabel l = new JLabel(s);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(TEXT_DARK);
        return l;
    }

    private JTextField input(String v) {
        JTextField tf = new JTextField(v);
        tf.setBackground(new Color(245, 246, 248));
        tf.setBorder(new EmptyBorder(12, 12, 12, 12));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return tf;
    }

    private JPanel twoColGrid() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        return p;
    }

    private GridBagConstraints baseGbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(0, 0, 8, 16);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        return g;
    }
    
    public boolean isAntragEingereicht() {
        return antragEingereicht;
    }
}