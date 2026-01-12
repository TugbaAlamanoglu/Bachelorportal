package datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BachelorarbeitDAO {

    public static void createAntrag(
            int studentId,
            int betreuerId,
            String thema,
            String unternehmen,
            String ort,
            String zeitraumVon,
            String zeitraumBis,
            String betreuerUnternehmen,
            boolean nda
    ) throws Exception {

        String sql = """
            INSERT INTO bachelorarbeit
            (student_id, betreuer_id, thema, unternehmen, ort, 
             zeitraum_von, zeitraum_bis, betreuer_unternehmen, nda, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'eingereicht')
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, betreuerId);
            ps.setString(3, thema);
            ps.setString(4, unternehmen);
            ps.setString(5, ort);
            ps.setString(6, zeitraumVon);
            ps.setString(7, zeitraumBis);
            ps.setString(8, betreuerUnternehmen);
            ps.setBoolean(9, nda);
            ps.executeUpdate();
        }
    }

    public static List<Bachelorarbeit> findForBetreuer(int betreuerId) throws Exception {
        String sql = """
            SELECT ba.id, ba.student_id, ba.thema, ba.unternehmen, ba.ort,
                   ba.zeitraum_von, ba.zeitraum_bis, ba.betreuer_unternehmen,
                   ba.nda, ba.status, u.vorname, u.nachname
            FROM bachelorarbeit ba
            JOIN user u ON ba.student_id = u.id
            WHERE ba.betreuer_id = ?
              AND ba.status = 'eingereicht'
        """;

        List<Bachelorarbeit> list = new ArrayList<>();

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, betreuerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Bachelorarbeit b = new Bachelorarbeit();
                    b.setId(rs.getInt("id"));
                    b.setStudentId(rs.getInt("student_id"));
                    b.setThema(rs.getString("thema"));
                    b.setUnternehmen(rs.getString("unternehmen"));
                    b.setOrt(rs.getString("ort"));
                    b.setZeitraumVon(rs.getString("zeitraum_von"));
                    b.setZeitraumBis(rs.getString("zeitraum_bis"));
                    b.setBetreuerUnternehmen(rs.getString("betreuer_unternehmen"));
                    b.setNda(rs.getBoolean("nda"));
                    b.setStatus(rs.getString("status"));
                    b.setStudentVorname(rs.getString("vorname"));
                    b.setStudentNachname(rs.getString("nachname"));
                    list.add(b);
                }
            }
        }
        return list;
    }
    
    public static int countOpenAntraegeForBetreuer(int betreuerId) throws Exception {
        String sql = "SELECT COUNT(*) FROM bachelorarbeit WHERE betreuer_id = ? AND status = 'eingereicht'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, betreuerId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    public static Bachelorarbeit findForStudent(int studentId) throws Exception {
        String sql = """
            SELECT * FROM bachelorarbeit 
            WHERE student_id = ? 
            ORDER BY id DESC 
            LIMIT 1
        """;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Bachelorarbeit ba = new Bachelorarbeit();
                    ba.setId(rs.getInt("id"));
                    ba.setStudentId(rs.getInt("student_id"));
                    ba.setBetreuerId(rs.getInt("betreuer_id"));
                    ba.setThema(rs.getString("thema"));
                    ba.setUnternehmen(rs.getString("unternehmen"));
                    ba.setOrt(rs.getString("ort"));
                    ba.setZeitraumVon(rs.getString("zeitraum_von"));
                    ba.setZeitraumBis(rs.getString("zeitraum_bis"));
                    ba.setBetreuerUnternehmen(rs.getString("betreuer_unternehmen"));
                    ba.setNda(rs.getBoolean("nda"));
                    ba.setStatus(rs.getString("status"));
                    
                    return ba;
                }
            }
        }
        return null;
    }
    
    public static void updateStatus(int id, String status) throws Exception {
        String sql = "UPDATE bachelorarbeit SET status = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}