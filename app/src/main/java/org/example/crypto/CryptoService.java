package org.example.crypto;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.Arrays;

public class CryptoService {
    private static final int SALT_LENGTH = 16;
    private static final int IV_LENGTH = 12;
    private static final int ITERATIONS = 65_536;
    private static final int KEY_LENGTH = 256;

    public byte[] encrypt(String plaintext, String masterPassword) {
        try {
            byte[] salt = randomBytes(SALT_LENGTH);
            SecretKey key = deriveKey(masterPassword, salt);

            byte[] iv = randomBytes(IV_LENGTH);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));

            byte[] cipherText = cipher.doFinal(plaintext.getBytes());

            return concat(salt, iv, cipherText);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public String decrypt(byte[] encrypted, String masterPassword) {
        try {
            byte[] salt = Arrays.copyOfRange(encrypted, 0, SALT_LENGTH);
            byte[] iv = Arrays.copyOfRange(encrypted, SALT_LENGTH, SALT_LENGTH + IV_LENGTH);
            byte[] cipherText = Arrays.copyOfRange(encrypted, SALT_LENGTH + IV_LENGTH, encrypted.length);

            SecretKey key = deriveKey(masterPassword, salt);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));

            byte[] plainBytes = cipher.doFinal(cipherText);
            return new String(plainBytes);
        } catch (Exception e) {
            throw new RuntimeException("Invalid master password or corrupted vault");
        }
    }

    private SecretKey deriveKey(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private byte[] randomBytes(int length) {
        byte[] b = new byte[length];
        new SecureRandom().nextBytes(b);
        return b;
    }

    private byte[] concat(byte[]... arrays) {
        int total = 0;
        for (byte[] a : arrays) total += a.length;

        byte[] result = new byte[total];
        int pos = 0;
        for (byte[] a : arrays) {
            System.arraycopy(a, 0, result, pos, a.length);
            pos += a.length;
        }
        return result;
    }
}
