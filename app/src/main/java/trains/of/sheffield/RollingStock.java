package trains.of.sheffield;

/**
 * RollingStock class extends Product class
 */
public class RollingStock extends Product {
    private String era;

    public RollingStock(String productCode, String brandName, String productName, Double price, Gauge gauge, String description, Integer stock) {
        super(productCode, brandName, productName, price, gauge, description, stock);
    }
}
