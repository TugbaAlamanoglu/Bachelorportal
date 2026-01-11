package util;

import java.awt.Color;

/**
 * Zentrale Farbdefinition für das gesamte UI.
 * Einheitlich für Login, Registrierung, StudentFenster und alle shared Components.
 */
public final class UIColors {

    private UIColors() {
        // Utility-Klasse – keine Instanzen erlaubt
    }

    // ===== Primärfarben =====
    public static final Color PRIMARY   = new Color(0x2563EB); // Hauptblau
    public static final Color SECONDARY = new Color(0x1E40AF); // dunkleres Blau

    // ===== App / Layout =====
    public static final Color BG_APP  = new Color(0xF3F4F6); // App-Hintergrund (hellgrau)
    public static final Color CARD_BG = Color.WHITE;         // Karten / Panels

    // ===== Texte =====
    public static final Color TEXT_DARK  = new Color(0x111827); // Haupttext
    public static final Color TEXT_MUTED = new Color(0x6B7280); // Sekundärtext
    public static final Color TEXT_LIGHT = Color.WHITE;

    // ===== Links =====
    public static final Color LINK = PRIMARY;

    // ===== Input-Felder =====
    public static final Color FIELD_BG    = Color.WHITE;            // Hintergrund
    public static final Color FIELD_TEXT  = new Color(0x111827);    // Eingabetext
    public static final Color PLACEHOLDER = new Color(0x9CA3AF);    // Placeholder

    // ===== Status / Fehler =====
    public static final Color ERROR_BG     = new Color(0xFDECEC);
    public static final Color ERROR_BORDER = new Color(0xF5C2C7);
    public static final Color ERROR_TEXT   = new Color(0xDC2626);

    public static final Color SUCCESS_BG   = new Color(0xECFDF5);
    public static final Color SUCCESS_TEXT = new Color(0x047857);
}

