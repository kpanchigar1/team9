package trains.of.sheffield;


import javax.swing.*;
import java.awt.*;
// TODO: fix layout
// TODO: Do database operations
// TODO: add sample data
// TODO: add action listener to submit button
// TODO: add back button
public class CardDetailsPage extends JFrame{
    private JTextField cardNameField;
    private JTextField cardNumberField;
    private JTextField expiryDateField;
    private JPasswordField cvvField;
    public CardDetailsPage (){
        super("Card Details");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);

        JPanel panel = new JPanel();
        this.add(panel);
        panel.setLayout(new GridLayout(4, 2));

        JLabel cardNameLabel = new JLabel("Card Name:");
        cardNameField = new JTextField(20);
        cardNumberField = new JTextField(16);
        expiryDateField = new JTextField(4);
        cvvField = new JPasswordField(3);
        JLabel cardNumberLabel = new JLabel("Card Number:");
        JLabel expiryDateLabel = new JLabel("Expiry Date (MMYY):");
        JLabel cvvLabel = new JLabel("CVV:");

        JButton submit = new JButton("Submit");

        panel.add(cardNameLabel);
        panel.add(cardNameField);
        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(expiryDateLabel);
        panel.add(expiryDateField);
        panel.add(cvvLabel);
        panel.add(cvvField);
        panel.add(submit);

        setVisible(true);
    }
}