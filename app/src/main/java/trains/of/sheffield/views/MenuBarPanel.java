package trains.of.sheffield.views;

import javax.swing.*;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarPanel extends JMenuBar {
    // TODO: fix sign out so that it closes the window and opens the login window
    JMenu profile, orders, exitMenu;
    JMenuItem editProfile, changePassword, editCardDetails, viewOrders, exit;
    public MenuBarPanel() {
        profile = new JMenu("Profile");
        editProfile = new JMenuItem("Edit Profile");
        changePassword = new JMenuItem("Change Password");
        editCardDetails = new JMenuItem("Edit Card Details");

        editProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILoader.changeDetailsWindow();
            }
        });
        profile.add(editProfile);

        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILoader.changePasswordWindow();
            }
        });
        profile.add(changePassword);

        editCardDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILoader.cardDetailsWindow();
            }
        });
        profile.add(editCardDetails);

        orders = new JMenu("Orders");
        viewOrders = new JMenuItem("View Orders");
        viewOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILoader.viewBasketWindow();
            }
        });
        orders.add(viewOrders);

        exitMenu = new JMenu("Exit");
        exit = new JMenuItem("Exit");
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

    public void addSignOut(JMenuItem signOut) {
        profile.add(signOut);
    }
}
