package trains.of.sheffield.models;

import trains.of.sheffield.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseOperations {


    public static boolean tryLogIn(String uName, char[] pWord) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM users WHERE uname = '"+uName+"'"; // Fetches the details under the selected username
            ResultSet results = st.executeQuery(r);
            results.next();
            if(results.getString("pword").equals(pWord)) { // If the entered passwords are the same, the details are entered
                results.next();
                UserStore.setUser(new User(results.getString("userID"), results.getString("forename"), results.getString("surname"), results.getString("email"), results.getString("passwordHash"), results.getInt("role"), Address.getAddressFromDB(results.getString("houseNumber"), results.getString("postCode")), CardDetail.getCardDetailFromDB(results.getInt("cardNumber")))); // Stores the user details
                Role role = Role.valueOf(results.getString("role")); // Gets the role of the user
                GUILoader.mainMenuWindow(role);
                return true;
            } else {
                GUILoader.alertWindow("Your password was incorrect"); // Tells the user the password is incorrect
            }
            connection.close(); // Ending connection
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return false;
    }
}
