package trains.of.sheffield.models;

import trains.of.sheffield.*;
import trains.of.sheffield.util.HashedPasswordGenerator;
import trains.of.sheffield.util.UniqueUserIDGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    // TODO: Check for unsafe operations
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
                    if (results.getString("passwordHash").equals(HashedPasswordGenerator.hashPassword(pWord))) { // If the entered passwords are the same, the details are entered
                        CurrentUser.setUser(getUserFromID(results.getString("userID"))); // Stores the user details
                        GUILoader.mainMenuWindow();
                        return true;
                    } else {
                        GUILoader.alertWindow("Your password was incorrect"); // Tells the user the password is incorrect
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                GUILoader.alertWindow("No user found under " + email); // Tells the user the username is incorrect
            }
            connection.close(); // Ending connection
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return false;
    }

    public static boolean trySignUp(char[] pWord, String fName, String sName, String email, String houseNumber, String streetName, String city, String postCode) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            // Insert address
            String checkAddressQuery = "SELECT * FROM Address WHERE houseNumber = ? AND postCode = ?";
            try (PreparedStatement checkAddressStatement = connection.prepareStatement(checkAddressQuery)) {
                checkAddressStatement.setString(1, houseNumber);
                checkAddressStatement.setString(2, postCode);
                ResultSet results = checkAddressStatement.executeQuery();
                if (!results.next()) {
                    // Address already exists
                    String addressQuery = "INSERT INTO Address VALUES (?, ?, ?, ?)";
                    try (PreparedStatement addressStatement = connection.prepareStatement(addressQuery)) {
                        addressStatement.setString(1, houseNumber);
                        addressStatement.setString(2, streetName);
                        addressStatement.setString(3, city);
                        addressStatement.setString(4, postCode);
                        addressStatement.executeUpdate();
                    }
                }
            }

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

    public static void updateDetails(String fName, String sName, String email, String houseNumber, String streetName, String city, String postCode) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            // Update address
            String checkAddressQuery = "SELECT * FROM Address WHERE houseNumber = ? AND postCode = ?";
            try (PreparedStatement checkAddressStatement = connection.prepareStatement(checkAddressQuery)) {
                checkAddressStatement.setString(1, houseNumber);
                checkAddressStatement.setString(2, postCode);
                ResultSet results = checkAddressStatement.executeQuery();
                if (!results.next()) {
                    // Address already exists
                    String addressQuery = "INSERT INTO Address VALUES (?, ?, ?, ?)";
                    try (PreparedStatement addressStatement = connection.prepareStatement(addressQuery)) {
                        addressStatement.setString(1, houseNumber);
                        addressStatement.setString(2, streetName);
                        addressStatement.setString(3, city);
                        addressStatement.setString(4, postCode);
                        addressStatement.executeUpdate();
                    }
                }
            }

            // Update user
            String userQuery = "UPDATE User SET forename = ?, surname = ?, email = ?, houseNumber = ?, postCode = ? WHERE userID = ?";
            try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
                userStatement.setString(1, fName);
                userStatement.setString(2, sName);
                userStatement.setString(3, email);
                userStatement.setString(4, houseNumber);
                userStatement.setString(5, postCode);
                userStatement.setString(6, CurrentUser.getCurrentUser().getId());
                userStatement.executeUpdate();
                // update current user details
                CurrentUser.setForename(fName);
                CurrentUser.setSurname(sName);
                CurrentUser.setEmail(email);
            }

            DatabaseConnectionHandler.closeConnection(); // Ending connection
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception for debugging purposes
            GUILoader.alertWindow("Error: Could not connect " + ex.getMessage()); // Outputs error message
        }
    }

    public static void updatePassword(char[] pWord) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            // Update user
            String userQuery = "UPDATE User SET passwordHash = ? WHERE userID = ?";
            try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
                userStatement.setString(1, HashedPasswordGenerator.hashPassword(pWord));
                userStatement.setString(2, CurrentUser.getCurrentUser().getId());
                userStatement.executeUpdate();
                // update current user details
                CurrentUser.setPasswordHash(HashedPasswordGenerator.hashPassword(pWord));
            }
            DatabaseConnectionHandler.closeConnection(); // Ending connection
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception for debugging purposes
            GUILoader.alertWindow("Error: Could not connect " + ex.getMessage()); // Outputs error message
        }
    }

    public static void tryCardDetails(String cardName, String cardNumber, String expiryDate, String cvv) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            // Insert card details
            String cardQuery = "INSERT INTO CardDetail VALUES (?, ?, ?, ?)";
            try (PreparedStatement cardStatement = connection.prepareStatement(cardQuery)) {
                cardStatement.setString(1, cardName);
                cardStatement.setString(2, cardNumber);
                cardStatement.setString(3, expiryDate);
                cardStatement.setString(4, cvv);
                cardStatement.executeUpdate();
            }

            // Update user
            String userQuery = "UPDATE User SET cardNumber = ? WHERE userID = ?";
            try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
                userStatement.setString(1, cardNumber);
                userStatement.setString(2, CurrentUser.getCurrentUser().getId());
                userStatement.executeUpdate();
                // update current user details
                CurrentUser.setCardDetail(new Card(cardName, cardNumber, expiryDate, cvv));
            }
            DatabaseConnectionHandler.closeConnection(); // Ending connection
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception for debugging purposes
            GUILoader.alertWindow("Error: Could not connect " + ex.getMessage()); // Outputs error message
        }
    }

    public static void updateCardDetails(String cardName, String cardNumber, String expiryDate, String cvv, String oldCardNumber) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            // Update card details
            String cardQuery = "UPDATE CardDetail SET cardName = ?, expiryDate = ?, cvv = ?, cardNumber = ? WHERE cardNumber = ?";
            try (PreparedStatement cardStatement = connection.prepareStatement(cardQuery)) {
                cardStatement.setString(1, cardName);
                cardStatement.setString(2, expiryDate);
                cardStatement.setString(3, cvv);
                cardStatement.setString(4, cardNumber);
                cardStatement.setString(5, oldCardNumber);
                cardStatement.executeUpdate();
            }

            // Update user
            String userQuery = "UPDATE User SET cardNumber = ? WHERE userID = ?";
            try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
                userStatement.setString(1, cardNumber);
                userStatement.setString(2, CurrentUser.getCurrentUser().getId());
                userStatement.executeUpdate();
                // update current user details
                CurrentUser.setCardDetail(new Card(cardName, cardNumber, expiryDate, cvv));
            }
            DatabaseConnectionHandler.closeConnection(); // Ending connection
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception for debugging purposes
            GUILoader.alertWindow("Error: Could not connect " + ex.getMessage()); // Outputs error message
        }
    }

    public static Card getCardDetailFromDB(String cardNumber) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String cardQuery = "SELECT * FROM CardDetail WHERE cardNumber = ?";
            try (PreparedStatement cardStatement = connection.prepareStatement(cardQuery)) {
                cardStatement.setString(1, cardNumber);
                ResultSet results = cardStatement.executeQuery();
                results.next();
                return new Card(results.getString("cardName"), results.getString("cardNumber"), results.getString("expiryDate"), results.getString("cvv")); // Returns the card details
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static Address getAddressFromDB(String houseNumber, String postCode) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String addressQuery = "SELECT * FROM Address WHERE houseNumber = ? AND postCode = ?";
            try (PreparedStatement addressStatement = connection.prepareStatement(addressQuery)) {
                addressStatement.setString(1, houseNumber);
                addressStatement.setString(2, postCode);
                ResultSet results = addressStatement.executeQuery();
                results.next();
                return new Address(results.getString("houseNumber"), results.getString("streetName"), results.getString("city"), results.getString("postCode")); // Returns the address details
            }

        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }

    private static String getBrandNameFromID(String brandID) {
        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            // parameterise query
            String brandQuery = "SELECT * FROM Brand WHERE brandID=?";
            try (PreparedStatement brandStatement = connection.prepareStatement(brandQuery)) {
                brandStatement.setString(1, brandID);
                ResultSet results = brandStatement.executeQuery();
                results.next();
                return results.getString("brandName");
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }

    private static Integer getProductStock(String productCode) {
        try {
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String stockQuery = "SELECT * FROM Stock WHERE productCode=?";
            try (PreparedStatement stockStatement = connection.prepareStatement(stockQuery)) {
                stockStatement.setString(1, productCode);
                ResultSet results = stockStatement.executeQuery();
                if (results.next()) {
                    return results.getInt("stockCount");
                } else {
                    return 0;
                }
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }

    public static List<Product> getProductsFromType(String m) {
        List<Product> allProducts = new ArrayList<>();
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            // String r = "SELECT * FROM Product WHERE productCode LIKE '"+m+"%'"
            String productQuery = "SELECT * FROM Product WHERE productCode LIKE ?";
            try (PreparedStatement productStatement = connection.prepareStatement(productQuery)) {
                productStatement.setString(1, m + "%");
                ResultSet results = productStatement.executeQuery();
                while (results.next()) {
                    allProducts.add(new Product(results.getString("productCode"),
                            getBrandNameFromID(results.getString("brandID")), results.getString("productName"),
                            results.getDouble("retailPrice"), Gauge.valueOf(results.getString("gauge")),
                            results.getString("description"), getProductStock(results.getString("productCode")))); // Stores the user details
                }
                return allProducts;
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }

    public static void updateStock(String productCode, int stock) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            // check if stock is present, if yes then update else insert
            String r = "SELECT * FROM Stock WHERE productCode = '" + productCode + "'"; // Fetches the details under the selected username
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
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
    }

    public static String[][] getOrdersFromStatus(Status status, boolean justCustomer) {
        // String[] columnNames = {"Order ID", "Order Date", "Customer Name", "Customer Email", "Postal Address", "Order Status", "Order Total"};
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            String orderQuery = "SELECT * FROM Orders WHERE status = ?"; // Fetches the details under the selected username
            try (PreparedStatement ordersStatement = connection.prepareStatement(orderQuery)) {
                ordersStatement.setInt(1, status.getStatusID());
                ResultSet results = ordersStatement.executeQuery();
                List<String[]> orderList = new ArrayList<>();
                while (results.next()) {
                    String[] order = new String[9];
                    User user = getUserFromID(results.getString("userID"));
                    if (justCustomer && user.getId().equals(CurrentUser.getCurrentUser().getId()) || !justCustomer) {
                        order[0] = results.getString("orderID");
                        order[1] = results.getString("date");
                        order[2] = user.getForename() + " " + user.getSurname();
                        order[3] = user.getEmail();
                        order[4] = user.getAddress().toString();
                        order[5] = Status.getStatus(results.getInt("status")).toString();
                        order[6] = String.valueOf(results.getDouble("totalPrice"));
                        if (status.equals(Status.FULFILLED)) {
                            order[7] = "True";
                        }
                        orderList.add(order);
                    }
                }
                DatabaseConnectionHandler.closeConnection();
                return orderList.toArray(new String[0][0]);
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return new String[0][];
    }

    public static Order getBasketFromUser(String userId) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            String orderQuery = "SELECT * FROM Orders WHERE userID = ? AND status = ?"; // Fetches the details under the selected username
            try (PreparedStatement ordersStatement = connection.prepareStatement(orderQuery)) {
                ordersStatement.setString(1, userId);
                ordersStatement.setInt(2, Status.PENDING.getStatusID());
                ResultSet results = ordersStatement.executeQuery();
                results.next();
                ArrayList orderLines = new ArrayList();
                for (String[] orderLine : getOrderLines(results.getInt("orderID"))) {
                    orderLines.add(orderLine);
                }
                return new Order(results.getInt("orderID"), results.getString("date"), results.getInt("status"), orderLines);
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }

    public static void addProductToOrder(Product product) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            // Check if User has a pending order
            String orderQuery = "SELECT * FROM Orders WHERE userID = ? AND status = ?";
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery)) {
                orderStatement.setString(1, CurrentUser.getCurrentUser().getId());
                orderStatement.setInt(2, Status.PENDING.getStatusID());
                System.out.println("Debug 1");
                ResultSet results = orderStatement.executeQuery();
                if (results.next()) {
                    // User has a pending order
                    // Check if product is already in order
                    String orderLineQuery = "SELECT * FROM OrderLines WHERE orderID = ? AND productCode = ?";
                    try (PreparedStatement orderLineStatement = connection.prepareStatement(orderLineQuery)) {
                        orderLineStatement.setInt(1, results.getInt("orderID"));
                        orderLineStatement.setString(2, product.getProductCode());
                        ResultSet orderLineResults = orderLineStatement.executeQuery();
                        if (orderLineResults.next()) {
                            // Product is already in order
                            // Update quantity
                            String updateQuery = "UPDATE OrderLines SET quantity = ? WHERE orderID = ? AND productCode = ?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                updateStatement.setInt(1, orderLineResults.getInt("quantity") + 1);
                                updateStatement.setInt(2, results.getInt("orderID"));
                                updateStatement.setString(3, product.getProductCode());
                                System.out.println("Debug 2");
                                updateStatement.executeUpdate();
                            }
                        } else {
                            // Product is not in order
                            // Add product to order
                            String insertQuery = "INSERT INTO OrderLines VALUES (?, ?, ?)";
                            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                                insertStatement.setInt(1, results.getInt("orderID"));
                                insertStatement.setString(2, product.getProductCode());
                                insertStatement.setInt(3, 1);
                                System.out.println("Debug 3");
                                insertStatement.executeUpdate();
                            }
                        }
                        // Update order total price
                        String updateQuery = "UPDATE Orders SET totalPrice = ? WHERE orderID = ?"; 
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setDouble(1, results.getDouble("totalPrice") + product.getPrice());
                            updateStatement.setInt(2, results.getInt("orderID"));
                            updateStatement.executeUpdate();
                        }
                    }
                } else {
                    // User does not have a pending order
                    // Create new order
                    String insertQuery = "INSERT INTO Orders (date, status, userID, totalPrice) VALUES (?, ?, ?, ?)";
                    Date utilDate = new Date(System.currentTimeMillis());
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setDate(1, new java.sql.Date(utilDate.getTime()));
                        insertStatement.setInt(2, 0);
                        insertStatement.setString(3, CurrentUser.getCurrentUser().getId());
                        insertStatement.setDouble(4, product.getPrice());
                        System.out.println("Debug 4");
                        insertStatement.executeUpdate();
                    }
                    // Get orderID
                    String orderIDQuery = "SELECT * FROM Orders WHERE userID = ? AND status = ?";
                    try (PreparedStatement orderIDStatement = connection.prepareStatement(orderIDQuery)) {
                        orderIDStatement.setString(1, CurrentUser.getCurrentUser().getId());
                        orderIDStatement.setInt(2, Status.PENDING.getStatusID());
                        System.out.println("Debug 5");
                        ResultSet orderIDResults = orderIDStatement.executeQuery();
                        orderIDResults.next();
                        // Add product to order
                        String insertQuery2 = "INSERT INTO OrderLines VALUES (?, ?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery2)) {
                            insertStatement.setInt(1, orderIDResults.getInt("orderID"));
                            insertStatement.setString(2, product.getProductCode());
                            insertStatement.setInt(3, 1);
                            System.out.println("Debug 6");
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Debug 7");
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
    }

    public static String[][] getOrdersFromUser(String id) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            String orderQuery = "SELECT * FROM Orders WHERE userID = ?"; // Fetches the details under the selected username
            try (PreparedStatement ordersStatement = connection.prepareStatement(orderQuery)) {
                ordersStatement.setString(1, id);
                ResultSet results = ordersStatement.executeQuery();
                List<String[]> orderList = new ArrayList<>();
                while(results.next()) {
                    String[] order = new String[9];
                    User user = getUserFromID(results.getString("userID"));
                    order[0] = results.getString("orderID");
                    order[1] = results.getString("date");
                    order[2] = user.getForename() + " " + user.getSurname();
                    order[3] = user.getEmail();
                    order[4] = user.getAddress().toString();
                    order[5] = Status.getStatus(results.getInt("status")).toString();
                    order[6] = String.valueOf(results.getDouble("totalPrice"));
                    orderList.add(order);
                }
                DatabaseConnectionHandler.closeConnection();
                return orderList.toArray(new String[0][0]);
            }
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return new String[0][];
    }
    private static User getUserFromID(String userID) {
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String userQuery = "SELECT * FROM User WHERE userID = ?";
            try (PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
                userStatement.setString(1, userID);
                ResultSet results = userStatement.executeQuery();
                results.next();
                return new User(results.getString("userID"), results.getString("forename"), results.getString("surname"), results.getString("email"), results.getString("passwordHash"), getAddressFromDB(results.getString("houseNumber"), results.getString("postCode")), getCardDetailFromDB(results.getString("cardNumber")), Role.getRole(results.getInt("role"))); // Stores the user details
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }

    public static String[][] getOrderLines(int orderID) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String orderLineQuery = "SELECT * FROM OrderLines WHERE orderID = ?";
            try (PreparedStatement orderLineStatement = connection.prepareStatement(orderLineQuery)) {
                orderLineStatement.setInt(1, orderID);
                ResultSet results = orderLineStatement.executeQuery();
                List<String[]> orderLineList = new ArrayList<>();
                while (results.next()) {
                    String[] orderLine = new String[5];
                    Product product = getProductFromCode(results.getString("productCode"));
                    orderLine[0] = product.getProductCode();
                    orderLine[1] = product.getProductName();
                    orderLine[2] = String.valueOf(results.getInt("quantity"));
                    orderLine[3] = String.valueOf(product.getPrice());
                    orderLineList.add(orderLine);
                }
                DatabaseConnectionHandler.closeConnection();
                return orderLineList.toArray(new String[0][0]);
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return new String[0][];
    }

    private static Product getProductFromCode(String productCode) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String productQuery = "SELECT * FROM Product WHERE productCode = ?";
            try (PreparedStatement productStatement = connection.prepareStatement(productQuery)) {
                productStatement.setString(1, productCode);
                ResultSet results = productStatement.executeQuery();
                results.next();
                return new Product(results.getString("productCode"),
                        getBrandNameFromID(results.getString("brandID")), results.getString("productName"),
                        results.getDouble("retailPrice"), Gauge.valueOf(results.getString("gauge")),
                        results.getString("description"), getProductStock(results.getString("productCode"))); // Stores the user details
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }

    public static Order getOrderFromId(int orderID) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            String orderQuery = "SELECT * FROM Orders WHERE orderID = ?"; // Fetches the details under the selected username
            try (PreparedStatement ordersStatement = connection.prepareStatement(orderQuery)) {
                ordersStatement.setInt(1, orderID);
                ResultSet results = ordersStatement.executeQuery();
                results.next();
                ArrayList orderLines = new ArrayList();
                for (String[] orderLine : getOrderLines(orderID)) {
                    orderLines.add(orderLine);
                }
                return new Order(results.getInt("orderID"), results.getString("date"), results.getInt("status"), orderLines);
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }

    public static void updateOrderStatus(int orderID, Status status) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            if(status.equals(Status.CONFIRMED) && !checkOrderCanBeConfirmed(orderID)) {
                status = Status.BLOCKED;
                GUILoader.alertWindow("Order has been blocked as there is not enough stock");
            } else if (status.equals(Status.FULFILLED)) {
                String notOrderQuery = "SELECT * FROM Orders WHERE orderID != ? AND status = ?";
                try (PreparedStatement notOrderStatement = connection.prepareStatement(notOrderQuery)) {
                    notOrderStatement.setInt(1, orderID);
                    notOrderStatement.setInt(2, Status.FULFILLED.getStatusID());
                    ResultSet results = notOrderStatement.executeQuery();
                    while (results.next()) {
                        if(!checkOrderCanBeConfirmed(results.getInt("orderID"))) {
                            updateOrderStatus(results.getInt("orderID"), Status.BLOCKED);
                            GUILoader.alertWindow("Order "+ results.getInt("orderID") +" has been blocked as there is not enough stock");
                        }
                    }
                }
            }
            String orderQuery = "UPDATE Orders SET status = ? WHERE orderID = ?";
            try (PreparedStatement ordersStatement = connection.prepareStatement(orderQuery)) {
                ordersStatement.setInt(1, status.getStatusID());
                ordersStatement.setInt(2, orderID);
                ordersStatement.executeUpdate();
            }
            DatabaseConnectionHandler.closeConnection();
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
    }

    public static boolean checkOrderCanBeConfirmed(int orderID) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            String orderQuery = "SELECT * FROM OrderLines WHERE orderID = ?";
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery)) {
                orderStatement.setInt(1, orderID);
                ResultSet results = orderStatement.executeQuery();
                while (results.next()) {
                    String productCode = results.getString("productCode");
                    int quantity = results.getInt("quantity");
                    String stockQuery = "SELECT * FROM Stock WHERE productCode = ?";
                    try (PreparedStatement stockStatement = connection.prepareStatement(stockQuery)) {
                        stockStatement.setString(1, productCode);
                        ResultSet stockResults = stockStatement.executeQuery();
                        stockResults.next();
                        if (stockResults.getInt("stockCount") < quantity) {
                            return false;
                        }
                    }
                }
                return true;
            }
        } catch(Exception ex) {
            GUILoader.alertWindow("Error: Could not connect "+ex); // Outputs error message
        }
        return false;
    }

    public static void updateOrderLines(int orderID, String productCode, int quantity) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            if (quantity == 0) {
                String orderLineQuery = "DELETE FROM OrderLines WHERE orderID = ? AND productCode = ?";
                try (PreparedStatement orderLineStatement = connection.prepareStatement(orderLineQuery)) {
                    orderLineStatement.setInt(1, orderID);
                    orderLineStatement.setString(2, productCode);
                    orderLineStatement.executeUpdate();
                }
            } else {
                String orderLineQuery = "UPDATE OrderLines SET quantity = ? WHERE orderID = ? AND productCode = ?";
                try (PreparedStatement orderLineStatement = connection.prepareStatement(orderLineQuery)) {
                    orderLineStatement.setInt(1, quantity);
                    orderLineStatement.setInt(2, orderID);
                    orderLineStatement.setString(3, productCode);
                    orderLineStatement.executeUpdate();
                    System.out.println(productCode + " " + quantity);
                }
            }
            DatabaseConnectionHandler.closeConnection();
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
    }

    public static void deleteOrder(int orderID) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            String orderQuery = "DELETE FROM Orders WHERE orderID = ?";
            try (PreparedStatement ordersStatement = connection.prepareStatement(orderQuery)) {
                ordersStatement.setInt(1, orderID);
                ordersStatement.executeUpdate();
            }
            DatabaseConnectionHandler.closeConnection();
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
    }

    public static void deleteProduct(String productCode) {
        // TODO: deleting products may cause errors since they exist in past orders
        // one option is to remove the relationship between products and order line
        // if product is in an order line, change orderline to "DELETED PRODUCT"

        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            String productQuery = "DELETE FROM Product WHERE productCode = ?";
            try (PreparedStatement productStatement = connection.prepareStatement(productQuery)) {
                productStatement.setString(1, productCode);
                productStatement.executeUpdate();
            }
            DatabaseConnectionHandler.closeConnection();
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
    }

    public static void decreaseStockLevels(String[][] orderData) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            for (String[] orderLine : orderData) {
                String productCode = orderLine[0];
                int quantity = Integer.parseInt(orderLine[2]);
                String stockQuery = "UPDATE Stock SET stockCount = stockCount - ? WHERE productCode = ?";
                try (PreparedStatement stockStatement = connection.prepareStatement(stockQuery)) {
                    stockStatement.setInt(1, quantity);
                    stockStatement.setString(2, productCode);
                    stockStatement.executeUpdate();
                }
            }
            DatabaseConnectionHandler.closeConnection();
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
    }


    public static String getProductEra(String productCode) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String productQuery = "SELECT * FROM EraLink WHERE productCode = ?";
            try (PreparedStatement productStatement = connection.prepareStatement(productQuery)) {
                productStatement.setString(1, productCode);
                ResultSet results = productStatement.executeQuery();
                results.next();
                return results.getString("era");
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }


    public static String getProductAnalogue(String productCode) {
        try {
            DatabaseConnectionHandler.openConnection(); // Opens connection
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();
            String productQuery = "SELECT * FROM ControllerTable WHERE productCode = ?";
            try (PreparedStatement productStatement = connection.prepareStatement(productQuery)) {
                productStatement.setString(1, productCode);
                ResultSet results = productStatement.executeQuery();
                results.next();
                if (results.getBoolean("analogue")) {
                    return "Analogue";
                } else {
                    return "Digital";
                }
            }
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }

    public static String[] getTrainSetData(String productCode) {
        String trainSetData[] = new String[4];
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            Statement st = connection.createStatement();

            String locomotiveQuery = "SELECT productName, productCode FROM Product WHERE productCode = (SELECT locomotiveCode FROM LocomotiveTrainSetLink WHERE trainSetCode = ?)";
            try (PreparedStatement productStatement = connection.prepareStatement(locomotiveQuery)) {
                productStatement.setString(1, productCode);
                try (ResultSet results = productStatement.executeQuery()) {
                    if (results.next()) {
                        trainSetData[0] = results.getString("productName") + " (" + results.getString("productCode") + ")";
                    }
                }
            }

            String rollingStockQuery = "SELECT productName, productCode FROM Product WHERE productCode = (SELECT rollingStockCode FROM RollingStockTrainSetLink WHERE trainSetCode = ?)";
            try (PreparedStatement productStatement = connection.prepareStatement(rollingStockQuery)) {
                productStatement.setString(1, productCode);
                try (ResultSet results = productStatement.executeQuery()) {
                    if (results.next()) {
                        trainSetData[1] = results.getString("productName") + " (" + results.getString("productCode") + ")";
                    }
                }
            }

            String controllerQuery = "SELECT productName, productCode FROM Product WHERE productCode = (SELECT controllerCode FROM ControllerTrainSetLink WHERE trainSetCode = ?)";
            try (PreparedStatement productStatement = connection.prepareStatement(controllerQuery)) {
                productStatement.setString(1, productCode);
                try (ResultSet results = productStatement.executeQuery()) {
                    if (results.next()) {
                        trainSetData[2] = results.getString("productName") + " (" + results.getString("productCode") + ")";
                    }
                }
            }

            String trackPackQuery = "SELECT productName, productCode FROM Product WHERE productCode = (SELECT trackPackCode FROM TrackPackTrainSetLink WHERE trainSetCode = ?)";
            try (PreparedStatement productStatement = connection.prepareStatement(trackPackQuery)) {
                productStatement.setString(1, productCode);
                try (ResultSet results = productStatement.executeQuery()) {
                    if (results.next()) {
                        trainSetData[3] = results.getString("productName") + " (" + results.getString("productCode") + ")";
                    }
                }
            }
            DatabaseConnectionHandler.closeConnection();
            return trainSetData;
        } catch (Exception ex) {
            GUILoader.alertWindow("Error: Could not connect " + ex); // Outputs error message
        }
        return null;
    }


    // Method to add a staff member
    public static boolean addStaffMember(String email) {
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "UPDATE User SET role = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Role.STAFF.getRoleID()); // Assuming a specific role ID for staff
                statement.setString(2, email);
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0; // True if the user was updated
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
            return false;
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }

    // Method to remove a staff member
    public static boolean removeStaffMember(String email) {
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "UPDATE User SET role = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, 2); // Assuming a default role for non-staff
                statement.setString(2, email);
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0; // True if the user was updated
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
            return false;
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }

    public static List<User> getStaffMembers() {
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "SELECT userID FROM User WHERE role = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Role.STAFF.getRoleID()); // Assuming a specific role ID for staff
                ResultSet results = statement.executeQuery();
                List<User> staffMembers = new ArrayList<>();
                while (results.next()) {
                    staffMembers.add(getUserFromID(results.getString("userID")));
                }
                return staffMembers;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
            return null;
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }

    public static void updateProduct(Product product) {
        // check if product exists, if yes then update, else insert
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "SELECT * FROM Product WHERE productCode = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, product.getProductCode());
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    // update
                    String updateQuery = "UPDATE Product SET brandID = ?, productName = ?, retailPrice = ?, gauge = ?, description = ? WHERE productCode = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, getBrandIDFromName(product.getBrandName()));
                        updateStatement.setString(2, product.getProductName());
                        updateStatement.setDouble(3, product.getPrice());
                        updateStatement.setString(4, product.getGauge().toString());
                        updateStatement.setString(5, product.getDescription());
                        updateStatement.setString(6, product.getProductCode());
                        updateStatement.executeUpdate();
                    }
                } else {
                    // insert
                    String insertQuery = "INSERT INTO Product VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, product.getProductCode());
                        insertStatement.setString(2, product.getBrandName());
                        insertStatement.setString(3, product.getProductName());
                        insertStatement.setDouble(4, product.getPrice());
                        insertStatement.setString(5, product.getGauge().toString());
                        insertStatement.setString(6, product.getDescription());
                        insertStatement.setInt(7, product.getStock());
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }

    private static int getBrandIDFromName(String brandName) {
        // check if brand exists, if yes then return ID, else insert and return ID
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "SELECT * FROM Brand WHERE brandName = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, brandName);
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    // return ID
                    return results.getInt("brandID");
                } else {
                    // insert
                    String insertQuery = "INSERT INTO Brand VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setInt(1, getNewBrandID());
                        insertStatement.setString(2, brandName);
                        insertStatement.executeUpdate();
                        return getBrandIDFromName(brandName);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
            return -1;
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }

    private static int getNewBrandID() {
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "SELECT MAX(brandID) FROM Brand";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet results = statement.executeQuery();
                results.next();
                return results.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
            return -1;
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }


    public static void updateProductEra(String productCode, String era) {
        // check if product exists, if yes then update, else insert
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "SELECT * FROM EraLink WHERE productCode = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, productCode);
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    // update
                    String updateQuery = "UPDATE EraLink SET era = ? WHERE productCode = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, era);
                        updateStatement.setString(2, productCode);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // insert
                    String insertQuery = "INSERT INTO EraLink VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, productCode);
                        insertStatement.setString(2, era);
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }


    public static void updateProductAnalogue(String productCode, String isAnalogue) {
        if (isAnalogue.equals("Analogue")) {
            isAnalogue = "true";
        } else {
            isAnalogue = "false";
        }
        // check if product exists, if yes then update, else insert
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "SELECT * FROM ControllerTable WHERE productCode = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, productCode);
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    // update
                    String updateQuery = "UPDATE ControllerTable SET analogue = ? WHERE productCode = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setBoolean(1, Boolean.parseBoolean(isAnalogue));
                        updateStatement.setString(2, productCode);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // insert
                    String insertQuery = "INSERT INTO ControllerTable VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, productCode);
                        insertStatement.setBoolean(2, Boolean.parseBoolean(isAnalogue));
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }

    public static void updateTrainSetData(String productCode, String locomotiveCode, String rollingStockCode, String controllerCode, String trackPackCode) {
        // check if products exist, if yes then update, else insert
        // update the different link tables
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "SELECT * FROM LocomotiveTrainSetLink WHERE trainSetCode = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, productCode);
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    // update
                    String updateQuery = "UPDATE LocomotiveTrainSetLink SET locomotiveCode = ? WHERE trainSetCode = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, locomotiveCode);
                        updateStatement.setString(2, productCode);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // insert
                    String insertQuery = "INSERT INTO LocomotiveTrainSetLink VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, productCode);
                        insertStatement.setString(2, locomotiveCode);
                        insertStatement.executeUpdate();
                    }
                }
            }

            query = "SELECT * FROM RollingStockTrainSetLink WHERE trainSetCode = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, productCode);
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    // update
                    String updateQuery = "UPDATE RollingStockTrainSetLink SET rollingStockCode = ? WHERE trainSetCode = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, rollingStockCode);
                        updateStatement.setString(2, productCode);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // insert
                    String insertQuery = "INSERT INTO RollingStockTrainSetLink VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, productCode);
                        insertStatement.setString(2, rollingStockCode);
                        insertStatement.executeUpdate();
                    }
                }
            }

            query = "SELECT * FROM ControllerTrainSetLink WHERE trainSetCode = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, productCode);
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    // update
                    String updateQuery = "UPDATE ControllerTrainSetLink SET controllerCode = ? WHERE trainSetCode = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, controllerCode);
                        updateStatement.setString(2, productCode);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // insert
                    String insertQuery = "INSERT INTO ControllerTrainSetLink VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, productCode);
                        insertStatement.setString(2, controllerCode);
                        insertStatement.executeUpdate();
                    }
                }
            }

            query = "SELECT * FROM TrackPackTrainSetLink WHERE trainSetCode = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, productCode);
                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    // update
                    String updateQuery = "UPDATE TrackPackTrainSetLink SET trackPackCode = ? WHERE trainSetCode = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, trackPackCode);
                        updateStatement.setString(2, productCode);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // insert
                    String insertQuery = "INSERT INTO TrackPackTrainSetLink VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, productCode);
                        insertStatement.setString(2, trackPackCode);
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }

    public static Product getProductFromID(String code) {
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            String query = "SELECT * FROM Product WHERE productCode = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, code);
                ResultSet results = statement.executeQuery();
                results.next();
                return new Product(results.getString("productCode"),
                        getBrandNameFromID(results.getString("brandID")), results.getString("productName"),
                        results.getDouble("retailPrice"), Gauge.valueOf(results.getString("gauge")),
                        results.getString("description"), getProductStock(results.getString("productCode"))); // Stores the user details
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
            return null;
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }

    public static void updateOrderLine(int orderID, String productID, int quantity) {
        // check if quantity is 0, if yes then delete, else update
        try {
            DatabaseConnectionHandler.openConnection();
            Connection connection = DatabaseConnectionHandler.getConnection();
            if (quantity == 0) {
                String query = "DELETE FROM OrderLines WHERE orderID = ? AND productCode = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, orderID);
                    statement.setString(2, productID);
                    statement.executeUpdate();
                }
            } else {
                String query = "UPDATE OrderLines SET quantity = ? WHERE orderID = ? AND productCode = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, quantity);
                    statement.setInt(2, orderID);
                    statement.setString(3, productID);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            GUILoader.alertWindow("Error: " + ex.getMessage());
        } finally {
            DatabaseConnectionHandler.closeConnection();
        }
    }
}



