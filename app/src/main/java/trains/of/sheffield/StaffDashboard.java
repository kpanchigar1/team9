package trains.of.sheffield;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StaffDashboard extends JFrame {
    private JPanel staffDashboardPanel;
    private JButton ordersButton, stockButton, addNewProductButton, backButton;

    //TODO: make GUI proper
    //TODO: stock page
    //TODO: orders page
    
    public StaffDashboard() {
        super("Trains of Sheffield - Staff Dashboard");
        staffDashboardPanel = new JPanel();
        setContentPane(staffDashboardPanel);
        setSize(800, 400);

        JMenuBar menuBar = new MenuBarPanel();


        JMenu staffMenu = new JMenu("Staff");
        staffMenu.add(new JMenuItem("Orders"));
        staffMenu.add(new JMenuItem("Stock"));
        menuBar.add(staffMenu);

        setJMenuBar(menuBar);

        ordersButton = new JButton("Orders");
        stockButton = new JButton("Stock");
        backButton = new JButton("Back");
        addNewProductButton = new JButton("Add New Product");
        staffDashboardPanel.add(ordersButton);
        staffDashboardPanel.add(stockButton);
        staffDashboardPanel.add(addNewProductButton);
        staffDashboardPanel.add(backButton);

        ordersButton.addActionListener(e -> {
            //dispose();
            //GUILoader.staffOrdersWindow();
        });
        stockButton.addActionListener(e -> {
            dispose();
            try {
                GUILoader.staffStockWindow();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton.addActionListener(e -> {
            dispose();
            GUILoader.mainMenuWindow();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StaffDashboard();
    }
}
