package trains.of.sheffield.views;

import javax.swing.*;

import trains.of.sheffield.GUILoader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarPanel extends JMenuBar {
    // TODO: make action listeners working
    public MenuBarPanel() {
        JMenu profile = new JMenu("Profile");
        JMenuItem editProfile = new JMenuItem("Edit Profile");
        JMenuItem editCardDetails = new JMenuItem("Edit Card Details");
        JMenuItem signOut = new JMenuItem("Sign Out");

        profile.add(editProfile);

        editCardDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILoader.cardDetailsWindow();
            }
        });
        profile.add(editCardDetails);
        profile.add(signOut);

        JMenu orders = new JMenu("Orders");
        JMenuItem viewOrders = new JMenuItem("View Orders");

        orders.add(viewOrders);

        JMenu exitMenu = new JMenu("Exit");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitMenu.add(exit);

        add(profile);
        add(orders);
        add(exitMenu);
    }
}
