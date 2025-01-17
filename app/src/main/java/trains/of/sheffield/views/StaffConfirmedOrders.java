package trains.of.sheffield.views;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Order;
import trains.of.sheffield.Status;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Class to create the staff confirmed orders window
 */

public class StaffConfirmedOrders extends JFrame {
    private JPanel titlePanel;
    private Container contentPane;
    private JLabel titleLabel;
    private JButton backButton;
    private JTable orderTable;
    private static int firstOrderID;

    /**
     * Constructor to create the staff confirmed orders window
     */
    public StaffConfirmedOrders(){
        super("Trains of Sheffield - Confirmed Orders");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(1600, 400);

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
        titleLabel = new JLabel("Trains of Sheffield - Confirmed Orders");
        titlePanel.add(titleLabel);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        String[] columnNames = {"Order ID", "Order Date", "Customer Name", "Customer Email", "Postal Address", "Order Status", "Order Total", "Valid Card", "Order Lines"};
        String[][] orderData = DatabaseOperations.getOrdersFromStatus(Status.CONFIRMED, false);
        for(int i = 0; i < orderData.length; i++){
            orderData[i][6] = orderData[i][6].substring(0, orderData[i][6].indexOf(".") + 3);
            orderData[i][8] = "<html><u>View Order Lines</u></html>";
        }
        if(orderData.length > 0) {
            firstOrderID = Integer.parseInt(orderData[0][0]);
        }

        DefaultTableModel tableModel = new DefaultTableModel(orderData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);

        orderTable.getColumnModel().getColumn(8).setCellRenderer(new HyperlinkCellRenderer());


        contentPane.add(scrollPane, BorderLayout.CENTER);
        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detect double-click
                    int selectedRow = orderTable.getSelectedRow();
                    int orderID = Integer.parseInt(orderTable.getValueAt(selectedRow, 0).toString());
                    if (orderID == firstOrderID) {
                        Order order = DatabaseOperations.getOrderFromId(orderID);
                        GUILoader.orderLinesWindow(order, StaffConfirmedOrders.this, false);
                    }
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

    /**
     * Class to create a hyperlink in a JTable
     */
    static class HyperlinkCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (column == 8 && row == 0) {
                label.setText("<html><u>" + value + "</u></html>");
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            return label;
        }
    }

    /**
     * Method to reload the window
     */
    public void reload() {
        dispose();
        new StaffConfirmedOrders().setVisible(true);
    }
}
