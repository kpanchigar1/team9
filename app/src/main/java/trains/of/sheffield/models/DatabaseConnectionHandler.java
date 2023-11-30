package trains.of.sheffield.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Class to handle the database connection
 */
public class DatabaseConnectionHandler {
    private static final String DB_URL = "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team009";
    private static final String DB_USER = "team009";
    private static final String DB_PASSWORD = "Zae5phaek";

    // Define the connection as a class member to share it across the application.
    private static Connection connection = null;

    /*
     * Method to open the database connection
     * @throws SQLException
     * @throws ClassNotFoundException
     * @return void
     */
    public static void openConnection() throws SQLException {
        // Load the JDBC driver and open the connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Method to close the database connection
     * @throws SQLException
     * @throws ClassNotFoundException
     * @return void
     */
    public static void closeConnection() {
        // Close the connection in a separate method to ensure proper resource
        // management.
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to get the database connection
     * @throws SQLException
     * @throws ClassNotFoundException
     * @return Connection
     */
    public static Connection getConnection() {
        return connection;
    }

}