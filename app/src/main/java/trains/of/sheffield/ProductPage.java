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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Filter Panel
        JPanel filterPanel = new JPanel();
        filterComboBox = new JComboBox<>(new String[]{"?"}); // not sure about this
        filterPanel.add(new JLabel("Filter by:"));
        filterPanel.add(filterComboBox);

        // Product Table
        String[] columnNames = {"Product Code", "Product Name", "Retail Price", "Gauge"};
        Object[][] data = {}; // Populate with product data
        productTable = new JTable(data, columnNames);

        // Adding components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.SOUTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Event Handling
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement search functionality
            }
        });

        //hello testing

        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement filter functionality
            }
        });
    }

