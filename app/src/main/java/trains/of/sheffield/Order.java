package trains.of.sheffield;
import java.util.List;

public class Order {
    private Integer orderId;
    private Integer date;
    private Status status;
    private Integer userId;
    private List<OrderLine> orderLines;
}
