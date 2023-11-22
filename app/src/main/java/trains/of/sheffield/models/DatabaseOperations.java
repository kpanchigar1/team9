package trains.of.sheffield.models;

import trains.of.sheffield.*;
import trains.of.sheffield.util.HashedPasswordGenerator;
import trains.of.sheffield.util.UniqueUserIDGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    //TODO: parameterise all queries
    public static boolean tryLogIn(String email, char[] pWord) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            String r = "SELECT * FROM User WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(r)) {
                preparedStatement.setString(1, email);

                try (ResultSet results = preparedStatement.executeQuery()) {
                    results.next();
                    // Process the result set if needed
                    if(results.getString("passwordHash").equals(HashedPasswordGenerator.hashPassword(pWord))) { // If the entered passwords are the same, the details are entered
                            CurrentUser.setUser(new User(results.getString("userID"),
                            results.getString("forename"), results.getString("surname"), 
                            results.getString("email"), results.getString("passwordHash"),
                            getAddressFromDB(results.getString("houseNumber"), results.getString("postCode")), 
                            getCardDetailFromDB(results.getInt("cardNumber")), Role.getRole(results.getInt("role")))); // Stores the user details
                        GUILoader.mainMenuWindow();
                        return true;
                    } else {
                        GUILoader.alertWindow("Your password was incorrect"); // Tells the user the password is incorrect
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                GUILoader.alertWindow("Error: Could not execute query " + ex.getMessage());
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
                // TODO: check if address already exists
                // Insert address
                String addressQuery = "INSERT INTO Address VALUES (?, ?, ?, ?)";
                try (PreparedStatement addressStatement = connection.prepareStatement(addressQuery)) {
                    addressStatement.setString(1, houseNumber);
                    addressStatement.setString(2, streetName);
                    addressStatement.setString(3, city);
                    addressStatement.setString(4, postCode);
                    addressStatement.executeUpdate();
                } // Missing closing brace for the inner try block

                // Insert user
                String userQuery = "INSERT INTO User VALUES (?, ?, ?, ?, ?, 2, ?, ?, NULL)";
                try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
                    userStatement.setString(1, UniqueUserIDGenerator.generateUniqueUserID());
                    userStatement.setString(2, fName);
                    userStatement.setString(3, sName);
                    userStatement.setString(4, email);
                    userStatement.setString(5, HashedPasswordGenerator.hashPassword(pWord));
                    userStatement.setString(6, houseNumber);
                    userStatement.setString(7, postCode);
                    userStatement.executeUpdate();
                }

                DatabaseConnectionHandler.closeConnection(); // Ending connection
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception for debugging purposes
                GUILoader.alertWindow("Error: Could not connect " + ex.getMessage()); // Outputs error message
                return false;
            }
        }


        public static CardDetail getCardDetailFromDB(int cardNumber){
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM CardDetail WHERE cardNumber = '"+cardNumber+"'"; // Fetches the details under the selected card number
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
            String r = "SELECT * FROM Address WHERE houseNumber = '"+houseNumber+"' AND postCode = '"+postCode+"'"; // Fetches the details under the selected house number and postcode
            ResultSet results = st.executeQuery(r);
            results.next();
            return new Address(results.getString("houseNumber"), results.getString("streetName"), results.getString("city"), results.getString("postCode")); // Returns the address details
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return null;
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

    public static List<Product> getAllProducts(){
        List<Product> allProducts = new ArrayList<>();
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM Product"; // Fetches the details under the selected username
            ResultSet results = st.executeQuery(r);
            while(results.next()) {
                allProducts.add(new Product(results.getString("productCode"),
                        getBrandNameFromID(results.getString("brandID")), results.getString("productName"),
                        results.getDouble("retailPrice"), Gauge.valueOf(results.getString("gauge")),
                        results.getString("description"), getProductStock(results.getString("productCode")))); // Stores the user details
            }
            DatabaseConnectionHandler.closeConnection();
            return allProducts;
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return null;
    }

    private static String getBrandNameFromID(String brandID) {
        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM Brand WHERE brandID = '"+brandID+"'"; // Fetches the details under the selected username
            ResultSet results = st.executeQuery(r);
            results.next();
            return results.getString("brandName");
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return null;
    }

    private static Integer getProductStock(String productCode) {
        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM Stock WHERE productCode = '"+productCode+"'"; // Fetches the details under the selected username
            ResultSet results = st.executeQuery(r);
            results.next();
            return results.getInt("stockCount");
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return null;
    }
}
