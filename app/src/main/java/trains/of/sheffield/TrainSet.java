package trains.of.sheffield;
import java.util.List;

public class TrainSet extends Product {
    private String era;
    private Locomotive locomotive;
    private List<RollingStock> rollingStock;
    private List<TrackPack> trackPacks;

    public TrainSet(String productCode, String brandName, String productName, Double price, Gauge gauge, String description, Integer stock) {
        super(productCode, brandName, productName, price, gauge, description, stock);
    }
}
