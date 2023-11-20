package trains.of.sheffield;
import trains.of.sheffield.models.DatabaseOperations;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {
    JLabel title, instructions, UN_prompt, PW_prompt; // Creating variables
	JTextField UN_enter;
	JPasswordField PW_enter;
	JButton login, signUp;
	JPanel UN, PW, buttons;
    
    public Login() {
        setLayout(new GridLayout(5,2)); // Setting out the layout
		
		title = new JLabel("Welcome to Trains of Sheffield", SwingConstants.CENTER); // A welcome message
		add(title);
		
		instructions = new JLabel("Please log in bellow", SwingConstants.CENTER); // Simple instructions
		add(instructions);
		
		UN = new JPanel(); // A panel to hold the user name objects
		UN.setLayout(new GridLayout(1,2));
		UN_prompt = new JLabel("email:  ", SwingConstants.RIGHT); // Shows user where to enter their user name
		UN.add(UN_prompt);
		UN_enter = new JTextField("", 30); // Where to enter the user name
		UN_enter.setBackground(new Color(147,198,249));
		UN.add(UN_enter);
		UN.setBackground(new Color(102,153,205));
		add(UN);
		
		PW = new JPanel(); // A panel to hold the password objects
		PW.setLayout(new GridLayout(1,2));
		PW_prompt = new JLabel("Password:  ",SwingConstants.RIGHT); // Shows user where to enter their password
		PW.add(PW_prompt);
		PW_enter = new JPasswordField("", 64); // Where to enter the password
		PW_enter.setBackground(new Color(147,198,249));
		PW.add(PW_enter);
		PW.setBackground(new Color(102,153,205));
		add(PW);
		
		buttons = new JPanel(); // A panel to hold the buttons to be pressed to either:
		buttons.setLayout(new GridLayout(1,2));
		signUp = new JButton("Sign up"); // create a new account
		actionSU SU = new actionSU();
		signUp.addActionListener(SU);
		signUp.setBackground(new Color(147,198,249));
		signUp.setBorder(BorderFactory.createBevelBorder(0));
		buttons.add(signUp);
		login = new JButton("Login"); // or login with an existing account
		actionLI LI = new actionLI();
		login.addActionListener(LI);
		login.setBackground(new Color(147,198,249));
		login.setBorder(BorderFactory.createBevelBorder(0));
		buttons.add(login);
		buttons.setBackground(new Color(102,153,205));
		add(buttons);
    }
	public class actionLI implements ActionListener {
		public void actionPerformed(ActionEvent LI) { // This compares the entered details with what is in the users table
			boolean loggedIn = DatabaseOperations.tryLogIn(UN_enter.getText(), PW_enter.getPassword());
			if (loggedIn) {
				dispose();
			}
		}
	}
	public class actionSU implements ActionListener {
		public void actionPerformed(ActionEvent SU) { // This takes the user to a temporary window to create an account
			dispose();
			GUILoader.signupWindow();
		}
	}

}
