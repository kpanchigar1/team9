package trains.of.sheffield;
public class Locomotive extends Product {
    private String era;

    public Locomotive(String productCode, String brandName, String productName, Double price, Gauge gauge, String description, Integer stock) {
        super(productCode, brandName, productName, price, gauge, description, stock);
    }
}
