}package datenbank;

import java.math.BigDecimal;

public class Bachelorarbeit {

    private int id;
    private int studentId;
    private Integer betreuerId;

    private String thema;
    private String unternehmen;
    private String zeitraum;

    private String betreuerUnternehmen;
    private String status;

    private BigDecimal note;
    private boolean noteFreigegeben;

    // ---------- Getter / Setter ----------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Integer getBetreuerId() {
        return betreuerId;
    }

    public void setBetreuerId(Integer betreuerId) {
        this.betreuerId = betreuerId;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public String getUnternehmen() {
        return unternehmen;
    }

    public void setUnternehmen(String unternehmen) {
        this.unternehmen = unternehmen;
    }

    public String getZeitraum() {
        return zeitraum;
    }

    public void setZeitraum(String zeitraum) {
        this.zeitraum = zeitraum;
    }

    public String getBetreuerUnternehmen() {
        return betreuerUnternehmen;
    }

    public void setBetreuerUnternehmen(String betreuerUnternehmen) {
        this.betreuerUnternehmen = betreuerUnternehmen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getNote() {
        return note;
    }

    public void setNote(BigDecimal note) {
        this.note = note;
    }

    public boolean isNoteFreigegeben() {
        return noteFreigegeben;
    }

    public void setNoteFreigegeben(boolean noteFreigegeben) {
        this.noteFreigegeben = noteFreigegeben;
    }
}

