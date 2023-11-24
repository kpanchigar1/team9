package trains.of.sheffield;
public class User {
    private String id;
    private String forename;
    private String surname;
    private String email;
    private String password;
    private Address address;
    private Card card;
    private Role role;

    public User(String id2, String forename, String surname, String email, String passwordHash, Address address, Card cardDetail, Role role) {
        this.id = id2;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.card = cardDetail;
        this.role = role;
    }

    public User(String string, String string2, String string3, String string4, String string5, int int1,
            Address addressFromDB, Card cardDetailFromDB) {
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
}
