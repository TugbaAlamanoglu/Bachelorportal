package datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BetreuerDAO {

    public static List<UserLoginResult> findAllBetreuer() throws Exception {

        String sql = """
            SELECT id, vorname, nachname, email
            FROM user
            WHERE rolle = 'betreuer'
            ORDER BY nachname
        """;

        List<UserLoginResult> result = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(new UserLoginResult(
                        rs.getInt("id"),
                        "betreuer",
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getString("email")
                ));
            }
        }

        return result;
    }
}
