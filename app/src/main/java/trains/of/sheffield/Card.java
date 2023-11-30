package trains.of.sheffield;

/**
 * Class to represent a card
 */
public class Card {
    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    /**
     * Constructor to create a card
     * @param cardName The card service company
     * @param cardNumber The card number
     * @param expiryDate The expiry date
     * @param cvv The cvv
     */
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
