package trains.of.sheffield;
/**
 * OrderLine class is used to store the product code and quantity of a product
 */
public class OrderLine {
    private int productCode;
    private int quantity;

    public OrderLine(int productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }
}
