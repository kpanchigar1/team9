package trains.of.sheffield;

import javax.swing.*;

public class StaffDashboard extends JFrame {
    private JPanel staffDashboardPanel;
    private JButton ordersButton, stockButton, backButton;

    //TODO: make GUI proper
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
        staffDashboardPanel.add(ordersButton);
        staffDashboardPanel.add(stockButton);
        staffDashboardPanel.add(backButton);
        ordersButton.addActionListener(e -> {
            //dispose();
            //GUILoader.staffOrdersWindow();
        });
        stockButton.addActionListener(e -> {
            //dispose();
            //GUILoader.staffStockWindow();
        });
        backButton.addActionListener(e -> {
            dispose();
            GUILoader.mainMenuWindow(CurrentUser.getRole());
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StaffDashboard();
    }
}
