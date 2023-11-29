package trains.of.sheffield.views;

import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Gauge;
import trains.of.sheffield.Product;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ProductDetailsPage extends JFrame {

    // TODO: product name and brand is swapped somewhere
    // TODO: add back button

    private JLabel productCodeLabel, nameLabel, brandLabel, scaleLabel, priceLabel, descriptionLabel, stockLabel, eraLabel, isAnalogueLabel, controllerLabel, locomotiveLabel, rollingStockLabel, trackPackLabel;
    private JTextField productCodeField, nameField, brandField, scaleField, priceField, descriptionField, stockField, eraField, isAnalogueField, controllerField, locomotiveField, rollingStockField, trackPackField;
    private JButton addToBasketButton, confirmChangesButton;

    public ProductDetailsPage(Product product, boolean fromStaffDashboard, String productType) {
        setTitle("Product Details Page");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(13, 2)); // Adjust the layout based on your needs

        // Initialize components
        productCodeLabel = new JLabel("Product Code:");
        nameLabel = new JLabel("Name:");
        brandLabel = new JLabel("Brand:");
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
        brandField = new JTextField();
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
        add(brandLabel);
        add(brandField);
        add(scaleLabel);
        add(scaleField);
        add(priceLabel);
        add(priceField);
        add(descriptionLabel);
        add(descriptionField);
        add(stockLabel);
        add(stockField);

        if(product != null){
            productType = product.getProductCode().substring(0, 1);

        }

        if(Objects.equals(productType, "M") || Objects.equals(productType, "L") || Objects.equals(productType, "S")) {
            add(eraLabel);
            add(eraField);
        }
        if(Objects.equals(productType, "C")) {
            // Controller
            add(isAnalogueLabel);
            add(isAnalogueField);
        }
        if(Objects.equals(productType, "P")) {
            // Track pack
            add(trackPackLabel);
            add(trackPackField);
        }
        if(Objects.equals(productType, "M")) {
            // Train set
            add(locomotiveLabel);
            add(locomotiveField);
            add(rollingStockLabel);
            add(rollingStockField);
            add(controllerLabel);
            add(controllerField);
            add(trackPackLabel);
            add(trackPackField);
        }

        add(new JLabel()); // Empty label as a placeholder

        confirmChangesButton = new JButton("Confirm Changes");

        // Add components to the JFrame

        // Set fields as uneditable (if not from staff dashboard)

        if(!fromStaffDashboard) {
            add(addToBasketButton);
            addToBasketButton.setVisible(true);  // Set addToBasketButton visible
            confirmChangesButton.setVisible(false);
            productCodeField.setEditable(false);
            nameField.setEditable(false);
            brandField.setEditable(false);
            scaleField.setEditable(false);
            priceField.setEditable(false);
            descriptionField.setEditable(false);
            stockField.setEditable(false);
            eraField.setEditable(false);
            isAnalogueField.setEditable(false);
            controllerField.setEditable(false);
            locomotiveField.setEditable(false);
            rollingStockField.setEditable(false);
            trackPackField.setEditable(false);
        }
        else {
            add(confirmChangesButton);
            addToBasketButton.setVisible(false);
            confirmChangesButton.setVisible(true);
            productCodeField.setEditable(true);
            nameField.setEditable(true);
            brandField.setEditable(true);
            scaleField.setEditable(true);
            priceField.setEditable(true);
            descriptionField.setEditable(true);
            stockField.setEditable(true);
            eraField.setEditable(true);
            isAnalogueField.setEditable(true);
            controllerField.setEditable(true);
            locomotiveField.setEditable(true);
            rollingStockField.setEditable(true);
            trackPackField.setEditable(true);
        }

        // Add action listeners to buttons
        addToBasketButton.addActionListener(e -> {
            DatabaseOperations.addProductToOrder(product);
            dispose();
            GUILoader.alertWindow("Product added to basket");
        });

        String finalProductType = productType;
        confirmChangesButton.addActionListener(e -> {
            // add new product to database / update existing product
            // TODO: update stock
            // TODO: brand and name are swapped somewhere
           DatabaseOperations.updateProduct(new Product(productCodeField.getText(), nameField.getText(), brandField.getText(), Double.parseDouble(priceField.getText()), Gauge.valueOf(scaleField.getText()), descriptionField.getText(), Integer.parseInt(stockField.getText())));
           // update other tables based on product type
            if(Objects.equals(finalProductType, "M") || Objects.equals(finalProductType, "L") || Objects.equals(finalProductType, "S")) {
                DatabaseOperations.updateProductEra(productCodeField.getText(), eraField.getText());
            }
            if(Objects.equals(finalProductType, "C")) {
                // Controller
                DatabaseOperations.updateProductAnalogue(productCodeField.getText(), isAnalogueField.getText());
            }
            if(Objects.equals(finalProductType, "P")) {
                // Track pack
                // DatabaseOperations.updateProductTrackPack(productCodeField.getText(), trackPackField.getText());
            }
            if(Objects.equals(finalProductType, "M")) {
                // Train set
                // pass in product code between the brackets
                String locomotiveCode = locomotiveField.getText().substring(locomotiveField.getText().indexOf("(") + 1, locomotiveField.getText().indexOf(")"));
                String rollingStockCode = rollingStockField.getText().substring(rollingStockField.getText().indexOf("(") + 1, rollingStockField.getText().indexOf(")"));
                String controllerCode = controllerField.getText().substring(controllerField.getText().indexOf("(") + 1, controllerField.getText().indexOf(")"));
                String trackPackCode = trackPackField.getText().substring(trackPackField.getText().indexOf("(") + 1, trackPackField.getText().indexOf(")"));
                DatabaseOperations.updateTrainSetData(productCodeField.getText(), locomotiveCode, rollingStockCode, controllerCode, trackPackCode);
            }
            JOptionPane.showMessageDialog(ProductDetailsPage.this, "Changes have been made successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        });

        if(product != null) {
            populateFields(product);
        }
        setVisible(true);
    }

    // Example method to set data from the database if editing
    public void populateFields(Product product) {
        // Replace this with your actual database logic
        productCodeField.setText(product.getProductCode());
        nameField.setText(product.getProductName());
        brandField.setText(product.getBrandName());
        scaleField.setText(product.getGauge().toString());
        priceField.setText(String.valueOf(product.getPrice()));
        descriptionField.setText(product.getDescription());
        stockField.setText(String.valueOf(product.getStock()));

        if(product.getProductCode().charAt(0) == 'M' || product.getProductCode().charAt(0) == 'L' || product.getProductCode().charAt(0) == 'S') {
            eraField.setText(DatabaseOperations.getProductEra(product.getProductCode()));
        }
        if(product.getProductCode().charAt(0) == 'C') {
            // Controller
            isAnalogueField.setText(DatabaseOperations.getProductAnalogue(product.getProductCode()));
        }
        if(product.getProductCode().charAt(0) == 'P') {
            // Track pack
            // trackPackField.setText(String.valueOf(product.getTrackPack()));
        }
        if(product.getProductCode().charAt(0) == 'M') {
            // Train set
            String[] trainSetData = DatabaseOperations.getTrainSetData(product.getProductCode());
            locomotiveField.setText(trainSetData[0]);
            rollingStockField.setText(trainSetData[1]);
            controllerField.setText(trainSetData[2]);
            trackPackField.setText(trainSetData[3]);
        }
    }

    public static void main(String[] args) {
        Product product = DatabaseOperations.getProductFromID("M001");
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(product, true, "M");
    }
}
