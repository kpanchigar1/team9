package trains.of.sheffield.views;

import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Product;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.util.List;

public class ProductStockPanel extends JFrame {
    private JPanel productStockPanel;
    private JButton backButton, confirmChangesButton;
    public ProductStockPanel(String productType) {
        // TODO: add a new product
        // TODO: edit product
        // TODO: delete product
        super("Trains of Sheffield - Staff Stock");
        productStockPanel = new JPanel();
        setContentPane(productStockPanel);
        setSize(800, 400);

        List<Product> allProducts = DatabaseOperations.getProductFromType(productType);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Product Code", "Product Name", "Stock Level"});

        for (Product product : allProducts) {
            Integer stock = product.getStock();
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(stock - 0, stock - 0, Integer.MAX_VALUE, 1));
            model.addRow(new Object[]{product.getProductCode(), product.getProductName(), stock});
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
        productStockPanel.add(stockTableScrollPane);

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
        productStockPanel.add(confirmChangesButton);

        // Edit product on double click, with option to delete

        JButton addNewProductButton = new JButton("Add New Product");
        addNewProductButton.addActionListener(e -> {
            dispose();
            GUILoader.addNewProductWindow(productType);
        });
        productStockPanel.add(addNewProductButton);

        backButton = new JButton("Back");
        productStockPanel.add(backButton);

        backButton.addActionListener(e -> {
            dispose();
            GUILoader.staffDashboardWindow();
        });

        //TODO: add confirm changes button

        setVisible(true);

    }
}
