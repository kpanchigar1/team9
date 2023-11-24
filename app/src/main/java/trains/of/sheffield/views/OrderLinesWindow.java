package trains.of.sheffield.views;

import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;

public class OrderLinesWindow extends JFrame {
    public OrderLinesWindow(int orderID) {
        //TODO: make it cleaner
        //TODO: quantity doesnt work
        super("Order Lines");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        String[] columnNames = {"Order ID", "Product ID", "Product Name", "Quantity"};
        String[][] orderData = DatabaseOperations.getOrderLines(orderID);

        JTable orderLinesTable = new JTable(orderData, columnNames);
        JScrollPane scrollPane = new JScrollPane(orderLinesTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        setSize(800, 400);
        setVisible(true);
    }
}
