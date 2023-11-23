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

    public static CardDetail getCardDetail() {
        if (user == null) {
            return null;
        }
        return user.getCardDetail();
    }
}
