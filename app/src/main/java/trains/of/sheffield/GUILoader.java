package trains.of.sheffield;
import trains.of.sheffield.views.*;

import javax.swing.*;
/**
 * This method is used to load the windows of the application
 */
public class GUILoader {

	/**
	 * This creates a new instance of the Login GUI
	 */
	public static void loginWindow() {

		Login loginGUI = new Login(); // Creates a new instance of the GUI
		loginGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ends the program when the user closes the window
		loginGUI.pack(); // Packed to fit
		loginGUI.setVisible(true); // Window can be seen
		loginGUI.setTitle("Trains of Sheffield - Login"); // Title of window
		loginGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	/**
	 * This creates a new instance of the SignUp GUI
	 */
    public static void signupWindow() {

		SignUp signUpWindowGUI = new SignUp();
		signUpWindowGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signUpWindowGUI.pack();
		signUpWindowGUI.setVisible(true);
		signUpWindowGUI.setTitle("Trains of Sheffield - New Account");
		signUpWindowGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	/**
	 * This creates a new instance of the Alert GUI
	 * @param message This is the message that will be displayed in the alert window
	 */
    public static void alertWindow(String message) {
		Alert alertWindowGUI = new Alert(message);
		alertWindowGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Doesn't close the program, just closes the window 
		alertWindowGUI.pack();
		alertWindowGUI.setVisible(true);
		alertWindowGUI.setTitle("ALERT");
	}

	/**
	 * This creates a new instance of the MainMenu GUI
	 */
	public static void mainMenuWindow() {
		MainMenu mainMenuGUI = new MainMenu(CurrentUser.getRole());
		mainMenuGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuGUI.pack();
		mainMenuGUI.setVisible(true);
		mainMenuGUI.setTitle("Trains of Sheffield - Main Menu");
		mainMenuGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	/**
	 * This creates a new instance of the Staff Dashboard GUI
	 */
	public static void staffDashboardWindow() {
		StaffDashboard staffDashboardGUI = new StaffDashboard();
		staffDashboardGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	/**
	 * This creates a new instance of the ProductStockPanel GUI which is used to display the stock for the staff users.
	 * @param productType This is the type of product that will be displayed in the window
	 */
	public static void productStockPanelWindow(String productType) {
		ProductStockPanel productStockPanelGUI = new ProductStockPanel(productType);
		productStockPanelGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	/**
	 * This creates a new instance of the PreviousOrders GUI, which is used to display the previous orders for the staff users.
	 */
	public static void previousOrdersWindow() {
		PreviousOrdersPanel previousOrdersGUI = new PreviousOrdersPanel();
		previousOrdersGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		previousOrdersGUI.pack();
		previousOrdersGUI.setVisible(true);
		previousOrdersGUI.setTitle("Trains of Sheffield - Previous Orders");
		previousOrdersGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	/**
	 * This creates a new instance of the ViewOrders GUI, which is used to display the basket for the customer users.
	 */
	public static void viewBasketWindow() {
		ViewOrders viewBasketGUI = new ViewOrders();
		viewBasketGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewBasketGUI.pack();
		viewBasketGUI.setVisible(true);
		viewBasketGUI.setTitle("Trains of Sheffield - View Basket");
		viewBasketGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	/**
	 * This creates a new instance of the CardDetails GUI, which is used to edit the card details for users.
	 */
	public static void cardDetailsWindow() {
		CardDetailsPage cardDetailsGUI = new CardDetailsPage();
		cardDetailsGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		cardDetailsGUI.pack();
		cardDetailsGUI.setVisible(true);
		cardDetailsGUI.setTitle("Trains of Sheffield - Card Details");
		cardDetailsGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
    }

	/**
	 * This creates a new instance of the ChangeDetails GUI, which is used to edit the personal details like
	 * name, email, address for users.
	 */
	public static void changeDetailsWindow() {
		ChangeDetails changeDetailsGUI = new ChangeDetails();
		changeDetailsGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		changeDetailsGUI.pack();
		changeDetailsGUI.setVisible(true);
		changeDetailsGUI.setTitle("Trains of Sheffield - Change Details");
		changeDetailsGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	/**
	 * This creates a new instance of the OrderLines GUI, which is used to display the order lines for the staff users.
	 * 				@param order This is the order that will be displayed in the window
	 *              @param staffConfirmedOrders This is the staffConfirmedOrders that will be displayed in the window
	 *              @param fromBasket This is the fromBasket that will be displayed in the window
	 */
	public static void orderLinesWindow(Order order, StaffConfirmedOrders staffConfirmedOrders, boolean fromBasket) {
		OrderLinesWindow orderLinesGUI = new OrderLinesWindow(order, staffConfirmedOrders, fromBasket);
		orderLinesGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		orderLinesGUI.pack();
		orderLinesGUI.setVisible(true);
		orderLinesGUI.setTitle("Order Lines");

	}

	/**
	 * This creates a new instance of the ChangePassword GUI, which is used to reset the password for users.
	 */
	public static void changePasswordWindow() {
		ChangePassword changePasswordGUI = new ChangePassword();
		changePasswordGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		changePasswordGUI.pack();
		changePasswordGUI.setVisible(true);
		changePasswordGUI.setTitle("Trains of Sheffield - Change Password");
		changePasswordGUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximises the window
	}

	/**
	 * This creates a new instance of the StaffConfirmedOrders GUI, which is used to display the confirmed orders for
	 * the staff users, which can be fulfilled.
	 */
	public static void pendingOrdersWindow() {
		StaffConfirmedOrders pendingOrders = new StaffConfirmedOrders();
		pendingOrders.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pendingOrders.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * This creates a new instance of the ProductDetailsPage GUI, which is used to display the details of a product.
	 * Depending on the params, it allows the user to edit the product details, or create a new product.
	 * @param product
	 * @param isStaffPage
	 * @param productType
	 */
	public static void productDetailsPageWindow(Product product, boolean isStaffPage, String productType) {
		ProductDetailsPage productDetailsPageGUI = new ProductDetailsPage(product, isStaffPage, productType);
		productDetailsPageGUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * This creates a new instance of the CustomerProductsPage GUI, which is used to display the
	 * products for the customer users.
	 * @param productType
	 */
	public static void customerProductPageWindow(String productType) {
		CustomerProductsPage customerProductsPageGUI = new CustomerProductsPage(productType);
		customerProductsPageGUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * This creates a new instance of the ManagerView GUI, which is used to display the manager dashboard.
	 */
	public static void managerDashboardWindow() {
		ManagerView managerDashboardGUI = new ManagerView();
		managerDashboardGUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}
