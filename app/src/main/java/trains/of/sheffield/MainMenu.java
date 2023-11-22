package trains.of.sheffield;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame
{
    private JButton trainSetButton, trackPackButton, locomotiveButton, rollingStockButton, trackButton, controllerButton, staffButton, managerButton;
    private JLabel titleLabel;
    private JPanel buttonPanel, titlePanel;
    private Container contentPane;

    public MainMenu(Role role)
    {

        super("Trains of Sheffield");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(800, 400);

        JMenuBar menuBar = new MenuBarPanel();

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
                //GUILoader.trainSetWindow();
            }
        });
        buttonPanel.add(trainSetButton);

        trackPackButton = new JButton("Track Packs");
        trackPackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //GUILoader.trackPackWindow();
            }
        });
        buttonPanel.add(trackPackButton);

        locomotiveButton = new JButton("Locomotives");
        locomotiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //GUILoader.locomotiveWindow();
            }
        });
        buttonPanel.add(locomotiveButton);

        rollingStockButton = new JButton("Rolling Stock");
        rollingStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //GUILoader.rollingStockWindow();
            }
        });
        buttonPanel.add(rollingStockButton);

        trackButton = new JButton("Track");
        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //GUILoader.trackWindow();
            }
        });
        buttonPanel.add(trackButton);

        controllerButton = new JButton("Controllers");
        controllerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //GUILoader.controllerWindow();
            }
        });
        buttonPanel.add(controllerButton);

        contentPane.add(buttonPanel, BorderLayout.CENTER);

        if (role == Role.STAFF || role == Role.MANAGER) {
            staffButton = new JButton("Staff Dashboard");
            staffButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    GUILoader.staffDashboardWindow();
                }
            });
            contentPane.add(staffButton, BorderLayout.SOUTH);
        }
        setVisible(true);
    }


    public static void main(String[] args)
    {
        MainMenu mainMenu = new MainMenu(Role.STAFF);
    }
}
