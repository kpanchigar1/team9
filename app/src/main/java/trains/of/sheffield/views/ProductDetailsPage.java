package trains.of.sheffield.views;

import trains.of.sheffield.Product;

import javax.swing.*;
import java.awt.*;

public class ProductDetailsPage extends JFrame {
    // TODO: display product code, name, scale, price, description, stock (if from staff dashboard), and other fields based on product type
    // TODO: populate fields with data from database if editing
    // TODO: make fields uneditable if not from staff dashboard
    // TODO: add a add to basket button if from customer dashboard

    private JLabel productCodeLabel, nameLabel, brandLabel, scaleLabel, priceLabel, descriptionLabel, stockLabel, eraLabel, isAnalogueLabel, controllerLabel, locomotiveLabel, rollingStockLabel, trackPackLabel;
    private JTextField productCodeField, nameField, scaleField, priceField, descriptionField, stockField, eraField, isAnalogueField, controllerField, locomotiveField, rollingStockField, trackPackField;
    private JButton addToBasketButton, confirmChangesButton;
    public ProductDetailsPage(Product product, boolean fromStaffDashboard) {
        setTitle("Product Details Page");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2)); // Adjust the layout based on your needs

        // Initialize components
        productCodeLabel = new JLabel("Product Code:");
        nameLabel = new JLabel("Name:");
        scaleLabel = new JLabel("Scale:");
        priceLabel = new JLabel("Price:");
        descriptionLabel = new JLabel("Description:");
        stockLabel = new JLabel("Stock:");

        productCodeField = new JTextField();
        nameField = new JTextField();
        scaleField = new JTextField();
        priceField = new JTextField();
        descriptionField = new JTextField();
        stockField = new JTextField();

        addToBasketButton = new JButton("Add to Basket");

        // Add components to the JFrame
        add(productCodeLabel);
        add(productCodeField);
        add(nameLabel);
        add(nameField);
        add(scaleLabel);
        add(scaleField);
        add(priceLabel);
        add(priceField);
        add(descriptionLabel);
        add(descriptionField);
        add(stockLabel);
        add(stockField);
        add(new JLabel()); // Empty label as a placeholder
        add(addToBasketButton);

        // Set fields as uneditable (if not from staff dashboard)
        // You should replace this condition with your actual logic

        productCodeField.setEditable(false);
        nameField.setEditable(false);
        scaleField.setEditable(false);
        priceField.setEditable(false);
        descriptionField.setEditable(false);
        stockField.setEditable(false);
        setVisible(true);
    }

    // Example method to set data from the database if editing
    public void populateFieldsFromDatabase() {
        // Replace this with your actual database logic
        productCodeField.setText("12345"); // Example data
        nameField.setText("Product Name");
        scaleField.setText("1:24");
        priceField.setText("$49.99");
        descriptionField.setText("Product description...");
        stockField.setText("10");
    }

    public static void main(String[] args) {
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(null, false);
        productDetailsPage.populateFieldsFromDatabase();
    }
}
