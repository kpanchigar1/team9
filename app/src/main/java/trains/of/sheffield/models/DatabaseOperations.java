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
                            CurrentUser.setUser(getUserFromID(results.getString("userID"))); // Stores the user details
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


        public static Card getCardDetailFromDB(int cardNumber){
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM CardDetail WHERE cardNumber = '"+cardNumber+"'"; // Fetches the details under the selected card number
            ResultSet results = st.executeQuery(r);
            results.next();
            return new Card(results.getString("cardName"), results.getInt("cardNumber"), results.getInt("expiryDate"), results.getInt("cvv")); // Returns the card details
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

    public static List<Product> getProductFromType(String m) {
        List<Product> allProducts = new ArrayList<>();
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM Product WHERE productCode LIKE '"+m+"%'"; // Fetches the details under the selected username
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

    public static void updateStock(String productCode, int stock) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            // check if stock is present, if yes then update else insert
            String r = "SELECT * FROM Stock WHERE productCode = '"+productCode+"'"; // Fetches the details under the selected username
            ResultSet results = st.executeQuery(r);
            if (results.next()) {
                // update
                String updateQuery = "UPDATE Stock SET stockCount = ? WHERE productCode = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, stock);
                    updateStatement.setString(2, productCode);
                    updateStatement.executeUpdate();
                }
            } else {
                // insert
                String insertQuery = "INSERT INTO Stock VALUES (?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(1, productCode);
                    insertStatement.setInt(2, stock);
                    insertStatement.executeUpdate();
                }
            }
            DatabaseConnectionHandler.closeConnection();
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
    }

    public static String[][] getPreviousOrders(){
        // String[] columnNames = {"Order ID", "Order Date", "Customer Name", "Customer Email", "Postal Address", "Order Status", "Order Total"};
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM Orders WHERE status = 2"; // Fetches the details under the selected username
            ResultSet results = st.executeQuery(r);
            List<String[]> orderList = new ArrayList<>();
            while(results.next()) {
                String[] order = new String[7];
                User user = getUserFromID(results.getString("userID"));
                order[0] = results.getString("orderID");
                order[1] = results.getString("orderDate");
                //order[2] = getCustomerName(results.getString("userID"));
                //order[3] = getCustomerEmail(results.getString("userID"));
                //order[4] = getCustomerAddress(results.getString("userID"));
                order[5] = Status.getStatus(results.getInt("status")).toString();
                order[6] = String.valueOf(results.getDouble("totalPrice"));

                orderList.add(order);
            }
            DatabaseConnectionHandler.closeConnection();
            return orderList.toArray(new String[0][0]);
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return new String[0][];
    }

    private static User getUserFromID(String userID) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM User WHERE userID = '"+userID+"'"; // Fetches the details under the selected username
            ResultSet results = st.executeQuery(r);
            results.next();
            return new User(results.getString("userID"),
                    results.getString("forename"), results.getString("surname"),
                    results.getString("email"), results.getString("passwordHash"),
                    getAddressFromDB(results.getString("houseNumber"), results.getString("postCode")),
                    getCardDetailFromDB(results.getInt("cardNumber")), Role.getRole(results.getInt("role"))); // Stores the user details
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return null;
    }

    public static ArrayList<Order> getPreviousOrders2() {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String r = "SELECT * FROM Orders WHERE status = 2";
            ResultSet results = st.executeQuery(r);
            ArrayList<Order> orders = new ArrayList<>();
            while(results.next()) {
                List<OrderLine> orderLines = new ArrayList<>();
                String orderLineQuery = "SELECT * FROM OrderLine WHERE orderID = '"+results.getString("orderID")+"'";
                ResultSet orderLineResults = st.executeQuery(orderLineQuery);
                while (orderLineResults.next()) {
                    orderLines.add(new OrderLine(orderLineResults.getInt("productCode"), orderLineResults.getInt("quantity")));
                }
                orders.add(new Order(results.getInt("orderID"), results.getString("orderDate"), results.getInt("orderStatus"), orderLines)); // Stores the user details
            }
            DatabaseConnectionHandler.closeConnection();
            return orders;
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return null;
    }
}
