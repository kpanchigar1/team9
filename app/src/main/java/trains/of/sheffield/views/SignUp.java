package trains.of.sheffield.views;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

import trains.of.sheffield.GUILoader;
import trains.of.sheffield.models.DatabaseOperations;

/**
 * This class is used to create a sign-up window for the user to enter their details.
 */
public class SignUp extends JFrame{
	JLabel emailPrompt, pwPrompt1, pwPrompt2, intro, firstNamePrompt, secondNamePrompt, housePrompt, roadPrompt, cityPrompt, postCodePrompt, loginIntro, detailsIntro, addressIntro; // Creating variables
	JTextField emailEnter, firstNameEnter, secondNameEnter, houseEnter, roadEnter, cityEnter, postCodeEnter;
	JPasswordField pwEnter1, pwEnter2;
	JButton cancel, submit;
	JPanel mainView, introPanel, loginDetails, loginFields, personalDetails, personalFields, address, addressFields, buttons;
	JScrollPane scroll;

	/**
	 * This constructor creates the sign-up window.
	 */
	public SignUp() {
		setLayout(new FlowLayout()); // Sets the layout of the window
		mainView = new JPanel(); // A panel to hold the main view
		mainView.setLayout(new GridLayout(5,1));
		mainView.setPreferredSize(new Dimension(750, 600)); // Adjust the size as needed
		scroll = new JScrollPane(mainView);

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
		loginFields.setLayout(new GridLayout(3,2));
		emailPrompt = new JLabel("Enter an email address:  ", SwingConstants.RIGHT); // Shows user where to enter their user name
		loginFields.add(emailPrompt);
		emailEnter = new JTextField("", 50); // Where to enter the user name
		loginFields.add(emailEnter);
		pwPrompt1 = new JLabel("Enter a new password:  ",SwingConstants.RIGHT); // Shows user where to enter their password
		loginFields.add(pwPrompt1);
		pwEnter1 = new JPasswordField("", 256); // Where to enter the password
		loginFields.add(pwEnter1);
		pwPrompt2 = new JLabel("Re-enter your password:  ",SwingConstants.RIGHT); // Shows user where to retype their password
		loginFields.add(pwPrompt2);
		pwEnter2 = new JPasswordField("", 256); // Where to retype the password	
		loginFields.add(pwEnter2);
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
		roadPrompt = new JLabel("Enter your road name:  ", SwingConstants.RIGHT); // Shows user where to enter their road name
		addressFields.add(roadPrompt);
		roadEnter = new JTextField("", 100); // Where to enter the road name
		addressFields.add(roadEnter);
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


		add(scroll);
	}

	/**
	 * This takes the user back to the login window
	 */
	public class ActionCancel implements ActionListener {
		public void actionPerformed(ActionEvent cancel) { // This takes the user to a temporary window to create an account
			dispose();
			GUILoader.loginWindow();
		}
	}

	/**
	 * This submits the new user's details and takes the user back to the login window
	 */
	public class ActionSubmit implements ActionListener {
		public void actionPerformed(ActionEvent submit) { // This takes the user to a temporary window to create an account
			if (Arrays.equals(pwEnter1.getPassword(), pwEnter2.getPassword())) {
				String email = emailEnter.getText();
				char[] password = pwEnter1.getPassword();
				String firstName = firstNameEnter.getText();
				String secondName = secondNameEnter.getText();
				String houseNumber = houseEnter.getText();
				String streetName = roadEnter.getText();
				String city = cityEnter.getText();
				String postCode = postCodeEnter.getText();
				if(email.equals("") || password.equals("") || firstName.equals("") || secondName.equals("") || houseNumber.equals("") || streetName.equals("") || city.equals("") || postCode.equals("")) {
					GUILoader.alertWindow("Error: Please fill out all fields");
				} else {
					DatabaseOperations.trySignUp(password, firstName, secondName, email, houseNumber, streetName, city, postCode);
					dispose();
					GUILoader.loginWindow();
				}
			} else {
				GUILoader.alertWindow("Error: Passwords do not match");
			}
		}
	}
}