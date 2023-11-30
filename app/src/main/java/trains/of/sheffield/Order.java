package trains.of.sheffield;
import java.util.List;

/**
 * Order class is used to store the details of an order
 */
public class Order {
    private int orderId;
    private String date;
    private Status status;
    private List<OrderLine> orderLines;

    /**
     * Constructor to create an order
     * @param orderID The order ID
     * @param orderDate The order date
     * @param orderStatus The order status
     * @param orderLines The order lines
     */
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
