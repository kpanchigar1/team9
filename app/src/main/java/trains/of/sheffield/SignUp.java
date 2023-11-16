package trains.of.sheffield;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignUp extends JFrame{
	JLabel emailPrompt, pwPrompt1, pwPrompt2, intro, firstNamePrompt, secondNamePrompt, housePrompt, roadPrompt, cityPrompt, postCodePrompt, loginIntro, detailsIntro, addressIntro; // Creating variables
	JTextField emailEnter, firstNameEnter, secondNameEnter, houseEnter, roadEnter, cityEnter, postCodeEnter;
	JPasswordField pwEnter1, pwEnter2;
	JButton cancel, signUp;
	JPanel loginDetails, loginFields, personalDetails, personalFields, address, addressFields, buttons;
	JScrollPane scroll;
	public SignUp() {
		setLayout(new FlowLayout()); // Sets the layout of the window
		add(scroll = new JScrollPane());
		intro = new JLabel("Please fill out this form and click \"Submit\"", SwingConstants.CENTER);
		scroll.add(intro);
		
		loginDetails = new JPanel(); // A panel to hold the login details title
		loginDetails.setLayout(new GridLayout(2,1));
		loginIntro = new JLabel ("Login Details");
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
		scroll.add(loginDetails);

		
		signUp = new JButton("Submit Details"); // Enters the details to be processed and saved
		scroll.add(signUp);
	}
}