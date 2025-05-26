package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESHelper {

    private static String getSecretKey() {
        //String key = System.getProperty("SECRET_KEY");
        String key = ConfigReader.getPropertyValue("SECRET_KEY");
        if (key == null) {
            key = System.getenv("SECRET_KEY");
        }
        if (key == null || key.length() != 16) {
            throw new IllegalArgumentException("SECRET_KEY must be 16 characters and provided via -DSECRET_KEY or env variable.");
        }
        return key;
    }

    public static String encrypt(String plainText) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getSecretKey().getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedText) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getSecretKey().getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decoded);
        return new String(decryptedBytes);
    }
}
