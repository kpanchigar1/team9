package trains.of.sheffield;
public class User {
    private String id;
    private String forename;
    private String surname;
    private String email;
    private String password;
    private Address address;
    private CardDetail cardDetail;
    private Role role;

    public User(String id2, String forename, String surname, String email, String passwordHash, Address address2, CardDetail cardDetail2, Role role2) {
        this.id = id2;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address2;
        this.cardDetail = cardDetail2;
        this.role = role2;
    }

    public User(String string, String string2, String string3, String string4, String string5, int int1,
            Address addressFromDB, CardDetail cardDetailFromDB) {
    }
}
