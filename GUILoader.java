import javax.swing.*;
import java.awt.*;
public class GUILoader {
    public static void loginWindow() {
		Login gui = new Login(); // Creates a new instance of the GUI
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ends the program when the user closes the window
		gui.pack(); // Packed to fit
		gui.setVisible(true); // Window can be seen
		gui.setTitle("Meal PLanner - Login"); // Title of window
		gui.getContentPane().setBackground(new Color(102,153,205));
	}
    public static void signupWindow() {
		SignUp signUpWindow = new SignUp();
		signUpWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signUpWindow.pack();
		signUpWindow.setVisible(true);
		signUpWindow.setTitle("Meal Planner - New Account");
	}
    public static void alertWindow(String message) {
		Alert alertWindowGUI = new Alert(message);
		alertWindowGUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Doesn't close the program, just closes the window 
		alertWindowGUI.pack();
		alertWindowGUI.setVisible(true);
		alertWindowGUI.setTitle("ALERT");
	}
}
