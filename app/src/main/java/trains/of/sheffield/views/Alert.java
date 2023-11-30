package trains.of.sheffield.views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is used to create an alert window for the user to see, in case of any error.
 */
public class Alert extends JDialog{
	JLabel messageLabel;
	JButton close;
	
	/**
	 * This constructor creates the alert window.
	 * @param message The message to be displayed in the alert window.
	 */
	public Alert(String message) {
		Toolkit.getDefaultToolkit().beep();
		setLayout(new GridLayout(2,1));
		
		messageLabel = new JLabel(message, SwingConstants.CENTER);
		add(messageLabel);
		
		close = new JButton("Close");
		event e = new event();
		close.addActionListener(e);
		add(close);
	}

	/**
	 * This method is used to display the alert window.
	 */
	public class event implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}