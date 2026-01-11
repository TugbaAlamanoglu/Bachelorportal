package gui.dekan;

import util.UIColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BachelorseminarView extends JPanel {

    private static final Color CARD_BORDER = new Color(230, 233, 239);
    private static final Color DROP_BORDER = new Color(200, 206, 216);

    public BachelorseminarView() {
        setLayout(new BorderLayout());
        setBackground(UIColors.BG_APP);

        add(buildScroll(), BorderLayout.CENTER);
    }

    private JComponent buildScroll() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(48, 64, 64, 64));

        // ===== Titel =====
        JLabel title = new JLabel("Bachelorseminar Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(UIColors.TEXT_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(title);
        content.add(Box.createVerticalStrut(24));

        // ===== Upload Card =====
        JPanel uploadCard = new JPanel();
        uploadCard.setLayout(new BoxLayout(uploadCard, BoxLayout.Y_AXIS));
        uploadCard.setBackground(Color.WHITE);
        uploadCard.setBorder(new RoundedBorder(16, CARD_BORDER));
        uploadCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        uploadCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 420));
        uploadCard.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel h = new JLabel("Seminarplan hochladen");
        h.setFont(new Font("SansSerif", Font.BOLD, 16));
        h.setForeground(UIColors.TEXT_DARK);

        JLabel info = new JLabel(
                "<html>Laden Sie den zentralen Seminarplan hoch, der für alle Betreuer und Studenten sichtbar ist.</html>"
        );
        info.setFont(new Font("SansSerif", Font.PLAIN, 14));
        info.setForeground(UIColors.TEXT_MUTED);

        uploadCard.add(h);
        uploadCard.add(Box.createVerticalStrut(6));
        uploadCard.add(info);
        uploadCard.add(Box.createVerticalStrut(18));

        // ===== Dropzone =====
        JPanel drop = new JPanel();
        drop.setLayout(new BoxLayout(drop, BoxLayout.Y_AXIS));
        drop.setBackground(new Color(248, 250, 252));
        drop.setBorder(new DashedRoundedBorder(14, DROP_BORDER));
        drop.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        drop.setPreferredSize(new Dimension(600, 160));

        JLabel icon = new JLabel("⬆");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 36));
        icon.setForeground(UIColors.TEXT_MUTED);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel txt = new JLabel("Seminarplan hochladen");
        txt.setFont(new Font("SansSerif", Font.BOLD, 14));
        txt.setForeground(UIColors.TEXT_DARK);
        txt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel types = new JLabel("PDF, DOC, DOCX");
        types.setFont(new Font("SansSerif", Font.PLAIN, 13));
        types.setForeground(UIColors.TEXT_MUTED);
        types.setAlignmentX(Component.CENTER_ALIGNMENT);

        drop.add(Box.createVerticalGlue());
        drop.add(icon);
        drop.add(Box.createVerticalStrut(6));
        drop.add(txt);
        drop.add(Box.createVerticalStrut(4));
        drop.add(types);
        drop.add(Box.createVerticalGlue());

        uploadCard.add(drop);
        uploadCard.add(Box.createVerticalStrut(18));

        // ===== Kommentar =====
        JLabel cLabel = new JLabel("Kommentar (optional)");
        cLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cLabel.setForeground(UIColors.TEXT_DARK);

        JTextArea comment = new JTextArea("Änderungen oder Anmerkungen zum neuen Plan...");
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        comment.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comment.setForeground(UIColors.TEXT_MUTED);
        comment.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane commentScroll = new JScrollPane(comment);
        commentScroll.setBorder(new RoundedBorder(12, CARD_BORDER));
        commentScroll.setPreferredSize(new Dimension(400, 100));

        uploadCard.add(cLabel);
        uploadCard.add(Box.createVerticalStrut(6));
        uploadCard.add(commentScroll);
        uploadCard.add(Box.createVerticalStrut(18));

        // ===== Button =====
        JButton upload = new JButton("Hochladen und veröffentlichen");
        upload.setFont(new Font("SansSerif", Font.BOLD, 14));
        upload.setBackground(UIColors.PRIMARY);
        upload.setForeground(Color.WHITE);
        upload.setFocusPainted(false);
        upload.setBorderPainted(false);
        upload.setPreferredSize(new Dimension(260, 44));
        upload.setMaximumSize(new Dimension(260, 44));

        uploadCard.add(upload);

        content.add(uploadCard);
        content.add(Box.createVerticalStrut(32));

        // ===== Upload-Historie =====
        JPanel history = new JPanel();
        history.setLayout(new BoxLayout(history, BoxLayout.Y_AXIS));
        history.setBackground(Color.WHITE);
        history.setBorder(new RoundedBorder(16, CARD_BORDER));
        history.setAlignmentX(Component.LEFT_ALIGNMENT);
        history.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel h2 = new JLabel("Upload-Historie");
        h2.setFont(new Font("SansSerif", Font.BOLD, 16));
        h2.setForeground(UIColors.TEXT_DARK);

        JLabel empty = new JLabel("Noch keine Uploads vorhanden");
        empty.setFont(new Font("SansSerif", Font.PLAIN, 14));
        empty.setForeground(UIColors.TEXT_MUTED);

        history.add(h2);
        history.add(Box.createVerticalStrut(12));
        history.add(empty);

        content.add(history);

        JScrollPane sp = new JScrollPane(content);
        sp.setBorder(null);
        sp.getViewport().setBackground(UIColors.BG_APP);
        sp.getVerticalScrollBar().setUnitIncrement(16);

        return sp;
    }

    // ===== Rounded Border =====
    static class RoundedBorder extends javax.swing.border.LineBorder {
        private final int r;
        RoundedBorder(int r, Color c) {
            super(c, 1, true);
            this.r = r;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.drawRoundRect(x, y, w - 1, h - 1, r, r);
            g2.dispose();
        }
    }

    // ===== Dashed Border =====
    static class DashedRoundedBorder extends RoundedBorder {
        DashedRoundedBorder(int r, Color c) {
            super(r, c);
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.setStroke(new BasicStroke(
                    1,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND,
                    0,
                    new float[]{6, 6},
                    0
            ));
            g2.drawRoundRect(x, y, w - 1, h - 1, 14, 14);
            g2.dispose();
        }
    }
}
