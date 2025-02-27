package cinema.hasher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasherPBKDF2 {

    public static String hashPassword(String password, byte[] salt,  int iterations, int keyLength) throws Exception {

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean checkPassword(String password, String hashedPassword, byte[] salt, int iterations, int keyLength) throws Exception {
        String newHash = hashPassword(password, salt, iterations, keyLength);
        return newHash.equals(hashedPassword);
    }

    public static byte[] getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte [16];
        sr.nextBytes(salt);
        return salt;
    }
}
