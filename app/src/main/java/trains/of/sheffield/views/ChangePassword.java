package trains.of.sheffield.views;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

import trains.of.sheffield.CurrentUser;
import trains.of.sheffield.GUILoader;
import trains.of.sheffield.User;
import trains.of.sheffield.models.DatabaseOperations;
import trains.of.sheffield.util.HashedPasswordGenerator;

public class ChangePassword extends JFrame{
	JLabel intro, passwordIntro, originalPasswordPrompt, pwPrompt1, pwPrompt2; // Creating variables
    JPasswordField originalPasswordEnter, pwEnter1, pwEnter2;
	JButton cancel, submit;
	JPanel mainView, introPanel, passwordPanel, passwordFields, buttons;
	JScrollPane scroll;
	public ChangePassword() {
		setLayout(new FlowLayout()); // Sets the layout of the window
		mainView = new JPanel(); // A panel to hold the main view
		mainView.setLayout(new GridLayout(3,1));
		mainView.setPreferredSize(new Dimension(750, 600)); // Adjust the size as needed
		scroll = new JScrollPane(mainView);

		introPanel = new JPanel(); // A panel to hold the intro message
		introPanel.setLayout(new GridLayout(1,1));
		intro = new JLabel("Please fill out this form and click \"Submit\"", SwingConstants.CENTER);
		introPanel.add(intro);
		introPanel.setSize(200, 10);
		mainView.add(introPanel);

		passwordPanel = new JPanel(); // A panel to hold the password details title
        passwordPanel.setLayout(new GridLayout(2,1));
        passwordIntro = new JLabel ("Password Details", SwingConstants.CENTER);
        passwordPanel.add(passwordIntro);
        passwordFields = new JPanel(); // A panel to hold the password details entry fields
        passwordFields.setLayout(new GridLayout(3,2));
        originalPasswordPrompt = new JLabel("Enter your original password:  ", SwingConstants.RIGHT); // Shows user where to enter their user name
        passwordFields.add(originalPasswordPrompt);
        originalPasswordEnter = new JPasswordField("", 256); // Where to enter the user name
        passwordFields.add(originalPasswordEnter);
        pwPrompt1 = new JLabel("Enter a new password:  ",SwingConstants.RIGHT); // Shows user where to enter their password
        passwordFields.add(pwPrompt1);
        pwEnter1 = new JPasswordField("", 256); // Where to enter the password
        passwordFields.add(pwEnter1);
        pwPrompt2 = new JLabel("Re-enter your password:  ",SwingConstants.RIGHT); // Shows user where to retype their password
        passwordFields.add(pwPrompt2);
        pwEnter2 = new JPasswordField("", 256); // Where to retype the password
        passwordFields.add(pwEnter2);
        passwordPanel.add(passwordFields);
        mainView.add(passwordPanel);

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

	public class ActionCancel implements ActionListener {
		public void actionPerformed(ActionEvent cancel) { // This takes the user to a temporary window to create an account
			dispose();
		}
	}
	public class ActionSubmit implements ActionListener {
		public void actionPerformed(ActionEvent submit) { // This takes the user to a temporary window to create an account
            if (HashedPasswordGenerator.hashPassword(originalPasswordEnter.getPassword()).equals(CurrentUser.getPasswordHash())){
                if (Arrays.equals(pwEnter1.getPassword(), pwEnter2.getPassword())) {
                    if (DatabaseOperations.tryLogIn(CurrentUser.getCurrentUser().getEmail(), originalPasswordEnter.getPassword())) {
                        DatabaseOperations.updatePassword(pwEnter1.getPassword());
                        dispose();
                    }
                } else {
                    GUILoader.alertWindow("Error: Passwords do not match");
                }
            } else {
                GUILoader.alertWindow("Error: Original password is incorrect");
            }
		}
	}
}