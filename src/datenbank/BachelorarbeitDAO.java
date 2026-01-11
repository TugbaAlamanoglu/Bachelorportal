package datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, betreuerId);
            ps.setString(3, thema);
            ps.setString(4, unternehmen);
            ps.setString(5, zeitraum);

            ps.executeUpdate();
        }
    }
}
