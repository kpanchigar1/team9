package trains.of.sheffield.util;

import java.util.UUID;

public class UniqueUserIDGenerator {
    public static String generateUniqueUserID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}