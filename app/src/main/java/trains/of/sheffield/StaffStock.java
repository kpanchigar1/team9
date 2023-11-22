package trains.of.sheffield;

import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatter;

public class StaffStock extends JFrame {
    private JPanel staffStockPanel;
    private JButton backButton, confirmChangesButton;
    // create table to view product and stock levels
    // put stock level in a Jspinner
    // add a confirm changes button to update stock levels

    public StaffStock() {
        super("Trains of Sheffield - Staff Stock");
        staffStockPanel = new JPanel();
        setContentPane(staffStockPanel);
        setSize(800, 400);

        List<Product> allProducts = DatabaseOperations.getAllProducts();

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Product Code", "Product Name", "Stock Level"});

        for (Product product : allProducts) {
            model.addRow(new Object[]{product.getProductCode(), product.getProductName(), product.getStock()});
        }

        // Create a JTable with the DefaultTableModel
        JTable stockTable = new JTable(model);

        // Create a custom TableCellEditor with a JSpinner
        stockTable.getColumnModel().getColumn(2).setCellEditor(new SpinnerEditor());

        JScrollPane stockTableScrollPane = new JScrollPane(stockTable);
        stockTableScrollPane.setBounds(0, 0, 800, 300);
        staffStockPanel.add(stockTableScrollPane);

        backButton = new JButton("Back");
        staffStockPanel.add(backButton);

        backButton.addActionListener(e -> {
            dispose();
            GUILoader.staffDashboardWindow();
        });

        //TODO: add confirm changes button

        setVisible(true);

    }

    private static class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
        private final JSpinner spinner;

        SpinnerEditor() {
            spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
            editor.getTextField().setHorizontalAlignment(JTextField.RIGHT);
            ((DefaultFormatter) editor.getTextField().getFormatter()).setAllowsInvalid(false);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            spinner.setValue(value);
            return spinner;
        }

        @Override
        public Object getCellEditorValue() {
            return spinner.getValue();
        }
    }

    public static void main(String[] args) {
        new StaffStock();
    }
}
