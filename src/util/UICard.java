package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UICard extends JPanel {

    public UICard() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Design.CARD_BG);
        setBorder(new EmptyBorder(18, 18, 18, 18));
    }
}

