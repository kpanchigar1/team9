package trains.of.sheffield.views;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Gauge;
import trains.of.sheffield.Product;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is used to create the product details page window which displays all details of a given product and allows the staff user to edit them.
 * This class is also used to create the product details page window which displays all details of a given product and allows the customer user to add it to their basket.
 */
public class ProductDetailsPage extends JFrame {

    private JLabel productCodeLabel, nameLabel, brandLabel, scaleLabel, priceLabel, descriptionLabel, stockLabel, eraLabel, isAnalogueLabel, isExtensionPackLabel;
    private JTextField productCodeField, nameField, brandField, scaleField, priceField, descriptionField, stockField, eraField, isAnalogueField, isExtensionPackField;
    private JButton addToBasketButton, confirmChangesButton, backButton, addItemButton;
    private JTable trainSetTable;

    public ProductDetailsPage(Product product, boolean fromStaffDashboard, String productType) {
        setTitle("Product Details Page");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(13, 2)); // Adjust the layout based on your needs

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
        isExtensionPackLabel = new JLabel("Extension Pack");

        productCodeField = new JTextField();
        nameField = new JTextField();
        brandField = new JTextField();
        scaleField = new JTextField();
        priceField = new JTextField();
        descriptionField = new JTextField();
        stockField = new JTextField();
        eraField = new JTextField();
        isAnalogueField = new JTextField();
        isExtensionPackField = new JTextField();


        addItemButton = new JButton("Add Item");
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

        trainSetTable = new JTable();
        DefaultTableModel model = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }
        };
        model.addColumn("Product Code");
        model.addColumn("Quantity");
        model.addColumn("");
        trainSetTable.setModel(model);

        JScrollPane scroll = new JScrollPane(trainSetTable);

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
            add(isExtensionPackLabel);
            add(isExtensionPackField);
        }
        if(Objects.equals(productType, "M")) {
            // Train set
            add(scroll);
            add(addItemButton);
        }

        add(new JLabel());
        add(new JLabel());


        confirmChangesButton = new JButton("Confirm Changes");

        // Add components to the JFrame

        // Set fields as uneditable (if not from staff dashboard)

        if(!fromStaffDashboard) {
            productCodeField.setEditable(false);
            nameField.setEditable(false);
            brandField.setEditable(false);
            scaleField.setEditable(false);
            priceField.setEditable(false);
            descriptionField.setEditable(false);
            stockField.setEditable(false);
            eraField.setEditable(false);
            isAnalogueField.setEditable(false);
        }
        else {
            productCodeField.setEditable(true);
            nameField.setEditable(true);
            brandField.setEditable(true);
            scaleField.setEditable(true);
            priceField.setEditable(true);
            descriptionField.setEditable(true);
            stockField.setEditable(true);
            eraField.setEditable(true);
            isAnalogueField.setEditable(true);
        }

        // Add action listeners to buttons
        addToBasketButton.addActionListener(e -> {
            DatabaseOperations.addProductToOrder(product);
            dispose();
            GUILoader.alertWindow("Product added to basket");
        });

        String finalProductType = productType;
        confirmChangesButton.addActionListener(e -> {
           DatabaseOperations.updateProduct(new Product(productCodeField.getText(), brandField.getText(), nameField.getText(), Double.parseDouble(priceField.getText()), Gauge.valueOf(scaleField.getText()), descriptionField.getText(), Integer.parseInt(stockField.getText())));
           // update other tables based on product type
            if(productCodeField.getText().substring(0, 1).equals(finalProductType)) {
                if (Objects.equals(finalProductType, "M") || Objects.equals(finalProductType, "L") || Objects.equals(finalProductType, "S")) {
                    DatabaseOperations.updateProductEra(productCodeField.getText(), eraField.getText());
                }
                if (Objects.equals(finalProductType, "C")) {
                    // Controller
                    DatabaseOperations.updateProductAnalogue(productCodeField.getText(), isAnalogueField.getText());
                }
                if (Objects.equals(finalProductType, "P")) {
                    // Track pack
                    DatabaseOperations.updateProductTrackPack(productCodeField.getText(), isExtensionPackField.getText());
                }
                if (Objects.equals(finalProductType, "M")) {
                    // Train set
                    // Go throug the table and update the database with the new values
                    DefaultTableModel model1 = (DefaultTableModel) trainSetTable.getModel();
                    for (int i = 0; i < model1.getRowCount(); i++) {
                        String productCode = (String) model1.getValueAt(i, 0);
                        int quantity = Integer.parseInt(model1.getValueAt(i, 1).toString());
                        if (productCode.equals("") || quantity == 0) {
                            continue;
                        }
                        DatabaseOperations.updateTrainSetData(productCodeField.getText(), productCode, quantity);
                    }
                }
                JOptionPane.showMessageDialog(ProductDetailsPage.this, "Changes have been made successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(ProductDetailsPage.this, "Product code does not match product type.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        if(product != null) {
            populateFields(product);
        }
        else if(productType.equals("M")) {
            model.addRow(new String[]{"", "", ""});
        }


        if(!fromStaffDashboard) {
            add(addToBasketButton);
        }
        else {
            add(confirmChangesButton);
        }

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
        });
        add(backButton);
        setVisible(true);
        trainSetTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = trainSetTable.rowAtPoint(evt.getPoint());
                int col = trainSetTable.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    if (col == 2) {
                        // Remove row
                        DefaultTableModel model = (DefaultTableModel) trainSetTable.getModel();
                        DatabaseOperations.deleteTrainSetLink(productCodeField.getText(), (String) model.getValueAt(row, 0));
                        model.removeRow(row);
                    }
                }
            }
        });

    }

    /**
     * This method is used to populate the fields of the product details page with the data from the database.
     * @param product
     */
    public void populateFields(Product product) {
        // Replace this with your actual database logic
        productCodeField.setText(product.getProductCode());
        nameField.setText(product.getProductName());
        brandField.setText(product.getBrandName());
        scaleField.setText(product.getGauge().toString());
        priceField.setText(String.valueOf(product.getPrice()));
        descriptionField.setText(product.getDescription());
        stockField.setText(String.valueOf(product.getStock()));

        if (product.getProductCode().charAt(0) == 'M' || product.getProductCode().charAt(0) == 'L' || product.getProductCode().charAt(0) == 'S') {
            eraField.setText(DatabaseOperations.getProductEra(product.getProductCode()));
        }
        if (product.getProductCode().charAt(0) == 'C') {
            // Controller
            isAnalogueField.setText(DatabaseOperations.getProductAnalogue(product.getProductCode()));
        }
        if (product.getProductCode().charAt(0) == 'P') {
            // Track pack
            isExtensionPackField.setText(DatabaseOperations.getTrackPackType(product.getProductCode()));
        }
        if (product.getProductCode().charAt(0) == 'M') {
            // Train set
            ArrayList<String[]> trainSetData = DatabaseOperations.getTrainSetData(product.getProductCode());
            DefaultTableModel model = (DefaultTableModel) trainSetTable.getModel();
            for (String[] row : trainSetData) {
                model.addRow(row);
            }
        }

        addItemButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) trainSetTable.getModel();
            model.addRow(new String[]{"", "", "<html><u>Remove</u></html>"});
        });
    }

    public static void main(String[] args) {
        ProductDetailsPage page = new ProductDetailsPage(null, true, "M");
    }
}
