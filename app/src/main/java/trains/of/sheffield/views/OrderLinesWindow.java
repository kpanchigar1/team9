package trains.of.sheffield.views;

import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Order;
import trains.of.sheffield.Status;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;

public class OrderLinesWindow extends JFrame {
    private StaffConfirmedOrders parent;
    public OrderLinesWindow(Order order, StaffConfirmedOrders parent, boolean fromBasket) {
        this.parent = parent;

        Container contentPane = getContentPane();
        String[] columnNames = {"Product ID", "Product Name", "Quantity"};
        String[][] orderData = DatabaseOperations.getOrderLines(order.getOrderID());

        JTable orderLinesTable = new JTable(orderData, columnNames);
        JScrollPane scrollPane = new JScrollPane(orderLinesTable);

        JButton markAsFulfilled = new JButton("Mark as fulfilled");
        markAsFulfilled.addActionListener(e -> {
            DatabaseOperations.updateOrderStatus(order.getOrderID(), Status.FULFILLED);
            DatabaseOperations.decreaseStockLevels(orderData);
            dispose();
            parent.reload();

        });
        JButton deleteOrder = new JButton("Delete order");
        deleteOrder.addActionListener(e -> {
            DatabaseOperations.deleteOrder(order.getOrderID());
            dispose();


        });

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new GridLayout(1, 2));
        buttonPanel1.add(markAsFulfilled);
        buttonPanel1.add(deleteOrder);
        System.out.println(order.getStatus());
        if(order.getStatus().equals(Status.CONFIRMED) && !fromBasket) {
            contentPane.add(buttonPanel1, BorderLayout.SOUTH);
        }

        JPanel buttonPanel2 = new JPanel();
        if (order.getStatus().equals(Status.BLOCKED))

        contentPane.add(scrollPane, BorderLayout.CENTER);
        setSize(400, 400);
        setVisible(true);

    }
}
