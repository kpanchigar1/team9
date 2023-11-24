package trains.of.sheffield.views;

import trains.of.sheffield.Status;

import javax.swing.*;

public class StaffPendingOrders extends JFrame {
    public StaffPendingOrders(){
        StaffOrdersPanel pendingOrders = new StaffOrdersPanel(Status.FULFILLED);
        // add edit and delete buttons

    }

    public static void main(String[] args) {
        new StaffPendingOrders();
    }
}
