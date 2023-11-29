package trains.of.sheffield.views;

import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Product;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * This class is used to create the product stock page window which displays all products of a given type and
 * allows the user to edit or delete them.
 */
public class ProductStockPanel extends JFrame {
    private JPanel productStockPanel, buttonPanel, southPanel;
    private JButton backButton, confirmChangesButton, addNewProductButton;
    public ProductStockPanel(String productType) {

        // TODO: delete product

        super("Trains of Sheffield - Staff Stock");
        productStockPanel = new JPanel(new GridBagLayout());
        setContentPane(productStockPanel);
        setSize(1200, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.weighty = 2;


        List<Product> allProducts = DatabaseOperations.getProductsFromType(productType);

        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnIdentifiers(new Object[]{"Product Code", "Product Name", "Stock Level", "Edit Product", "Delete Product"});


        for (Product product : allProducts) {
            Integer stock = product.getStock();
            model.addRow(new Object[]{product.getProductCode(), product.getProductName(), stock, "<html><u>Edit Product</u></html>", "<html><u>Delete Product</u></html>"});
        }

        // Create a JTable with the DefaultTableModel
        JTable stockTable = new JTable(model);


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

        // Edit product on double click, with option to delete

        addNewProductButton = new JButton("Add New Product");
        addNewProductButton.addActionListener(e -> {
            GUILoader.productDetailsPageWindow(null, true, productType);
        });
        buttonPanel.add(addNewProductButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 0;
        gbc.gridheight = 1;
        productStockPanel.add(buttonPanel, gbc);

        backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            dispose();
            GUILoader.staffDashboardWindow();
        });
        southPanel = new JPanel();
        southPanel.add(backButton);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        productStockPanel.add(southPanel, gbc);

        setVisible(true);

    }
}
