package trains.of.sheffield;
public class Card {
    private String cardName;
    private String cardNumber;
    private Integer expiryDate;
    private Integer cvv;

    public Card(String cardName, String cardNumber, Integer expiryDate, Integer cvv) {
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

    public Integer getExpiryDate() {
        return expiryDate;
    }

    public Integer getCvv() {
        return cvv;
    }
}
