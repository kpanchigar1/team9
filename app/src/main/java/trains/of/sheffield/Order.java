package trains.of.sheffield;
import java.util.List;

public class Order {
    private int orderId;
    private Integer date;
    private Status status;
    private List<OrderLine> orderLines;

    public Order(Integer orderID, String orderDate, int orderStatus, List<OrderLine> orderLines) {
        this.orderId = orderID;
        this.date = Integer.parseInt(orderDate);
        this.status = Status.valueOf(String.valueOf(orderStatus));
        this.orderLines = orderLines;
    }
}
