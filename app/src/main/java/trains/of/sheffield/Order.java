package trains.of.sheffield;
import java.util.List;

public class Order {
    private int orderId;
    private String date;
    private Status status;
    private List<OrderLine> orderLines;

    public Order(Integer orderID, String orderDate, int orderStatus, List<OrderLine> orderLines) {
        this.orderId = orderID;
        this.date = orderDate;
        this.status = Status.values()[orderStatus];
        this.orderLines = orderLines;
    }

    public int getOrderID() {
        return orderId;
    }

    public Status getStatus() {
        return status;
    }
}
