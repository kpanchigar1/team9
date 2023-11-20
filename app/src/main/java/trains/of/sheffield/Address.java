package trains.of.sheffield;
public class Address {
    private String houseNumber;
    private String streetName;
    private String city;
    private String postCode;

    public static Address getAddressFromDB(String houseNumber, String postCode) {
        return new Address();
    }
}
