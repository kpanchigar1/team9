package trains.of.sheffield;
public class Controller extends Product{
    private Boolean digital;

    public Controller(String productCode, String brandName, String productName, Double price, Gauge gauge, String description, Integer stock) {
        super(productCode, brandName, productName, price, gauge, description, stock);
    }
}
