package trains.of.sheffield.util;

import java.util.UUID;

/**
 * UniqueUserIDGenerator.java
 * Purpose: Generates a unique user ID.
 */
public class UniqueUserIDGenerator {
    /**
     * Generates a unique user ID.
     * @return The unique user ID.
     */
    public static String generateUniqueUserID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}