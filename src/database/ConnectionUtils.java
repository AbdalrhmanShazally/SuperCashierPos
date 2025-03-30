package database;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

    public static Connection getConnection() throws SQLException,
            ClassNotFoundException {

        // Using Oracle
        // You may be replaced by other Database.
    	// commented by a.shazally on 26-03-2025 changing ConnectionUtils class.
       // return MySQLconnUtils.getMySQLConnection();
        return MySQLconnUtils.getConnection();
    }

    //
    // Test Connection ...
    //
   /* public static void main(String[] args) throws SQLException,
            ClassNotFoundException {

        System.out.println("Get connection ... ");

        // Get a Connection object
        Connection conn = ConnectionUtils.getConnection();

        System.out.println("Get connection " + conn);

        System.out.println("Done!");
    }*/
}