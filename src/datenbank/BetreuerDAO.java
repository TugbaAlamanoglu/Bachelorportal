package datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class BetreuerDAO {

    /**
     * Liefert alle Betreuer aus der user-Tabelle
     * key   = user.id
     * value = "Vorname Nachname"
     */
    public static Map<Integer, String> findAllBetreuer() throws Exception {

        String sql = """
            SELECT id, vorname, nachname
            FROM user
            WHERE rolle = 'betreuer'
            ORDER BY nachname, vorname
        """;

        Map<Integer, String> result = new LinkedHashMap<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name =
                        rs.getString("vorname") + " " + rs.getString("nachname");

                result.put(id, name);
            }
        }

        return result;
    }
}
