package trains.of.sheffield;
/**
 * Manager class extends Staff class
 */
public class Manager extends Staff{
    public Manager(String id, String forename, String surname, String email, String password, Address address, Card cardDetail, Role role) {
        super(id, forename, surname, email, password, address, cardDetail, role);
    }
}
