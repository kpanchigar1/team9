package trains.of.sheffield;
/**
 * Staff class extends User class
 */
public class Staff extends User{
    public Staff(String id, String forename, String surname, String email, String password, Address address, Card cardDetail, Role role) {
        super(id, forename, surname, email, password, address, cardDetail, role);
    }
}
