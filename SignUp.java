import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignUp extends JFrame{
	JLabel usernamePrompt, pwPrompt1, pwPrompt2, intro, firstNamePrompt, secondNamePrompt, housePrompt, roadPrompt, cityPrompt, postCodePrompt, loginIntro, detailsIntro, adressIntro; // Creating variables
	JTextField usernameEnter, firstNameEnter, secondNameEnter, houseEnter, roadEnter, cityEnter, postCodeEnter;
	JPasswordField pwEnter1, pwEnter2;
	JButton cancel, signUp;
	JPanel loginDetails, loginFields, personalDetails, personalFields, address, addressFields, buttons;
	public SignUp() {
		setLayout(new GridLayout(5,1));
		
		intro = new JLabel("Please fill out this form and click \"Submit\"", SwingConstants.CENTER);
		add(intro);
		
		loginDetails = new JPanel(); // A panel to hold the user name objects
		loginDetails.setLayout(new GridLayout(2,1));

		UN_prompt = new JLabel("Enter a new username:  ", SwingConstants.RIGHT); // Shows user where to enter their user name
		loginDetails.add(UN_prompt);
		UN_enter = new JTextField("", 30); // Where to enter the user name
		loginDetails.add(UN_enter);
		PW_prompt1 = new JLabel("Enter a new password:  ",SwingConstants.RIGHT); // Shows user where to enter their password
		PW.add(PW_prompt1);
		PW_enter1 = new JPasswordField("", 30); // Where to enter the password
		PW.add(PW_enter1);
		PW_prompt2 = new JLabel("Retype your password:  ",SwingConstants.RIGHT); // Shows user where to retype their password
		PW.add(PW_prompt2);
		PW_enter2 = new JPasswordField("", 30); // Where to retype the password	
		PW.add(PW_enter2);
		add(PW);
		
		signUp = new JButton("Submit Details"); // Enters the details to be processed and saved
		event e = new event();
		signUp.addActionListener(e);
		add(signUp);
	}
	public class event implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			trySignUp(); // Attempts to sign the user up to the database
		}
	}
	public void trySignUp() {
		String user = UN_enter.getText(); // Fetches the username
		if((PW_enter1.getPassword()).equals(PW_enter2.getPassword())) { // Checks passwords are the same
			try {
				String uName = UN_enter.getText();
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = null; // Initialising connection
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal planner", "user_access", "User1"); // Setting up a database connection
				Statement st = con.createStatement();
				String r = "INSERT INTO `users` (`USER_ID`, `uname`, `pword`) VALUES (NULL, '"+UN_enter.getText()+"', '"+PW_enter1.getPassword()+"')"; // Enters details into the database
				int results = st.executeUpdate(r);
				con.close(); // Ending connection
				dispose();
				GUILoader.loginWindow(); // Takes them back to the login screen
				GUILoader.alertWindow("Account opened successfully."); // Alerts them that they have an account
			} catch(Exception ex) {
				GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
			}
		} else {
			intro.setText("The passwords you have entered don't match! Please try again."); // Error message for when the passwords don't match
		}
	}
}