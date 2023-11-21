package trains.of.sheffield;

public class CurrentUser {
    private static User user = null;
    public static User getCurrentUser() {
        return user;
    }

    public static void setUser(User newUser) {
        user = newUser;
    }
}
