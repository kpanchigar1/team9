package trains.of.sheffield.views;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.User;
import trains.of.sheffield.models.DatabaseOperations;

/**
 * This class is used to create the change details window which allows the user to change their details.
 */
public class ChangeDetails extends JFrame{
	JLabel emailPrompt, intro, firstNamePrompt, secondNamePrompt, housePrompt, streetPrompt, cityPrompt, postCodePrompt, loginIntro, detailsIntro, addressIntro; // Creating variables
	JTextField emailEnter, firstNameEnter, secondNameEnter, houseEnter, streetEnter, cityEnter, postCodeEnter;
	JButton cancel, submit;
	JPanel mainView, introPanel, loginDetails, loginFields, personalDetails, personalFields, address, addressFields, buttons;
	JScrollPane scroll;
	public ChangeDetails() {
		setLayout(new FlowLayout()); // Sets the layout of the window
		mainView = new JPanel(); // A panel to hold the main view
		mainView.setLayout(new GridLayout(5,1));
		mainView.setPreferredSize(new Dimension(750, 600)); // Adjust the size as needed
		scroll = new JScrollPane(mainView);

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

		introPanel = new JPanel(); // A panel to hold the intro message
		introPanel.setLayout(new GridLayout(1,1));
		intro = new JLabel("Please fill out this form and click \"Submit\"", SwingConstants.CENTER);
		introPanel.add(intro);
		introPanel.setSize(200, 10);
		mainView.add(introPanel);

		loginDetails = new JPanel(); // A panel to hold the login details title
		loginDetails.setLayout(new GridLayout(2,1));
		loginIntro = new JLabel ("Login Details", SwingConstants.CENTER);
		loginDetails.add(loginIntro);
		loginFields = new JPanel(); // A panel to hold the login details entry fields
		loginFields.setLayout(new GridLayout(1,2));
		emailPrompt = new JLabel("Enter an email address:  ", SwingConstants.RIGHT); // Shows user where to enter their user name
		loginFields.add(emailPrompt);
		emailEnter = new JTextField("", 50); // Where to enter the user name
		loginFields.add(emailEnter);
		loginDetails.add(loginFields);
		mainView.add(loginDetails);

		personalDetails = new JPanel(); // A panel to hold the personal details
		personalDetails.setLayout(new GridLayout(2,1));
		detailsIntro = new JLabel ("Personal Details", SwingConstants.CENTER);
		personalDetails.add(detailsIntro);
		personalFields = new JPanel(); // A panel to hold the personal details entry fields
		personalFields.setLayout(new GridLayout(2,2));
		firstNamePrompt = new JLabel("Enter your first name:  ", SwingConstants.RIGHT); // Shows user where to enter their first name
		personalFields.add(firstNamePrompt);
		firstNameEnter = new JTextField("", 50); // Where to enter the first name
		personalFields.add(firstNameEnter);
		secondNamePrompt = new JLabel("Enter your second name:  ", SwingConstants.RIGHT); // Shows user where to enter their second name
		personalFields.add(secondNamePrompt);
		secondNameEnter = new JTextField("", 50); // Where to enter the second name
		personalFields.add(secondNameEnter);
		personalDetails.add(personalFields);
		mainView.add(personalDetails);

		address = new JPanel(); // A panel to hold the address details
		address.setLayout(new GridLayout(2,1));
		addressIntro = new JLabel("Address", SwingConstants.CENTER);
		address.add(addressIntro);
		addressFields = new JPanel(); // A panel to hold the address details entry fields
		addressFields.setLayout(new GridLayout(4,2));
		housePrompt = new JLabel("Enter your house number:  ", SwingConstants.RIGHT); // Shows user where to enter their house number
		addressFields.add(housePrompt);
		houseEnter = new JTextField("", 5); // Where to enter the house number
		addressFields.add(houseEnter);
		streetPrompt = new JLabel("Enter your street name:  ", SwingConstants.RIGHT); // Shows user where to enter their street name
		addressFields.add(streetPrompt);
		streetEnter = new JTextField("", 100); // Where to enter the street name
		addressFields.add(streetEnter);
		cityPrompt = new JLabel("Enter your city:  ", SwingConstants.RIGHT); // Shows user where to enter their city
		addressFields.add(cityPrompt);
		cityEnter = new JTextField("", 100); // Where to enter the city
		addressFields.add(cityEnter);
		postCodePrompt = new JLabel("Enter your post code:  ", SwingConstants.RIGHT); // Shows user where to enter their post code
		addressFields.add(postCodePrompt);
		postCodeEnter = new JTextField("", 10); // Where to enter the post code
		addressFields.add(postCodeEnter);
		address.add(addressFields);
		mainView.add(address);

		buttons = new JPanel(); // A panel to hold the buttons to be pressed to either: return to login or submit details
		buttons.setLayout(new GridLayout(1, 2));
		cancel = new JButton(); // Returns to the login window
		ActionCancel actionCancel = new ActionCancel();
		cancel.addActionListener(actionCancel);
		cancel.setText("Cancel"); // Set the text
		buttons.add(cancel);
		submit = new JButton(); // Enters the details to be processed and saved
		submit.setText("Submit"); // Set the text
		ActionSubmit actionSubmit = new ActionSubmit();
		submit.addActionListener(actionSubmit);
		buttons.add(submit);
		mainView.add(buttons);

        fillDetails();
		add(scroll);
	}

	/**
	 * This method fills the details with the user into the text fields.
	 */
	private void fillDetails() {
        User user = CurrentUser.getCurrentUser();
        emailEnter.setText(user.getEmail());
        firstNameEnter.setText(user.getForename());
        secondNameEnter.setText(user.getSurname());
        houseEnter.setText(user.getAddress().getHouseNumber());
        streetEnter.setText(user.getAddress().getStreetName());
        cityEnter.setText(user.getAddress().getCity());
        postCodeEnter.setText(user.getAddress().getPostCode());
    }

	public class ActionCancel implements ActionListener {
		public void actionPerformed(ActionEvent cancel) { // This takes the user to a temporary window to create an account
			dispose();
		}
	}
	public class ActionSubmit implements ActionListener {
		public void actionPerformed(ActionEvent submit) { // This takes the user to a temporary window to create an account
            String email = emailEnter.getText();
            String firstName = firstNameEnter.getText();
            String secondName = secondNameEnter.getText();
            String houseNumber = houseEnter.getText();
            String streetName = streetEnter.getText();
            String city = cityEnter.getText();
            String postCode = postCodeEnter.getText();
            if (email.equals("") || firstName.equals("") || secondName.equals("") || houseNumber.equals("") || streetName.equals("") || city.equals("") || postCode.equals("")) {
                GUILoader.alertWindow("Please fill out all fields");
            } else {
                DatabaseOperations.updateDetails(firstName, secondName, email, houseNumber, streetName, city, postCode);
                dispose();
            }
		}
	}
}