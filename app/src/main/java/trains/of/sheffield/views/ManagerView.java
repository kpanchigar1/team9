package trains.of.sheffield.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerView extends JFrame {
    private JTextField staffEmailField;
    private JButton addButton, removeButton;
    private JList<String> staffList; 
    private DefaultListModel<String> listModel; 

    public ManagerView() {
        setTitle("Manager Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for staff list
        listModel = new DefaultListModel<>();
        staffList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(staffList);

        // Adding/removing staff panel 
        JPanel inputPanel = new JPanel(new GridLayout(1, 3));
        staffEmailField = new JTextField();
        addButton = new JButton("Add Staff");
        removeButton = new JButton("Remove Staff");
        
        inputPanel.add(staffEmailField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        // Adding components to the frame
        add(listScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String staffEmail = staffEmailField.getText();
                // Implement logic to add staff
                listModel.addElement(staffEmail); 
                staffEmailField.setText(""); // Clear input field
            }
        });

        // Remove Button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStaff = staffList.getSelectedValue();
                if (selectedStaff != null) {
                    // Implement logic to remove selected staff
                    listModel.removeElement(selectedStaff); // Update UI list
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ManagerView().setVisible(true);
            }
        });
    }
}