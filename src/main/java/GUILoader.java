import javax.swing.*;
import java.awt.*;
public class GUILoader {
    public static void loginWindow() {
		Login loginGUI = new Login(); // Creates a new instance of the GUI
		loginGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ends the program when the user closes the window
		loginGUI.pack(); // Packed to fit
		loginGUI.setVisible(true); // Window can be seen
		loginGUI.setTitle("Trains of Sheffield - Login"); // Title of window
		loginGUI.getContentPane().setBackground(new Color(102,153,205));
	}
    public static void signupWindow() {
		SignUp signUpWindowGUI = new SignUp();
		signUpWindowGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signUpWindowGUI.pack();
		signUpWindowGUI.setVisible(true);
		signUpWindowGUI.setTitle("Trains of Sheffield - New Account");
		signUpWindowGUI.getContentPane().setBackground(new Color(102,153,205));
	}
    public static void alertWindow(String message) {
		Alert alertWindowGUI = new Alert(message);
		alertWindowGUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Doesn't close the program, just closes the window 
		alertWindowGUI.pack();
		alertWindowGUI.setVisible(true);
		alertWindowGUI.setTitle("ALERT");
		alertWindowGUI.getContentPane().setBackground(new Color(102,153,205));
	}
}
