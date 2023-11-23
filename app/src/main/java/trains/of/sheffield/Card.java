package trains.of.sheffield;
public class Card {
    private String cardName;
    private Integer cardNumber;
    private Integer expiryDate;
    private Integer cvv;

    public Card(String cardName, Integer cardNumber, Integer expiryDate, Integer cvv) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
}
