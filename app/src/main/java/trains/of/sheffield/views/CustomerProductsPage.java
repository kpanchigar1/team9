package trains.of.sheffield.views;

import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Product;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerProductsPage extends JFrame {
    private JTable productsTable;
    private JButton backButton;

    public CustomerProductsPage(String category) {
        setTitle("Customer Products Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Assuming you have a method to get products based on the category
        // Replace Product.getProductsByCategory(category) with your actual logic
        Product[] products = DatabaseOperations.getProductsFromType(category).toArray(new Product[0]);

        // Create a table model with column names
        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Product Code");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Brand");
        tableModel.addColumn("Retail Price");
        tableModel.addColumn("View Product");
        tableModel.addColumn("Add to Basket");

        // Populate the table model with data from the products array
        for (Product product : products) {
            Object[] rowData = {product.getProductCode(), product.getProductName(), product.getBrandName(), product.getPrice(), "<html><u>View Product</u></html>", "<html><u>Add to Basket</u></html>"};
            tableModel.addRow(rowData);
        }

        // Create the JTable with the populated table model
        productsTable = new JTable(tableModel);

        // Add the table to a JScrollPane to enable scrolling
        JScrollPane scrollPane = new JScrollPane(productsTable);

        // Add the JScrollPane to the JFrame
        add(scrollPane, BorderLayout.CENTER);

        // Set the JFrame visible
        setVisible(true);

        // Add a mouse listener to the table to detect when a user clicks on a row
        productsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Get the row that was clicked
                int row = productsTable.rowAtPoint(evt.getPoint());
                // Get the column that was clicked
                int col = productsTable.columnAtPoint(evt.getPoint());
                // Get the product code from the clicked row
                String productCode = productsTable.getValueAt(row, 0).toString();
                // Get the product name from the clicked row
                String productName = productsTable.getValueAt(row, 1).toString();
                // Get the brand name from the clicked row
                String brandName = productsTable.getValueAt(row, 2).toString();
                // Get the retail price from the clicked row
                String retailPrice = productsTable.getValueAt(row, 3).toString();
                // Get the text from the clicked cell
                String text = productsTable.getValueAt(row, col).toString();

                // If the user clicked on the "View Product" cell
                if (text.equals("<html><u>View Product</u></html>")) {
                    // Create a new ProductDetailsPage with the product code
                    GUILoader.productDetailsPageWindow(DatabaseOperations.getProductFromID(productCode), false, null);
                }

                // If the user clicked on the "Add to Basket" cell
                if (text.equals("<html><u>Add to Basket</u></html>")) {
                    DatabaseOperations.addProductToOrder(DatabaseOperations.getProductFromID(productCode));
                    GUILoader.alertWindow("Product added to basket");
                }
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            GUILoader.mainMenuWindow();
        });
        add(backButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerProductsPage customerProductsPage = new CustomerProductsPage("M");
        });
    }
}
