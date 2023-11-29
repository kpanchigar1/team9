package trains.of.sheffield.views;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Role;
import trains.of.sheffield.models.DatabaseOperations;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class is used to create the main menu window
 */
public class MainMenu extends JFrame
{
    private JButton trainSetButton, trackPackButton, locomotiveButton, rollingStockButton, trackButton, controllerButton, staffButton, basketButton, ordersButton;
    private JLabel titleLabel;
    private JPanel buttonPanel, titlePanel, southPanel;
    private Container contentPane;

    public MainMenu(Role role)
    {
        super("Trains of Sheffield");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(800, 400);

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
        

        titlePanel = new JPanel();
        titleLabel = new JLabel("Welcome to Trains of Sheffield");
        titlePanel.add(titleLabel);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        trainSetButton = new JButton("Train Sets");
        trainSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.customerProductPageWindow("M");
            }
        });
        buttonPanel.add(trainSetButton);

        trackPackButton = new JButton("Track Packs");
        trackPackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.customerProductPageWindow("P");
            }
        });
        buttonPanel.add(trackPackButton);

        locomotiveButton = new JButton("Locomotives");
        locomotiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.customerProductPageWindow("L");
            }
        });
        buttonPanel.add(locomotiveButton);

        rollingStockButton = new JButton("Rolling Stock");
        rollingStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.customerProductPageWindow("S");
            }
        });
        buttonPanel.add(rollingStockButton);

        trackButton = new JButton("Track");
        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.customerProductPageWindow("R");
            }
        });
        buttonPanel.add(trackButton);

        controllerButton = new JButton("Controllers");
        controllerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.customerProductPageWindow("C");
            }
        });
        buttonPanel.add(controllerButton);

        contentPane.add(buttonPanel, BorderLayout.CENTER);

        ordersButton = new JButton("View Orders");
        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILoader.viewBasketWindow();
            }
        });

        basketButton = new JButton("View Basket");
        basketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILoader.orderLinesWindow(DatabaseOperations.getBasketFromUser(CurrentUser.getId()), null, true);
            }
        });

        southPanel = new JPanel();

        if (role == Role.STAFF || role == Role.MANAGER) {
            southPanel.setLayout(new GridLayout(1, 3));
            southPanel.add(ordersButton);
            southPanel.add(basketButton);
            staffButton = new JButton("Staff Dashboard");
            staffButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    GUILoader.staffDashboardWindow();
                }
            });
            southPanel.add(staffButton);
        } else {
            southPanel.setLayout(new GridLayout(1, 2));
            southPanel.add(ordersButton);
            southPanel.add(basketButton);
        }

        contentPane.add(southPanel, BorderLayout.SOUTH);
    }
}
