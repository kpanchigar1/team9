package trains.of.sheffield.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HashedPasswordGenerator.java
 * Purpose: Generates a hashed password from a given password.
 */
public class HashedPasswordGenerator {
    private static final String SALT = "Team 9 is the best team";

    /**
     * Generates a hashed password from a given password.
     * @param password The password to hash.
     * @return The hashed password.
     */
    public static String hashPassword(char[] password) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Concatenate the salt and password bytes
            byte[] saltedPasswordBytes = concatenateBytes(SALT.getBytes(), new String(password).getBytes());

            // Update the digest with the salted password bytes
            md.update(saltedPasswordBytes);

            // Get the hashed password bytes
            byte[] hashedPasswordBytes = md.digest();

            // Convert the hashed password bytes to a hexadecimal string
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }
            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Concatenates two byte arrays.
     * @param arr1 The first byte array.
     * @param arr2 The second byte array.
     * @return The concatenated byte array.
     */
    public static byte[] concatenateBytes(byte[] arr1, byte[] arr2) {
        byte[] combined = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, combined, 0, arr1.length);
        System.arraycopy(arr2, 0, combined, arr1.length, arr2.length);
        return combined;
    }
}