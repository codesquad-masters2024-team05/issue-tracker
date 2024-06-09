package com.CodeSquad.IssueTracker.user.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256Util {

    private static final int SALT_LEN = 16;
    private static final String ENCRYPT_ALGORITHM = "SHA-256";
    private static final int SPLIT_SALT = 0;
    private static final int SPLIT_HASH = 1;
    private static final String PEPPER_STRING = "pepper";


    public static String encrypt(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
            digest.update(salt);
            digest.update(PEPPER_STRING.getBytes(StandardCharsets.UTF_8));
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(ENCRYPT_ALGORITHM + "암호화 방식을 찾을 수 없습니다.");
        }
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LEN];
        random.nextBytes(salt);
        return salt;
    }

    public static String getSaltedHash(String password) {
        byte[] salt = generateSalt();
        String hash = encrypt(password, salt);
        return Base64.getEncoder().encodeToString(salt) + ":" + hash;
    }

    public static boolean verify(String password, String saltedHash) {
        String[] split = saltedHash.split(":");
        byte[] salt = Base64.getDecoder().decode(split[SPLIT_SALT]);
        String hash = encrypt(password, salt);
        return hash.equals(split[SPLIT_HASH]);
    }
}
