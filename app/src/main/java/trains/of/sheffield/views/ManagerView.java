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
                String staffEmail = JOptionPane.showInputDialog(ManagerView.this, 
                                                                "Enter Staff Member's Email:", 
                                                                "Add Staff", 
                                                                JOptionPane.PLAIN_MESSAGE);
                if (staffEmail != null && !staffEmail.trim().isEmpty()) {
                    // Implement logic to add staff with the given email
                    listModel.addElement(staffEmail); // Update UI list
                } else {
                    // Handle case where no email was entered or dialog was cancelled
                    JOptionPane.showMessageDialog(ManagerView.this, 
                                                  "No email entered. Staff member not added.", 
                                                  "Info", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                }
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