package trains.of.sheffield.models;

import trains.of.sheffield.*;
import trains.of.sheffield.util.HashedPasswordGenerator;
import trains.of.sheffield.util.UniqueUserIDGenerator;

import java.sql.*;

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
                CurrentUser.setUser(new User(results.getString("userID"),
                    results.getString("forename"), results.getString("surname"), 
                    results.getString("email"), results.getString("passwordHash"), 
                    results.getInt("role"), getAddressFromDB(results.getString("houseNumber"),
                    results.getString("postCode")), getCardDetailFromDB(results.getInt("cardNumber")))); // Stores the user details
                GUILoader.mainMenuWindow(CurrentUser.getRole());
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

    public static boolean trySignUp(char[] pWord, String fName, String sName, String email, String houseNumber, String streetName, String city, String postCode, String cardName) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "INSERT INTO User VALUES ("+UniqueUserIDGenerator.generateUniqueUserID()+
                ", "+fName+", "+sName+", "+email+", "+HashedPasswordGenerator.hashPassword(pWord)+
                ", 2, "+houseNumber+", "+postCode+", NULL)"; // Fetches the details under the selected username
            st.executeUpdate(r);
            r = "INSERT INTO Address VALUES ("+houseNumber+", "+streetName+", "+city+", "+postCode+")";
            return true;
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
            return false;
        }
    }

    public static CardDetail getCardDetailFromDB(int cardNumber){
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM cardDetails WHERE cardNumber = '"+cardNumber+"'"; // Fetches the details under the selected card number
            ResultSet results = st.executeQuery(r);
            results.next();
            return new CardDetail(results.getString("cardName"), results.getInt("cardNumber"), results.getInt("expiryDate"), results.getInt("cvv")); // Returns the card details
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return null;
    }

    public static Address getAddressFromDB(String houseNumber, String postCode){
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM address WHERE houseNumber = '"+houseNumber+"' AND postCode = '"+postCode+"'"; // Fetches the details under the selected house number and postcode
            ResultSet results = st.executeQuery(r);
            results.next();
            return new Address(results.getString("houseNumber"), results.getString("streetName"), results.getString("city"), results.getString("postCode")); // Returns the address details
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return null;
    }
    public String verifyLogin(Connection connection, String username, char[] enteredPassword) {
        try {
            // Query the database to fetch user information
            String sql = "SELECT userId, password_hash, failed_login_attempts, " +
                    "account_locked FROM Users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String storedPasswordHash = resultSet.getString("password_hash");
                int failedLoginAttempts = resultSet.getInt("failed_login_attempts");
                boolean accountLocked = resultSet.getBoolean("account_locked");

                // Check if the account is locked
                if (accountLocked) {
                    return "Account is locked. Please contact support.";
                } else {
                    // Verify the entered password against the stored hash
                    if (verifyPassword(enteredPassword, storedPasswordHash)) {
                        // Update the last login time
                        sql = "UPDATE Users SET last_login = CURRENT_TIMESTAMP, " +
                                "failed_login_attempts = 0 WHERE userId = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, userId);
                        statement.executeUpdate();
                        return "Login successful for user: " + username;
                    } else {
                        // Incorrect password, update failed login attempts
                        failedLoginAttempts++;
                        sql = "UPDATE Users SET failed_login_attempts = ? WHERE userId = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setInt(1, failedLoginAttempts);
                        statement.setString(2, userId);
                        statement.executeUpdate();

                        return "Incorrect password. Failed login attempts: " + failedLoginAttempts;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "User not found.";
    }

    private static boolean verifyPassword(char[] enteredPassword, String storedPasswordHash) {
        try {
            String hashedEnteredPassword = HashedPasswordGenerator.hashPassword(enteredPassword);
            return hashedEnteredPassword.equals(storedPasswordHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
