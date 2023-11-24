package trains.of.sheffield.views;

import trains.of.sheffield.*;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreviousOrders extends JFrame {
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
        String[] columnNames = {"Order ID", "Order Date", "Customer Name", "Customer Email", "Postal Address", "Order Status", "Order Total", "View Order Lines"};
        String[][] orderData = DatabaseOperations.getPreviousOrders();

        // Create a DefaultTableModel
        DefaultTableModel tableModel = new DefaultTableModel(orderData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        // Create JTable with the custom model
        JTable orderTable = new JTable(tableModel);

        // TODO: add a button to view order lines

        // Create a button column for viewing order lines
        ButtonColumn buttonColumn = new ButtonColumn(orderTable, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the order ID from the selected row
                int selectedRow = orderTable.getSelectedRow();
                int orderID = Integer.parseInt(orderTable.getValueAt(selectedRow, 0).toString());
                System.out.println("HELLO");
                // Get the order lines for the selected order
                String[] columnNames = {"Order Line ID", "Product Name", "Quantity", "Price"};
                String[][] orderLineData = DatabaseOperations.getOrderLines(orderID);

                // Create a DefaultTableModel
                DefaultTableModel tableModel = new DefaultTableModel(orderLineData, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        // Make all cells non-editable
                        return false;
                    }
                };

                // Create JTable with the custom model
                JTable orderLineTable = new JTable(tableModel);

                // Create a scroll pane for the table
                JScrollPane scrollPane = new JScrollPane(orderLineTable);

                // Create a dialog to display the order lines
                JDialog orderLineDialog = new JDialog();
                orderLineDialog.setTitle("Order Lines");
                orderLineDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                orderLineDialog.setSize(400, 300);
                orderLineDialog.setLocationRelativeTo(null);
                orderLineDialog.add(scrollPane);
                orderLineDialog.setVisible(true);
            }
        }, 7);



        JScrollPane scrollPane = new JScrollPane(orderTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            GUILoader.staffDashboardWindow();
        });
        contentPane.add(backButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    private class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
        private JButton renderButton;
        private JButton editButton;
        private Object editorValue;
        private JTable table;

        public ButtonColumn(JTable table, AbstractAction abstractAction, int column) {
            this.table = table;

            renderButton = new JButton();
            editButton = new JButton();
            editButton.setFocusPainted(false);
            editButton.addActionListener(this);  // Add ActionListener to the editButton

            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer(this);
            columnModel.getColumn(column).setCellEditor(this);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (hasFocus) {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            } else if (isSelected) {
                renderButton.setForeground(table.getSelectionForeground());
                renderButton.setBackground(table.getSelectionBackground());
            } else {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }

            renderButton.setText("View Order Lines");
            return renderButton;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            editButton.setText("View Order Lines");
            editorValue = value;
            return editButton;
        }

        @Override
        public Object getCellEditorValue() {
            return editorValue;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = Integer.parseInt(e.getActionCommand()); // Extract the row from the action command
            fireEditingStopped();

            // Invoke the actionListener
            ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "" + row);
            buttonColumnAction(event);
        }

        private void buttonColumnAction(ActionEvent e) {
            // Your logic to handle the button click event goes here
            System.out.println("Button clicked at row: " + e.getActionCommand());
        }
    }



    public static void main(String[] args) {
        PreviousOrders previousOrders = new PreviousOrders();
    }
}
