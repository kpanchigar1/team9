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

    /**
     * Constructor to create a user
     * @param id The user ID
     * @param forename The forename
     * @param surname The surname
     * @param email The email
     * @param passwordHash The password hash
     * @param address The address
     * @param cardDetail The card details
     * @param role The role
     */
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

    /**
     * Method to set the forename
     * @param forename The forename
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * Method to set the surname
     * @param surname The surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Method to set the email
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to set the address
     * @param address The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Method to set the card details
     * @param cardDetail The card details
     */
    public void setCardDetail(Card cardDetail) {
        this.card = cardDetail;
    }

    /**
     * Method to set the password hash
     * @param passwordHash The password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }
}