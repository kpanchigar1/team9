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

    public User(String id, String forename, String surname, String email, String passwordHash, Integer role, Address address, CardDetail cardDetail) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.cardDetail = cardDetail;
        this.role = Role.getRole(role);
    }
}
