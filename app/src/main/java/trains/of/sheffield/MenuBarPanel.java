package trains.of.sheffield;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarPanel extends JMenuBar {

    public MenuBarPanel() {
        JMenu profile = new JMenu("Profile");
        JMenuItem editProfile = new JMenuItem("Edit Profile");
        JMenuItem editCardDetails = new JMenuItem("Edit Card Details");
        JMenuItem signOut = new JMenuItem("Sign Out");

        profile.add(editProfile);
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
