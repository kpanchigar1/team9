package trains.of.sheffield.views;

import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Order;
import trains.of.sheffield.Status;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;

public class OrderLinesWindow extends JFrame {
    private StaffConfirmedOrders parent;
    public OrderLinesWindow(Order order, StaffConfirmedOrders parent) {
        super("Order Lines");
        this.parent = parent;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        String[] columnNames = {"Product ID", "Product Name", "Quantity"};
        String[][] orderData = DatabaseOperations.getOrderLines(order.getOrderID());

        JTable orderLinesTable = new JTable(orderData, columnNames);
        JScrollPane scrollPane = new JScrollPane(orderLinesTable);

        JButton markAsFulfilled = new JButton("Mark as fulfilled");
        markAsFulfilled.addActionListener(e -> {
            DatabaseOperations.updateOrderStatus(order.getOrderID(), Status.FULFILLED);
            dispose();
            // TODO: reload StaffConfirmedOrders
            parent.reload();

        });
        JButton deleteOrder = new JButton("Delete order");
        deleteOrder.addActionListener(e -> {
            DatabaseOperations.deleteOrder(order.getOrderID());
            dispose();


        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(markAsFulfilled);
        buttonPanel.add(deleteOrder);
        System.out.println(order.getStatus());
        if(order.getStatus().equals(Status.CONFIRMED)) {
            contentPane.add(buttonPanel, BorderLayout.SOUTH);
        }

        contentPane.add(scrollPane, BorderLayout.CENTER);
        setSize(400, 400);
        setVisible(true);

    }
}
