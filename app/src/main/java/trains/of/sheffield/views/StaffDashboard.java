package trains.of.sheffield.views;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffDashboard extends JFrame {
    private JPanel titlePanel, centerButtonPanel, southButtonPanel;
    private Container contentPane;
    private JButton trainSetButton, trackPackButton, locomotiveButton, rollingStockButton, trackButton,
            controllerButton, managerButton, pendingOrdersButton, previousOrdersButton;
    private JLabel titleLabel;

    // TODO: make fullscreen
    public StaffDashboard() {
        super("Trains of Sheffield - Staff Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(800, 400);


        JMenuBar menuBar = new MenuBarPanel();

        JMenu staffMenu = new JMenu("Staff");
        staffMenu.add(new JMenuItem("Orders"));
        staffMenu.add(new JMenuItem("Stock"));
        menuBar.add(staffMenu);

        setJMenuBar(menuBar);

        titlePanel = new JPanel();
        titleLabel = new JLabel("Trains of Sheffield - Staff Dashboard");
        titlePanel.add(titleLabel);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        centerButtonPanel = new JPanel();
        centerButtonPanel.setLayout(new GridLayout(1, 3));

        southButtonPanel = new JPanel();
        southButtonPanel.setLayout(new GridLayout(1, 3));

        trainSetButton = new JButton("Train Set Stock");
        trainSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("M");
            }
        });

        trackPackButton = new JButton("Track Pack Stock");
        trackPackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("P");
            }
        });

        locomotiveButton = new JButton("Locomotives Stock");
        locomotiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("L");
            }
        });

        rollingStockButton = new JButton("Rolling Stock Stock");
        rollingStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("S");
            }
        });

        trackButton = new JButton("Track Stock");
        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.productStockPanelWindow("R");
            }
        });

        controllerButton = new JButton("Controllers Stock");
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
                //GUILoader.staffStockWindow();
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
        southButtonPanel.add(previousOrdersButton);

        if (CurrentUser.getRole() == Role.MANAGER) {
            southButtonPanel.add(managerButton);
        }

        contentPane.add(southButtonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StaffDashboard();
    }
}
