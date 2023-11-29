package trains.of.sheffield;

/**
 * Class to represent an address
 */
public class Address {
    private String houseNumber;
    private String streetName;
    private String city;
    private String postCode;

    public Address(String houseNumber, String streetName, String city, String postCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.postCode = postCode;
    }

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
