package trains.of.sheffield;
import trains.of.sheffield.views.*;

import javax.swing.*;

public class GUILoader {
    public static void loginWindow() {
		Login loginGUI = new Login(); // Creates a new instance of the GUI
		loginGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ends the program when the user closes the window
		loginGUI.pack(); // Packed to fit
		loginGUI.setVisible(true); // Window can be seen
		loginGUI.setTitle("Trains of Sheffield - Login"); // Title of window
		loginGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}
    public static void signupWindow() {
		SignUp signUpWindowGUI = new SignUp();
		signUpWindowGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signUpWindowGUI.pack();
		signUpWindowGUI.setVisible(true);
		signUpWindowGUI.setTitle("Trains of Sheffield - New Account");
		signUpWindowGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}
    public static void alertWindow(String message) {
		Alert alertWindowGUI = new Alert(message);
		alertWindowGUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Doesn't close the program, just closes the window 
		alertWindowGUI.pack();
		alertWindowGUI.setVisible(true);
		alertWindowGUI.setTitle("ALERT");
	}

	public static void mainMenuWindow() {
		MainMenu mainMenuGUI = new MainMenu(CurrentUser.getRole());
		mainMenuGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuGUI.pack();
		mainMenuGUI.setVisible(true);
		mainMenuGUI.setTitle("Trains of Sheffield - Main Menu");
		mainMenuGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	// public static void customerDashboardWindow() {
		//CustomerDashboard customerDashboardGUI = new CustomerDashboard();
	//}

	public static void staffDashboardWindow() {
		StaffDashboard staffDashboardGUI = new StaffDashboard();
	}

	public static void productStockPanelWindow(String productType) {
		ProductStockPanel productStockPanelGUI = new ProductStockPanel(productType);
	}

	public static void previousOrdersWindow() {
		PreviousOrders previousOrdersGUI = new PreviousOrders();
	}
    public static void cardDetailsWindow() {
		CardDetailsPage cardDetailsGUI = new CardDetailsPage();
		cardDetailsGUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		cardDetailsGUI.pack();
		cardDetailsGUI.setVisible(true);
		cardDetailsGUI.setTitle("Trains of Sheffield - Card Details");
		cardDetailsGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
    }
}
