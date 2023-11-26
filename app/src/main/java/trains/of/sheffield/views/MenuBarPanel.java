package trains.of.sheffield.views;

import javax.swing.*;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarPanel extends JMenuBar {
    // TODO: make action listeners work
    JMenu profile, orders, exitMenu;
    JMenuItem editProfile, changePassword, editCardDetails, signOut, viewOrders, exit;
    public MenuBarPanel() {
        profile = new JMenu("Profile");
        editProfile = new JMenuItem("Edit Profile");
        changePassword = new JMenuItem("Change Password");
        editCardDetails = new JMenuItem("Edit Card Details");
        signOut = new JMenuItem("Sign Out");

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

        signOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentUser.setUser(null);
                GUILoader.loginWindow();
            }
        });
        profile.add(signOut);

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
}
