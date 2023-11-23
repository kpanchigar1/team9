package trains.of.sheffield;


import javax.swing.*;
import java.awt.*;

// TODO: Do database operations
// TODO: add sample data
// TODO: add action listener to submit button
// TODO: add back button
// TODO: add action listener to back button
// TODO: password hashing

public class CardDetailsPage extends JFrame{
    private JPanel panel, expiryDatePanel;
    private JLabel cardNameLabel, cardNumberLabel, expiryDateLabel, cvvLabel, slash;
    private JTextField cardNameField, cardNumberField, monthField, yearField;
    private JPasswordField cvvField;
    private JButton submit, back;
    public CardDetailsPage (){
        super("Card Details");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);

        panel = new JPanel();
        this.add(panel);
        panel.setLayout(new GridLayout(5, 2));

        cardNameLabel = new JLabel("Card Name:");
        cardNameField = new JTextField(20);
        panel.add(cardNameLabel);
        panel.add(cardNameField);

        cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField(16);
        panel.add(cardNumberLabel);
        panel.add(cardNumberField);

        expiryDateLabel = new JLabel("Expiry Date (MMYY):");
        expiryDatePanel = new JPanel();
        expiryDatePanel.setLayout(new GridLayout(1, 3));
        monthField = new JTextField(2);
        expiryDatePanel.add(monthField);
        slash = new JLabel("/");
        slash.setHorizontalAlignment(JLabel.CENTER);
        expiryDatePanel.add(slash);
        yearField = new JTextField(2);
        expiryDatePanel.add(yearField);
        panel.add(expiryDateLabel);
        panel.add(expiryDatePanel);

        cvvLabel = new JLabel("CVV:");
        cvvField = new JPasswordField(3);
        panel.add(cvvLabel);
        panel.add(cvvField);

        back = new JButton("Back");
        submit = new JButton("Submit");
        panel.add(back);
        panel.add(submit);

        setVisible(true);
    }

    private void fillDetails() {
        if(!CurrentUser.getCardDetail().equals(null)) {
            Card card = CurrentUser.getCardDetail();
            cardNameField.setText(card.getCardName());
            cardNumberField.setText(card.getCardNumber());
            monthField.setText(card.getExpiryDate().substring(0, 2));
            yearField.setText(card.getExpiryDate().substring(2, 4));
            cvvField.setText(card.getCvv());
        }
    }

    public static void main(String[] args) {
        CardDetailsPage cardDetailsPage = new CardDetailsPage();
    }
}