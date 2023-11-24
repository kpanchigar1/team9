package trains.of.sheffield;

public class CurrentUser {
    private static User user = null;
    public static User getCurrentUser() {
        return user;
    }

    public static void setUser(User newUser) {
        user = newUser;
    }
    public static void clearUser() {
        user = null;
    }

    public static Role getRole() {
        if (user == null) {
            return Role.CUSTOMER;
        }
        return user.getRole();
    }

    public static Card getCardDetail() {
        if (user == null || user.getCardDetail() == null) {
            return null;
        }
        return user.getCardDetail();
    }
    public static String getPasswordHash() {
        if (user == null) {
            return null;
        }
        return user.getPasswordHash();
    }

    public static void setAddress(Address addressFromDB) {
        user.setAddress(addressFromDB);
    }

    public static void setCardDetail(Card cardFromDB) {
        user.setCardDetail(cardFromDB);
    }

    public static void setForename(String forename) {
        user.setForename(forename);
    }

    public static void setSurname(String surname) {
        user.setSurname(surname);
    }

    public static void setEmail(String email) {
        user.setEmail(email);
    }

    public static void setPasswordHash(String passwordHash) {
        user.setPasswordHash(passwordHash);
    }
}
