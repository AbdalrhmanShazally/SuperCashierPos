package database;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBIntialize2 {
	
	private Connection connection;
	private Statement statement;
	
	public DBIntialize2() throws ClassNotFoundException,SQLException, InstantiationException,IllegalAccessException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		System.out.println("Driver Loaded");
		
		connection = (Connection) MySQLconnUtils.getConnection();
		System.out.println("Database Connected");
		
		statement = (Statement) connection.createStatement();
		
	}
	
	public Statement getStatement() {
		return statement;
	}
	
	
	public void close() throws SQLException {
		
		if(statement != null) statement.close();
		if(connection != null) connection.close();
		
	}

}
