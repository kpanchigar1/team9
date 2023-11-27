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
		PreviousOrdersPanel previousOrdersGUI = new PreviousOrdersPanel();
		previousOrdersGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		previousOrdersGUI.pack();
		previousOrdersGUI.setVisible(true);
		previousOrdersGUI.setTitle("Trains of Sheffield - Previous Orders");
		previousOrdersGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	public static void viewBasketWindow() {
		ViewOrders viewBasketGUI = new ViewOrders();
		viewBasketGUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		viewBasketGUI.pack();
		viewBasketGUI.setVisible(true);
		viewBasketGUI.setTitle("Trains of Sheffield - View Basket");
		viewBasketGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}
    public static void cardDetailsWindow() {
		CardDetailsPage cardDetailsGUI = new CardDetailsPage();
		cardDetailsGUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		cardDetailsGUI.pack();
		cardDetailsGUI.setVisible(true);
		cardDetailsGUI.setTitle("Trains of Sheffield - Card Details");
		cardDetailsGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
    }

	public static void changeDetailsWindow() {
		ChangeDetails changeDetailsGUI = new ChangeDetails();
		changeDetailsGUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		changeDetailsGUI.pack();
		changeDetailsGUI.setVisible(true);
		changeDetailsGUI.setTitle("Trains of Sheffield - Change Details");
		changeDetailsGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	public static void orderLinesWindow(Order order, StaffConfirmedOrders staffConfirmedOrders) {
		OrderLinesWindow orderLinesGUI = new OrderLinesWindow(order, staffConfirmedOrders);
	}

	public static void changePasswordWindow() {
		ChangePassword changePasswordGUI = new ChangePassword();
		changePasswordGUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		changePasswordGUI.pack();
		changePasswordGUI.setVisible(true);
		changePasswordGUI.setTitle("Trains of Sheffield - Change Password");
		changePasswordGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	public static void pendingOrdersWindow() {
		StaffConfirmedOrders pendingOrders = new StaffConfirmedOrders();
	}

	public static void staffConfirmedOrdersWindow() {
		StaffConfirmedOrders staffConfirmedOrdersGUI = new StaffConfirmedOrders();
	}

	public static void addNewProductWindow(String productType) {
		AddNewProduct addNewProductGUI = new AddNewProduct(productType);
	}
}
