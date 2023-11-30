package trains.of.sheffield.views;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is used to create the staff dashboard window which has buttons to access the stock pages,
 * pending orders page and previous orders page.
 */
public class StaffDashboard extends JFrame {
    private JPanel titlePanel, centerButtonPanel, southButtonPanel;
    private Container contentPane;
    private JButton trainSetButton, trackPackButton, locomotiveButton, rollingStockButton, trackButton,
            controllerButton, managerButton, pendingOrdersButton, previousOrdersButton, backButton;
    private JLabel titleLabel;

    /**
     * This constructor creates the staff dashboard window.
     */
    public StaffDashboard() {
        super("Trains of Sheffield - Staff Dashboard");
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
        titleLabel = new JLabel("Trains of Sheffield - Staff Dashboard");
        titlePanel.add(titleLabel);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        centerButtonPanel = new JPanel();
        centerButtonPanel.setLayout(new GridLayout(1, 3));

        southButtonPanel = new JPanel();
        southButtonPanel.setLayout(new GridLayout(1, 3));

        trainSetButton = new JButton("Train Set");
        trainSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("M");
            }
        });

        trackPackButton = new JButton("Track Pack");
        trackPackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("P");
            }
        });

        locomotiveButton = new JButton("Locomotives");
        locomotiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("L");
            }
        });

        rollingStockButton = new JButton("Rolling Stock");
        rollingStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("S");
            }
        });

        trackButton = new JButton("Track");
        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("R");
            }
        });

        controllerButton = new JButton("Controllers");
        controllerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("C");
            }
        });

        managerButton = new JButton("Manager Dashboard");
        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.managerDashboardWindow();
            }
        });

        centerButtonPanel.add(trainSetButton);
        centerButtonPanel.add(trackPackButton);
        centerButtonPanel.add(locomotiveButton);
        centerButtonPanel.add(rollingStockButton);
        centerButtonPanel.add(trackButton);
        centerButtonPanel.add(controllerButton);

        contentPane.add(centerButtonPanel, BorderLayout.CENTER);

        pendingOrdersButton = new JButton("Pending Orders");
        pendingOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.pendingOrdersWindow();
            }
        });
        southButtonPanel.add(pendingOrdersButton);

        previousOrdersButton = new JButton("View Previous Sales");
        previousOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.previousOrdersWindow();
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.mainMenuWindow();
            }
        });
        southButtonPanel.add(previousOrdersButton);
        southButtonPanel.add(backButton);

        if (CurrentUser.getRole() == Role.MANAGER) {
            southButtonPanel.add(managerButton);
        }

        contentPane.add(southButtonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
