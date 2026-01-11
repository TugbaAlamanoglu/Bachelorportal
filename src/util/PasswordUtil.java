package util;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class PasswordUtil {

    private PasswordUtil() {}

    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Hashen des Passworts", e);
        }
    }

    public static boolean pruefePasswort(String eingegebenesPasswort, String gespeicherterHash) {
        return hash(eingegebenesPasswort).equals(gespeicherterHash);
    }
}

