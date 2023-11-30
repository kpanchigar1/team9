package trains.of.sheffield.views;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Order;
import trains.of.sheffield.Status;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Class to create the order lines window
 */
public class OrderLinesWindow extends JFrame {
    private StaffConfirmedOrders parent;

    /**
     * Constructor to create the order lines window
     * @param order the order to be displayed
     * @param parent the parent window
     * @param fromBasket whether the order is from the basket or not
     */
    public OrderLinesWindow(Order order, StaffConfirmedOrders parent, boolean fromBasket) {
        this.parent = parent;

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

        Container contentPane = getContentPane();
        String[] columnNames = {"Product ID", "Product Name", "Quantity"};
        String[][] orderData = DatabaseOperations.getOrderLines(order.getOrderID());

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2 && order.getStatus().equals(Status.PENDING) && fromBasket) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        tableModel.setColumnIdentifiers(columnNames);
        JTable orderLinesTable = new JTable(tableModel);

        // Create a GenericSpinnerEditor instance
        GenericSpinnerEditor spinnerEditor = new GenericSpinnerEditor(0, true);

        // Set the spinner editor for the "Quantity" column
        orderLinesTable.getColumnModel().getColumn(2).setCellEditor(spinnerEditor);


        if (order.getStatus().equals(Status.PENDING) && fromBasket) {
            for (int i = 0; i < orderData.length; i++) {
                int quantity = Integer.parseInt(orderData[i][2]);
                tableModel.addRow(new Object[]{orderData[i][0], orderData[i][1], quantity});
            }
        }
        else {
            for (int i = 0; i < orderData.length; i++) {
                tableModel.addRow(new Object[]{orderData[i][0], orderData[i][1], orderData[i][2]});
            }
        }

        JScrollPane scrollPane = new JScrollPane(orderLinesTable);

        JButton markAsFulfilled = new JButton("Mark as fulfilled");
        markAsFulfilled.addActionListener(e -> {
            DatabaseOperations.decreaseStockLevels(orderData);
            DatabaseOperations.updateOrderStatus(order.getOrderID(), Status.FULFILLED);
            dispose();
            parent.reload();

        });
        JButton deleteOrder = new JButton("Delete order");
        deleteOrder.addActionListener(e -> {
            DatabaseOperations.deleteOrder(order.getOrderID());
            dispose();


        });
        JButton checkout = new JButton("Checkout");
        checkout.addActionListener(e -> {
            if (CurrentUser.getCardDetail() != null) {
                DatabaseOperations.updateOrderStatus(order.getOrderID(), Status.CONFIRMED);
                dispose();
            } else {
                dispose();
                GUILoader.cardDetailsWindow();
                GUILoader.alertWindow("You must add a card to your account before you can checkout");
            }
            
        });

        JButton confirmChanges = new JButton("Confirm changes");
        confirmChanges.addActionListener(e -> {
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                String productCode = (String) tableModel.getValueAt(row, 0);
                int updatedQuantity = (int) tableModel.getValueAt(row, 2);

                // Now you have the product code and the updated quantity value
                DatabaseOperations.updateOrderLines(order.getOrderID(), productCode, updatedQuantity);
                dispose();
                GUILoader.orderLinesWindow(order, parent, fromBasket);
            }
        });


        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.add(markAsFulfilled);
        if(order.getStatus().equals(Status.CONFIRMED) && !fromBasket) {
            buttonPanel1.add(deleteOrder);
            contentPane.add(buttonPanel1, BorderLayout.SOUTH);
        }

        JPanel buttonPanel2 = new JPanel();
        if (order.getStatus().equals(Status.BLOCKED) && fromBasket) {
            buttonPanel2.add(deleteOrder);
            contentPane.add(buttonPanel2, BorderLayout.SOUTH);
        }

        JPanel buttonPanel3 = new JPanel();
        buttonPanel3.setLayout(new GridLayout(1, 3));
        buttonPanel3.add(confirmChanges);
        buttonPanel3.add(checkout);
        if (order.getStatus().equals(Status.PENDING) && fromBasket) {
            buttonPanel3.add(deleteOrder);
            contentPane.add(buttonPanel3, BorderLayout.SOUTH);
        }

        contentPane.add(scrollPane, BorderLayout.CENTER);
        setSize(400, 400);
        setVisible(true);

        }
    }