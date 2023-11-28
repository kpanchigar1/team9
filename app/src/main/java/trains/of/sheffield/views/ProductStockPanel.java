package trains.of.sheffield.views;

import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Product;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProductStockPanel extends JFrame {
    private JPanel productStockPanel, buttonPanel;
    private JButton backButton, confirmChangesButton, addNewProductButton;
    public ProductStockPanel(String productType) {

        // TODO: delete product
        // TODO: fix back button layout
        super("Trains of Sheffield - Staff Stock");
        productStockPanel = new JPanel(new GridBagLayout());
        setContentPane(productStockPanel);
        setSize(1200, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;


        List<Product> allProducts = DatabaseOperations.getProductsFromType(productType);

        DefaultTableModel model = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                if(column == 2){
                    return true;
                }
                return false;
            }
        };
        model.setColumnIdentifiers(new Object[]{"Product Code", "Product Name", "Stock Level", "Edit Product", "Delete Product"});


        for (Product product : allProducts) {
            Integer stock = product.getStock();
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(stock - 0, stock - 0, Integer.MAX_VALUE, 1));
            model.addRow(new Object[]{product.getProductCode(), product.getProductName(), stock, "<html><u>Edit Product</u></html>", "<html><u>Delete Product</u></html>"});
        }

        // Create a JTable with the DefaultTableModel
        JTable stockTable = new JTable(model);

        TableColumn stockColumn = stockTable.getColumnModel().getColumn(2);
        stockColumn.setCellRenderer(new GenericSpinnerRenderer<>());

        for (int row = 0; row < model.getRowCount(); row++) {
            int originalValue = (int) model.getValueAt(row, 2);
            stockColumn.setCellEditor(new GenericSpinnerEditor<>(originalValue, new SpinnerNumberModel(originalValue, 0, Integer.MAX_VALUE, 1)));
        }


        // Create a custom TableCellEditor with a JSpinner

        JScrollPane stockTableScrollPane = new JScrollPane(stockTable);
        stockTableScrollPane.setBounds(0, 0, 800, 300);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;

        stockTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detect double click
                    int row = stockTable.rowAtPoint(e.getPoint());
                    int column = stockTable.columnAtPoint(e.getPoint());

                    if (column == 4) { // Assuming "Delete Product" is at column index 4
                        // Perform delete product action here
                        String productCode = (String) model.getValueAt(row, 0);
                        int option = JOptionPane.showConfirmDialog(
                                ProductStockPanel.this,
                                "Are you sure you want to delete this product?",
                                "Confirm Deletion",
                                JOptionPane.YES_NO_OPTION);

                        if (option == JOptionPane.YES_OPTION) {
                            // Perform the deletion action
                            System.out.println("Deleting product " + productCode);
                            //DatabaseOperations.deleteProduct(productCode);
                            //model.removeRow(row);
                        }
                    }

                    if (column == 3) { // Assuming "Edit Product" is at column index 3
                        // Perform edit product action here
                        String productCode = (String) model.getValueAt(row, 0);
                        Product product = DatabaseOperations.getProductFromID(productCode);
                        GUILoader.productDetailsPageWindow(product, true, null);
                    }
                }
            }
        });

        productStockPanel.add(stockTableScrollPane, gbc);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        confirmChangesButton = new JButton("Confirm Changes");
        confirmChangesButton.addActionListener(e -> {
            for (int row = 0; row < model.getRowCount(); row++) {
                String productCode = (String) model.getValueAt(row, 0);
                // get spinner value
                int stock = 0;
                TableCellEditor editor = stockTable.getCellEditor(row, 2);
                if (editor instanceof GenericSpinnerEditor) {
                    JSpinner spinner = ((GenericSpinnerEditor<?>) editor).getSpinner();
                    stock = (int) spinner.getValue();

                    // Now you have the product code and the current stock value

                DatabaseOperations.updateStock(productCode, stock);

                dispose();
                GUILoader.productStockPanelWindow(productType);
                }
            }
        });
        buttonPanel.add(confirmChangesButton);

        // Edit product on double click, with option to delete

        addNewProductButton = new JButton("Add New Product");
        addNewProductButton.addActionListener(e -> {
            GUILoader.productDetailsPageWindow(null, true, productType);
        });
        buttonPanel.add(addNewProductButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        productStockPanel.add(buttonPanel, gbc);

        backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            dispose();
            GUILoader.staffDashboardWindow();
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        productStockPanel.add(backButton, gbc);

        setVisible(true);

    }
}
