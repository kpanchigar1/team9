package trains.of.sheffield;

/**
 * Class to represent an address
 */
public class Address {
    private String houseNumber;
    private String streetName;
    private String city;
    private String postCode;

    /**
     * Constructor to create an address
     * @param houseNumber The house number
     * @param streetName The street name
     * @param city The city
     * @param postCode The post code
     */
    public Address(String houseNumber, String streetName, String city, String postCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.postCode = postCode;
    }

    /**
     * Method to return the address as a string
     * @return The address as a string
     */
    @Override
    public String toString() {
        return houseNumber + " " + streetName + ", " + city + ", " + postCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }
}
