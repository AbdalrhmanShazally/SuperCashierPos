package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitialize {

	public static Statement statement;
	public void DBInitialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		
		
	 // Load the JDBC driver
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	System.out.println("Driver loaded");
	  // Connect to a database
	/*
	Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/ucsmpos","supercashier","super_cashier");
	// commented by a.shazally on 26-03-2025 changing ConnectionUtils class.
	*/
	Connection connection = ConnectionUtils.getConnection();
	System.out.println("Database connected"); // Create a statement
	   statement = connection.createStatement();
	  // Execute a statement
	}
	  
}
