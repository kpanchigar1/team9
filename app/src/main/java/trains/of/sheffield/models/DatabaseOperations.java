package trains.of.sheffield.models;

import trains.of.sheffield.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseOperations {
    public static void tryLogIn(String uName, char[] pWord) {
        try {
            String uName = UN_enter.getText();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = null; // Initialising connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal planner", "user_access", "User1"); // Setting up a database connection
            Statement st = con.createStatement();
            String r = "SELECT * FROM users WHERE uname = '"+uName+"'"; // Fetches the details under the selected username
            ResultSet results = st.executeQuery(r);
            results.next();
            if(results.getString("pword").equals(PW_enter.getPassword())) { // If the entered passwords are the same, the details are entered
                dispose(); // Takes out the current window
                results.next();
                UserStore.setUser(new User(results.getString("userID"), results.getString("forename"), results.getString("surname"), results.getString("email"), results.getString("passwordHash"), results.getInt("role"), Address.getAddressFromDB(results.getString("houseNumber"), results.getString("postCode")), CardDetail.getCardDetailFromDB(results.getInt("cardNumber")))); // Stores the user details
                Role role = Role.valueOf(results.getString("role")); // Gets the role of the user
                GUILoader.mainMenuWindow(role);
            } else {
                GUILoader.alertWindow("Your password was incorrect"); // Tells the user the password is incorrect
            }
            con.close(); // Ending connection
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
    }
}
