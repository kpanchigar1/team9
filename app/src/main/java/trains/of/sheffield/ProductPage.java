package trains.of.sheffield;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ProductPage extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> filterComboBox;
    private JTable productTable;

    public ProductPage() {
        setTitle("Product Page");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // The search Panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // The filter Panel
        JPanel filterPanel = new JPanel();
        filterComboBox = new JComboBox<>(new String[]{"All Categories", "Train Sets", "Locomotives", "Carriages", "Tracks"}); // not sure about this
        filterPanel.add(new JLabel("Filter by:"));
        filterPanel.add(filterComboBox);

        // The product Table
        String[] columnNames = {"Product Code", "Product Name", "Retail Price", "Gauge"};
        Object[][] data = {};
        productTable = new JTable(data, columnNames);

        // Adding components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.SOUTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                String filter = filterComboBox.getSelectedItem().toString();
                // updateProductTable(searchProducts(searchTerm, filter));
            }
        });

        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                String filter = filterComboBox.getSelectedItem().toString();
                // updateProductTable(searchProducts(searchTerm, filter));
            }
        });
    }

    private void updateProductTable(Object[][] data) {
        // productTable.setModel(new DefaultTableModel(data, columnNames));
    }
}

