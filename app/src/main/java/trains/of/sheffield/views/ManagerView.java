package trains.of.sheffield.views;

import javax.swing.*;

import trains.of.sheffield.models.DatabaseOperations;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; // Make sure to import List

// TODO: make slightly prettier

public class ManagerView extends JFrame {
    private JTextField staffEmailField;
    private JButton addButton, removeButton;
    private JList<String> staffList; 
    private DefaultListModel<String> listModel; 

    public ManagerView() {
        // TODO: make it look better
        setTitle("Manager Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        staffList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(staffList);

        JPanel inputPanel = new JPanel(new GridLayout(1, 3));
        staffEmailField = new JTextField();
        addButton = new JButton("Add Staff");
        removeButton = new JButton("Remove Staff");
        
        inputPanel.add(staffEmailField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        add(listScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        updateStaffList(); // Populate the list with current staff
        

        // Add button listener
addButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String staffEmail = JOptionPane.showInputDialog(ManagerView.this, 
                                                        "Enter Staff Member's Email:", 
                                                        "Add Staff", 
                                                        JOptionPane.PLAIN_MESSAGE);
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

// Remove button listener
removeButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedStaff = staffList.getSelectedValue();
        if (selectedStaff != null) {
            if (DatabaseOperations.removeStaffMember(selectedStaff)) {
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
    }
});

    }

    private void updateStaffList() {
        List<String> staffMembers = DatabaseOperations.getStaffMembers();
        listModel.clear(); 
        for (String email : staffMembers) {
            listModel.addElement(email);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ManagerView().setVisible(true);
            }
        });
    }
}
