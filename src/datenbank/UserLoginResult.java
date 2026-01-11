package datenbank;

public class UserLoginResult {

    private final int mnr;
    private final String rolle;

    private final String vorname;
    private final String nachname;
    private final String email;

    public UserLoginResult(int mnr, String rolle, String vorname, String nachname, String email) {
        this.mnr = mnr;
        this.rolle = rolle;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
    }

    public int getMnr() {
        return mnr;
    }

    public String getRolle() {
        return rolle;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        String v = (vorname == null) ? "" : vorname.trim();
        String n = (nachname == null) ? "" : nachname.trim();
        String full = (v + " " + n).trim();
        return full.isEmpty() ? "Unbekannt" : full;
    }
}
