package trains.of.sheffield.views;

import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Order;
import trains.of.sheffield.Status;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PreviousOrdersPanel extends JFrame {
    private JPanel titlePanel;
    private Container contentPane;
    private JLabel titleLabel;
    private JButton backButton;
    private JTable orderTable;

    public PreviousOrdersPanel() {
        // TODO: edit column widths
        super("Trains of Sheffield - Previous Orders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(1600, 400);

        JMenuBar menuBar = new MenuBarPanel();
        setJMenuBar(menuBar);

        titlePanel = new JPanel();
        titleLabel = new JLabel("Trains of Sheffield - Previous Orders");
        titlePanel.add(titleLabel);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        String[] columnNames = {"Order ID", "Order Date", "Customer Name", "Customer Email", "Postal Address", "Order Status", "Order Total", "Valid Card"};
        String[][] orderData = DatabaseOperations.getOrdersFromStatus(Status.FULFILLED);


        // TODO: Make the table display the order lines when double clicked
        // TODO: Make the order id column look like a hyperlink
        DefaultTableModel tableModel = new DefaultTableModel(orderData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);

        //  TODO: Make the table non editable


        contentPane.add(scrollPane, BorderLayout.CENTER);

        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detect double-click
                    int selectedRow = orderTable.getSelectedRow();
                    int orderID = Integer.parseInt(orderTable.getValueAt(selectedRow, 0).toString());
                    Order order = DatabaseOperations.getOrderFromId(orderID);
                    // Display order lines using the retrieved orderID
                    GUILoader.orderLinesWindow(order, null);
                }
            }
        });

        scrollPane = new JScrollPane(orderTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            GUILoader.staffDashboardWindow();
        });
        contentPane.add(backButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PreviousOrdersPanel();
    }
}