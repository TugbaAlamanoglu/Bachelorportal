package datenbank;

import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {

    public static UserLoginResult login(String email, String passwort) throws Exception {

        String sql = """
            SELECT id, rolle, passwort, vorname, nachname, email
            FROM user
            WHERE email = ?
            LIMIT 1
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String gespeicherterHash = rs.getString("passwort");

                    if (PasswordUtil.pruefePasswort(passwort, gespeicherterHash)) {
                        return new UserLoginResult(
                                rs.getInt("id"),
                                rs.getString("rolle"),
                                rs.getString("vorname"),
                                rs.getString("nachname"),
                                rs.getString("email")
                        );
                    }
                }
            }
            return null;
        }
    }

    // Nur falls du Student-Registrierung verwendest:
    public static int registerStudent(String vorname, String nachname, String email, String passwortKlartext) throws Exception {
        String hash = PasswordUtil.hash(passwortKlartext);

        String sql = """
            INSERT INTO user (vorname, nachname, email, passwort, rolle)
            VALUES (?, ?, ?, ?, 'student')
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, vorname);
            ps.setString(2, nachname);
            ps.setString(3, email);
            ps.setString(4, hash);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }

        throw new Exception("Student konnte nicht erstellt werden (keine ID zurückgegeben).");
    }
}