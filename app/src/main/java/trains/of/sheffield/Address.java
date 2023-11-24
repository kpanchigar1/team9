package trains.of.sheffield;
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
}
