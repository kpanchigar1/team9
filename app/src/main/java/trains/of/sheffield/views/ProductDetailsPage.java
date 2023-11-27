package trains.of.sheffield.views;

import trains.of.sheffield.Gauge;
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
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(12, 2)); // Adjust the layout based on your needs

        // Initialize components
        productCodeLabel = new JLabel("Product Code:");
        nameLabel = new JLabel("Name:");
        scaleLabel = new JLabel("Scale:");
        priceLabel = new JLabel("Price:");
        descriptionLabel = new JLabel("Description:");
        stockLabel = new JLabel("Stock:");
        eraLabel = new JLabel("Era:");
        isAnalogueLabel = new JLabel("Analogue:");
        controllerLabel = new JLabel("Controller:");
        locomotiveLabel = new JLabel("Locomotive:");
        rollingStockLabel = new JLabel("Rolling Stock:");
        trackPackLabel = new JLabel("Track Pack:");

        productCodeField = new JTextField();
        nameField = new JTextField();
        scaleField = new JTextField();
        priceField = new JTextField();
        descriptionField = new JTextField();
        stockField = new JTextField();
        eraField = new JTextField();
        isAnalogueField = new JTextField();
        controllerField = new JTextField();
        locomotiveField = new JTextField();
        rollingStockField = new JTextField();
        trackPackField = new JTextField();


        addToBasketButton = new JButton("Add to Basket");

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


        // Era - only for train sets, locomotives, rolling stock
        // isAnalogue - only for controllers
        // isExtensionPack - only for track packs
        // Train set - Locomotive, rolling stock, track pack, controller

        String productCode = product.getProductCode();

        if(productCode.charAt(0) == 'M' || productCode.charAt(0) == 'L' || productCode.charAt(0) == 'S') {
            add(eraLabel);
            add(eraField);
        }
        if(productCode.charAt(0) == 'C') {
            // Controller
            add(isAnalogueLabel);
            add(isAnalogueField);
        }
        if(productCode.charAt(0) == 'P') {
            // Track pack
            add(trackPackLabel);
            add(trackPackField);
        }
        if(productCode.charAt(0) == 'M') {
            // Train set
            add(locomotiveLabel);
            add(locomotiveField);
            add(rollingStockLabel);
            add(rollingStockField);
            add(controllerLabel);
            add(controllerField);
        }

        add(new JLabel()); // Empty label as a placeholder
        add(addToBasketButton);

        // Add components to the JFrame

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
        productCodeField.setText("M12345");
        nameField.setText("Product Name");
        scaleField.setText("1:24");
        priceField.setText("$49.99");
        descriptionField.setText("Product description...");
        stockField.setText("10");
    }

    public static void main(String[] args) {
        Product product = new Product("M12345", "Product Name", "1:24", 49.99, Gauge.OO, "Product desc..", 10);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(product, false);

    }
}
