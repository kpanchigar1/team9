package trains.of.sheffield;

public class UserStore {
    private static User user = null;
    public static User getUser() {
        return user;
    }

    public static void setUser(User newUser) {
        user = newUser;
    }
}
