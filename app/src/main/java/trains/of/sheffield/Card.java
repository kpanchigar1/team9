package trains.of.sheffield;
public class Card {
    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public Card(String cardName, String cardNumber, String expiryDate, String cvv) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }
}
