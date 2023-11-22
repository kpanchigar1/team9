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


        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem signOut = new JMenuItem("Sign Out");
        JMenuItem exit = new JMenuItem("Exit");
        menu.add(signOut);
        menu.add(exit);
        menuBar.add(menu);
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

        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.loginWindow();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StaffDashboard();
    }
}
