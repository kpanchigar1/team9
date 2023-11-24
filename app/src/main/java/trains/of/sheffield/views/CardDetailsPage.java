package trains.of.sheffield.views;
import trains.of.sheffield.Card;
import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.models.DatabaseOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// TODO: Do database operations
// TODO: add sample data
// TODO: add action listener to submit button
// TODO: add back button
// TODO: add action listener to back button
// TODO: turn cvv back to JPasswordField

public class CardDetailsPage extends JFrame{
    private JPanel panel, expiryDatePanel;
    private JLabel cardNameLabel, cardNumberLabel, expiryDateLabel, cvvLabel, slash;
    private JTextField cardNameField, cardNumberField, monthField, yearField, cvvField;
    private JButton submit, back;
    private Card card = CurrentUser.getCardDetail();
    public CardDetailsPage (){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);

        panel = new JPanel();
        this.add(panel);
        panel.setLayout(new GridLayout(5, 2));

        cardNameLabel = new JLabel("Card Name:");
        cardNameLabel.setHorizontalAlignment(JLabel.CENTER);
        cardNameField = new JTextField(20);
        panel.add(cardNameLabel);
        panel.add(cardNameField);

        cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setHorizontalAlignment(JLabel.CENTER);
        cardNumberField = new JTextField(16);
        panel.add(cardNumberLabel);
        panel.add(cardNumberField);

        expiryDateLabel = new JLabel("Expiry Date (MMYY):");
        expiryDateLabel.setHorizontalAlignment(JLabel.CENTER);
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

        cvvLabel = new JLabel("CVV (three numbers on the back of your card):");
        cvvLabel.setHorizontalAlignment(JLabel.CENTER);
        cvvField = new JPasswordField(3);
        panel.add(cvvLabel);
        panel.add(cvvField);

        back = new JButton("Back");
        ReturnToMenu returnToMenu = new ReturnToMenu();
        back.addActionListener(returnToMenu);
        submit = new JButton("Submit");
        SubmitDetails submitDetails = new SubmitDetails();
        submit.addActionListener(submitDetails);
        panel.add(back);
        panel.add(submit);

        fillDetails();
    }

    private void fillDetails() {
        if(card != null) {
            cardNameField.setText(card.getCardName());
            cardNumberField.setText(card.getCardNumber().toString());
            monthField.setText(card.getExpiryDate().toString().substring(0, 2));
            yearField.setText(card.getExpiryDate().toString().substring(2, 4));
            cvvField.setText(card.getCvv().toString());
        }
    }

    public class ReturnToMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    public class SubmitDetails implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cardName = cardNameField.getText();
            String cardNumber = cardNumberField.getText();
            String expiryDate = monthField.getText() + yearField.getText();
            String cvv = cvvField.getText();
            if(cardName.equals("") || cardNumber.equals("") || expiryDate.equals("") || cvv.equals("")) {
                GUILoader.alertWindow("Error: Please fill in all fields");
            } else {
                if(card == null) {
                    DatabaseOperations.tryCardDetails(cardName, cardNumber, expiryDate, cvv);
                } else {
                    DatabaseOperations.updateCardDetails(cardName, cardNumber, expiryDate, cvv, card.getCardNumber());
                }
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        CardDetailsPage cardDetailsPage = new CardDetailsPage();
    }
}