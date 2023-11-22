package trains.of.sheffield;
public class TrackPack extends Product{
    private Boolean isExtensionPack;
    private Track track;

    public TrackPack(String productCode, String brandName, String productName, Double price, Gauge gauge, String description, Integer stock) {
        super(productCode, brandName, productName, price, gauge, description, stock);
    }
}
