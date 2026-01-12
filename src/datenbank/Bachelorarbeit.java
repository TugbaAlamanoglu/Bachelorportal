package datenbank;

public class Bachelorarbeit {
    private int id;
    private int studentId;
    private int betreuerId;
    private String thema;
    private String unternehmen;
    private String ort;
    private String zeitraumVon;
    private String zeitraumBis;
    private String betreuerUnternehmen;
    private boolean nda;
    private String status;
    
    private String studentVorname;
    private String studentNachname;
    
    public Bachelorarbeit() {}
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public int getBetreuerId() { return betreuerId; }
    public void setBetreuerId(int betreuerId) { this.betreuerId = betreuerId; }
    
    public String getThema() { return thema; }
    public void setThema(String thema) { this.thema = thema; }
    
    public String getUnternehmen() { return unternehmen; }
    public void setUnternehmen(String unternehmen) { this.unternehmen = unternehmen; }
    
    public String getOrt() { return ort; }
    public void setOrt(String ort) { this.ort = ort; }
    
    public String getZeitraumVon() { return zeitraumVon; }
    public void setZeitraumVon(String zeitraumVon) { this.zeitraumVon = zeitraumVon; }
    
    public String getZeitraumBis() { return zeitraumBis; }
    public void setZeitraumBis(String zeitraumBis) { this.zeitraumBis = zeitraumBis; }
    
    public String getBetreuerUnternehmen() { return betreuerUnternehmen; }
    public void setBetreuerUnternehmen(String betreuerUnternehmen) { this.betreuerUnternehmen = betreuerUnternehmen; }
    
    public boolean isNda() { return nda; }
    public void setNda(boolean nda) { this.nda = nda; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getStudentVorname() { return studentVorname; }
    public void setStudentVorname(String studentVorname) { this.studentVorname = studentVorname; }
    
    public String getStudentNachname() { return studentNachname; }
    public void setStudentNachname(String studentNachname) { this.studentNachname = studentNachname; }
}