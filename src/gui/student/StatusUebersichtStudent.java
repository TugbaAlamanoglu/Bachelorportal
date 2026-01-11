package gui.student;

/**
 * Zentrale Status-Logik für den Studenten
 * KEIN UI – nur Logik
 */
public class StatusUebersichtStudent {

    public enum AntragStatus {
        NICHT_BEGONNEN,
        EINGEREICHT,
        GENEHMIGT,
        ABGELEHNT
    }

    private AntragStatus antragStatus = AntragStatus.NICHT_BEGONNEN;
    private boolean anmeldungBachelorarbeitEingereicht = false;
    private boolean abgabeEingereicht = false;

    // ---------------- Antrag ----------------

    public AntragStatus getAntragStatus() {
        return antragStatus;
    }

    public void setAntragStatus(AntragStatus status) {
        this.antragStatus = status;
    }

    public boolean isAntragGenehmigt() {
        return antragStatus == AntragStatus.GENEHMIGT;
    }

    public boolean isAntragInBearbeitung() {
        return antragStatus == AntragStatus.EINGEREICHT;
    }

    // ---------------- Anmeldung Bachelorarbeit ----------------

    public boolean isAnmeldungBachelorarbeitMoeglich() {
        return isAntragGenehmigt();
    }

    public boolean isAnmeldungBachelorarbeitEingereicht() {
        return anmeldungBachelorarbeitEingereicht;
    }

    public void setAnmeldungBachelorarbeitEingereicht(boolean value) {
        this.anmeldungBachelorarbeitEingereicht = value;
    }

    // ---------------- Arbeitsstand ----------------

    public boolean isArbeitsstandMoeglich() {
        return anmeldungBachelorarbeitEingereicht;
    }

    // ---------------- Abgabe ----------------

    public boolean isAbgabeMoeglich() {
        return anmeldungBachelorarbeitEingereicht && !abgabeEingereicht;
    }

    public boolean isAbgabeEingereicht() {
        return abgabeEingereicht;
    }

    public void setAbgabeEingereicht(boolean value) {
        this.abgabeEingereicht = value;
    }
}
