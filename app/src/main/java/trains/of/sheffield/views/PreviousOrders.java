package trains.of.sheffield.views;

import trains.of.sheffield.*;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// TODO: display previous orders, show the order details when clicked
public class PreviousOrders extends JFrame{
    private JPanel titlePanel;
    private Container contentPane;
    private JLabel titleLabel;
    private JButton backButton;

    public PreviousOrders() {
        super("Trains of Sheffield - Previous Orders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(800, 400);

        JMenuBar menuBar = new MenuBarPanel();

        setJMenuBar(menuBar);

        titlePanel = new JPanel();
        titleLabel = new JLabel("Trains of Sheffield - Previous Orders");
        titlePanel.add(titleLabel);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        // add a JTable to display previous orders
        String[] columnNames = {"Order ID", "Order Date", "Customer Name", "Customer Email", "Postal Address", "Order Status", "Order Total"};
        String[][] orderData = DatabaseOperations.getPreviousOrders();

        JTable orderTable = new JTable(orderData, columnNames);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.staffDashboardWindow();
            }
        });
        contentPane.add(backButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        PreviousOrders previousOrders = new PreviousOrders();
    }
}
