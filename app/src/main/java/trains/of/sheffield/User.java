package trains.of.sheffield;

/**
 * User class is used to store the details of a user
 */
public class User {
    private String id;
    private String forename;
    private String surname;
    private String email;
    private String password;
    private Address address;
    private Card card;
    private Role role;

    public User(String id, String forename, String surname, String email, String passwordHash, Address address, Card cardDetail, Role role) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = passwordHash;
        this.address = address;
        this.card = cardDetail;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public Card getCardDetail() {
        return card;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getPasswordHash() {
        return password;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setCardDetail(Card cardDetail) {
        this.card = cardDetail;
    }

    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }
}