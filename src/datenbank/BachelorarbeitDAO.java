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
            String zeitraum
    ) throws Exception {

        String sql = """
            INSERT INTO bachelorarbeit
            (student_id, betreuer_id, thema, unternehmen, zeitraum, status)
            VALUES (?, ?, ?, ?, ?, 'eingereicht')
        """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, betreuerId);
            ps.setString(3, thema);
            ps.setString(4, unternehmen);
            ps.setString(5, zeitraum);
            ps.executeUpdate();
        }
    }

    public static List<Bachelorarbeit> findForBetreuer(int betreuerId) throws Exception {

        String sql = """
            SELECT id, student_id, thema
            FROM bachelorarbeit
            WHERE betreuer_id = ?
              AND status = 'eingereicht'
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
                    list.add(b);
                }
            }
        }
        return list;
    }
}
