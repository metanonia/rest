package com.metanonia.rest.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;

public class CryptoUtils {
    public static String Sha256(BigInteger message) {
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.reset();
            sh.update(message.toByteArray());
            byte[] byteData = sh.digest();

            return new String(byteData, "UTF-8");
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String Sha256(String message) {
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.reset();
            sh.update(message.getBytes());
            byte[] byteData = sh.digest();

            return new String(byteData, "UTF-8");
        }
        catch (Exception e) {
            return null;
        }
    }
}
