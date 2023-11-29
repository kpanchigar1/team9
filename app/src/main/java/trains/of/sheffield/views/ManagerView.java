package trains.of.sheffield.views;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.User;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerView extends JFrame {
    private JTextField staffEmailField;
    private JLabel staffEmailLabel;
    private JButton addButton, removeButton, backButton;
    private JList<String> staffList;
    private DefaultListModel<String> listModel;

    public ManagerView() {
        setTitle("Manager Dashboard");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        listModel = new DefaultListModel<>();
        staffList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(staffList);

        // Title Label
        JLabel titleLabel = new JLabel("List of Staff Members");

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        staffEmailLabel = new JLabel("Staff Email: ");
        staffEmailField = new JTextField(20);
        addButton = new JButton("Add Staff");
        removeButton = new JButton("Remove Staff");
        backButton = new JButton("Back");

        inputPanel.add(staffEmailLabel);
        inputPanel.add(staffEmailField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(backButton);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(listScrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);

        updateStaffList();
        setVisible(true);

// Add button listener
    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String staffEmail = staffEmailField.getText();
            if (staffEmail != null && !staffEmail.trim().isEmpty()) {
                if (DatabaseOperations.addStaffMember(staffEmail)) {
                    updateStaffList(); // Refresh the list after successful addition
                    JOptionPane.showMessageDialog(ManagerView.this,
                                                  "Staff member added successfully.",
                                                  "Success",
                                                  JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ManagerView.this,
                                                  "Failed to add staff member.",
                                                  "Error",
                                                  JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    });


    removeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedStaff = staffList.getSelectedValue();
            if (selectedStaff != null) {
                int choice = JOptionPane.showConfirmDialog(
                        ManagerView.this,
                        "Are you sure you want to remove the selected staff member?",
                        "Confirm Removal",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    if (DatabaseOperations.removeStaffMember(selectedStaff.substring(selectedStaff.indexOf("(") + 1, selectedStaff.indexOf(")")))) {
                        updateStaffList(); // Refresh the list after successful removal
                        JOptionPane.showMessageDialog(ManagerView.this,
                                "Staff member removed successfully.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ManagerView.this,
                                "Failed to remove staff member.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(
                        ManagerView.this,
                        "Please select a staff member to remove.",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    });

    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            GUILoader.staffDashboardWindow();
        }
    });

}
    /**
     * Updates the staff list with the current staff members
     */
    private void updateStaffList() {
        List<User> staffMembers = DatabaseOperations.getStaffMembers();
        listModel.clear(); 
        for (User staffMember : staffMembers) {
            listModel.addElement(staffMember.getForename() + " " + staffMember.getSurname() + " (" + staffMember.getEmail() + ")");
        }
    }
}
