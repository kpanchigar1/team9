package trains.of.sheffield.views;

import trains.of.sheffield.*;
import trains.of.sheffield.models.DatabaseOperations;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;

public class ViewOrders extends JFrame{

    private JPanel titlePanel;
    private Container contentPane;
    private JLabel titleLabel;
    private JButton backButton;
    private JTable orderTable;
    private JMenuBar menuBar;
    private JScrollPane scrollPane;

    public ViewOrders() {
        // TODO: edit column widths
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        menuBar = new MenuBarPanel();
        setJMenuBar(menuBar);

        titlePanel = new JPanel();
        titleLabel = new JLabel("Trains of Sheffield - Previous Orders");
        titlePanel.add(titleLabel);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        String[] columnNames = {"Order ID", "Order Date", "Customer Name", "Customer Email", "Postal Address", "Order Status", "Order Total", "Valid Card", "Order Lines"};
        String[][] orderData = DatabaseOperations.getOrdersFromStatus(Status.PENDING, true);
        for (int i = 0; i < orderData.length; i++) {
            orderData[i][8] = "<html><a>View Order Lines</a></html>";
        }


        // TODO: Make the table display the order lines when double clicked
        // TODO: Make the order id column look like a hyperlink
        DefaultTableModel tableModel = new DefaultTableModel(orderData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = new JTable(tableModel);
        scrollPane = new JScrollPane(orderTable);


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
        });
        contentPane.add(backButton, BorderLayout.SOUTH);
    }

    private static class HyperlinkCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            label.setText("<html><u>" + value + "</u></html>");
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));


            return label;
        }
    }

    public static void main(String[] args) {
        new PreviousOrdersPanel();
    }
    
}