package trains.of.sheffield;
/**
 * Controller class extends Product class and adds a boolean to indicate if the controller is digital or not
 */
public class Controller extends Product{
    private Boolean digital;

    public Controller(String productCode, String brandName, String productName, Double price, Gauge gauge, String description, Integer stock) {
        super(productCode, brandName, productName, price, gauge, description, stock);
    }
}
