package trains.of.sheffield;
/**
 * Locomotive class extends Product class and adds a String to indicate the era of the locomotive
 */
public class Locomotive extends Product {
    private String era;

    public Locomotive(String productCode, String brandName, String productName, Double price, Gauge gauge, String description, Integer stock, String era) {
        super(productCode, brandName, productName, price, gauge, description, stock);
        this.era = era;
    }
}
