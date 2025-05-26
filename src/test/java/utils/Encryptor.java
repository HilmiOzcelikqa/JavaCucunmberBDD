package utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Encryptor {
    public static void main(String[] args) throws Exception {
        // Şifrelemek istediğin düz veriler
        String plainUsername = "";
        String plainPassword = "";

        // Şifreleme
        String encryptedUsername = AESHelper.encrypt(plainUsername);
        String encryptedPassword = AESHelper.encrypt(plainPassword);

        // config.properties dosyasını güncelle
        String path = "src/test/resources/config/config.properties"; // ihtiyaca göre değiştir

        // Var olanları yükle (diğer config değerleri silinmesin)
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            props.load(input);
        }

        // Güncelle
        props.setProperty("username", encryptedUsername);
        props.setProperty("password", encryptedPassword);

        // Kaydet
        try (OutputStream output = new FileOutputStream(path)) {
            props.store(output, "Encrypted credentials updated by Encryptor");
        }

        System.out.println("✅ Encrypted credentials written to config.properties");
    }
}

