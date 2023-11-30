package trains.of.sheffield.views;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Order;
import trains.of.sheffield.Status;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PreviousOrdersPanel extends JFrame {
    private JPanel titlePanel;
    private Container contentPane;
    private JLabel titleLabel;
    private JButton backButton;
    private JTable orderTable;

    /**
     * This class is used to create the previous orders page window which displays all previous orders for the staff users
     * and allows the user to view the order lines.
     */
    public PreviousOrdersPanel() {
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JMenuBar menuBar = new MenuBarPanel();
        JMenuItem signOut = new JMenuItem("Sign Out");
        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentUser.setUser(null);
                dispose();
                GUILoader.loginWindow();
            }
        });
        menuBar.getMenu(0).add(signOut);

        setJMenuBar(menuBar);

        titlePanel = new JPanel();
        titleLabel = new JLabel("Trains of Sheffield - Previous Orders");
        titlePanel.add(titleLabel);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        String[] columnNames = {"Order ID", "Order Date", "Customer Name", "Customer Email", "Postal Address", "Order Status", "Order Total", "Valid Card", "Order Lines"};
        String[][] orderData = DatabaseOperations.getOrdersFromStatus(Status.FULFILLED, false);
        for (int i = 0; i < orderData.length; i++) {
            orderData[i][6] = orderData[i][6].substring(0, orderData[i][6].indexOf(".") + 3);
            orderData[i][8] = "<html><u>View Order Lines</u></html>";
        }
        DefaultTableModel tableModel = new DefaultTableModel(orderData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);


        contentPane.add(scrollPane, BorderLayout.CENTER);

        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detect double-click
                    int selectedRow = orderTable.getSelectedRow();
                    int orderID = Integer.parseInt(orderTable.getValueAt(selectedRow, 0).toString());
                    Order order = DatabaseOperations.getOrderFromId(orderID);
                    // Display order lines using the retrieved orderID
                    GUILoader.orderLinesWindow(order, null, false);
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
    }
}
