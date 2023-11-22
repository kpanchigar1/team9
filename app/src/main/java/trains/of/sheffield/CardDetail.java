package trains.of.sheffield;
public class CardDetail {
    private String cardName;
    private Integer cardNumber;
    private Integer expiryDate;
    private Integer cvv;

    public CardDetail(String cardName, Integer cardNumber, Integer expiryDate, Integer cvv) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
}
