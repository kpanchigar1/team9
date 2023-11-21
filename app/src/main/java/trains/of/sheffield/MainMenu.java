package trains.of.sheffield;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame
{
    private JButton managerButton, customerButton, staffButton;
    private JLabel titleLabel;
    private JPanel buttonPanel, titlePanel;
    private Container contentPane;
    private Font titleFont = new Font("Arial", Font.BOLD, 36);
    private Font buttonFont = new Font("Arial", Font.BOLD, 24);

    public MainMenu(Role role)
    {
        // TODO: add action listeners to buttons

        super("Trains of Sheffield");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(800, 400);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem signOut = new JMenuItem("Sign Out");
        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GUILoader.loginWindow();
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentUser.clearUser();
                dispose();
                GUILoader.loginWindow();
            }
        });

        menu.add(signOut);
        menu.add(exit);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // create the title panel
        titlePanel = new JPanel();
        titleLabel = new JLabel("Welcome to Trains of Sheffield");
        titleLabel.setFont(titleFont);
        titlePanel.add(titleLabel);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        // create the button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        managerButton = new JButton("Manager");
        if (role != Role.MANAGER) {
            managerButton.setEnabled(false);
        }
        managerButton.setFont(buttonFont);
        buttonPanel.add(managerButton);
        customerButton = new JButton("Customer");
        customerButton.setFont(buttonFont);
        buttonPanel.add(customerButton);
        staffButton = new JButton("Staff");
        if (role == Role.CUSTOMER) {
            staffButton.setEnabled(false);
        }
        staffButton.setFont(buttonFont);
        buttonPanel.add(staffButton);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        System.out.println(role);
        setVisible(true);
    }


    public static void main(String[] args)
    {
        MainMenu mainMenu = new MainMenu(Role.MANAGER);
    }
}
