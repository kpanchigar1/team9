package trains.of.sheffield.views;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Order;
import trains.of.sheffield.Status;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

public class OrderLinesWindow extends JFrame {
    private StaffConfirmedOrders parent;
    public OrderLinesWindow(Order order, StaffConfirmedOrders parent, boolean fromBasket) {
        this.parent = parent;
        // TODO: spinner is not storing values properly

        Container contentPane = getContentPane();
        String[] columnNames = {"Product ID", "Product Name", "Quantity"};
        String[][] orderData = DatabaseOperations.getOrderLines(order.getOrderID());

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2 && order.getStatus().equals(Status.PENDING) && fromBasket) {
                    return true;
                }
                else {
                    return false;
                }
            }
        };

        tableModel.setColumnIdentifiers(columnNames);
        JTable orderLinesTable = new JTable(tableModel);

        if (order.getStatus().equals(Status.PENDING) && fromBasket) {
            for (int i = 0; i < orderData.length; i++) {
                int quantity = Integer.parseInt(orderData[i][2]);
                JSpinner spinner = new JSpinner(new SpinnerNumberModel(quantity, 0, Integer.MAX_VALUE, 1));
                tableModel.addRow(new Object[]{orderData[i][0], orderData[i][1], quantity});

                TableColumn quantityColumn = orderLinesTable.getColumnModel().getColumn(2);
                quantityColumn.setCellRenderer(new GenericSpinnerRenderer<>());

                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    int originalValue = Integer.parseInt(tableModel.getValueAt(row, 2).toString());
                    quantityColumn.setCellEditor(new GenericSpinnerEditor<>(originalValue, new SpinnerNumberModel(originalValue, 0, Integer.MAX_VALUE, 1), false));
                }
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
        JButton checkout = new JButton("Checkout");
        checkout.addActionListener(e -> {
            if (CurrentUser.getCardDetail() != null) {
                DatabaseOperations.updateOrderStatus(order.getOrderID(), Status.CONFIRMED);
                dispose();
                GUILoader.alertWindow("Order confirmed");
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
                // get spinner value
                int stock = 0;
                TableCellEditor editor = orderLinesTable.getCellEditor(row, 2);
                if (editor instanceof GenericSpinnerEditor) {
                    JSpinner spinner = ((GenericSpinnerEditor<?>) editor).getSpinner();
                    stock = (int) spinner.getValue();
                    System.out.println(stock);
                    // Now you have the product code and the current quantity value

                DatabaseOperations.updateOrderLines(order.getOrderID(), productCode, stock);
                }

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new GridLayout(1, 2));
        buttonPanel1.add(markAsFulfilled);
        buttonPanel1.add(deleteOrder);
        if(order.getStatus().equals(Status.CONFIRMED) && !fromBasket) {
            contentPane.add(buttonPanel1, BorderLayout.SOUTH);
        }

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridLayout(1, 1));
        buttonPanel2.add(deleteOrder);
        if (order.getStatus().equals(Status.BLOCKED) && fromBasket) {
            contentPane.add(buttonPanel2, BorderLayout.SOUTH);
        }

        JPanel buttonPanel3 = new JPanel();
        buttonPanel3.setLayout(new GridLayout(1, 3));
        buttonPanel3.add(deleteOrder);
        buttonPanel3.add(confirmChanges);
        buttonPanel3.add(checkout);
        if (order.getStatus().equals(Status.PENDING) && fromBasket) {
            contentPane.add(buttonPanel3, BorderLayout.SOUTH);
        }

        contentPane.add(scrollPane, BorderLayout.CENTER);
        setSize(400, 400);
        setVisible(true);

        }
    });
    }
}