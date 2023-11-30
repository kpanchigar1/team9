package trains.of.sheffield;

/**
 * CurrentUser class is used to store the current logged-in user's details
 */
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

    public static String getId() {
        if (user == null) {
            return null;
        }
        return user.getId();
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

    /**
     * Method to get the address of the current user
     * @param addressFromDB The address from the database
     */
    public static void setAddress(Address addressFromDB) {
        user.setAddress(addressFromDB);
    }

    /**
     * Method to get the card details of the current user
     * @param cardFromDB The card details from the database
     */
    public static void setCardDetail(Card cardFromDB) {
        user.setCardDetail(cardFromDB);
    }

    /**
     * Method to set the forename of the current user
     * @param forename The forename
     */
    public static void setForename(String forename) {
        user.setForename(forename);
    }

    /**
     * Method to set the surname of the current user
     * @param surname The surname
     */
    public static void setSurname(String surname) {
        user.setSurname(surname);
    }

    /**
     * Method to set the email of the current user
     * @param email The email
     */
    public static void setEmail(String email) {
        user.setEmail(email);
    }

    /**
     * Method to set the password hash of the current user
     * @param passwordHash The password hash
     */
    public static void setPasswordHash(String passwordHash) {
        user.setPasswordHash(passwordHash);
    }
}
