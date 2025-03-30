package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import security.EncryptionUtil;

public class MySQLconnUtils {
	
	
	    //Connection Strings:
	    static String username ;
	    static String password ;
	    static String hostName ;
	    static String dbName   ;
	    static String port     ;
	    static String url 	   ;
	    
	    static {
	    	try {
	    		Properties props = new Properties();
	    		FileInputStream inputStream = new FileInputStream("dbconfig.txt");
	    		props.load(inputStream);
	    		
	    		
	    		//Assign values to Connection Strings:
	    		username = EncryptionUtil.decrypt(props.getProperty("username")) ;
	    		password = EncryptionUtil.decrypt(props.getProperty("password")) ;
	    		hostName = EncryptionUtil.decrypt(props.getProperty("hostname")) ;
	    		dbName 	 = EncryptionUtil.decrypt(props.getProperty("dbname")) ;
	    		port	 = EncryptionUtil.decrypt(props.getProperty("port")) ;
	    		
	    		
	    		//build Connection URL:
	            url = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName;
	            inputStream.close();
	    	}catch(IOException e) {
	    		
	    		throw new RuntimeException("Failed to load database configuration.");
	    	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    
	    
	    public static Connection getConnection() throws ClassNotFoundException,SQLException {

	         Connection conn = DriverManager.getConnection(url,username,password);
	         conn.setAutoCommit(false);
	         return conn;

	    }
/*
		// commented by a.shazally on 26-03-2025 changing ConnectionUtils class.
        public static Connection getMySQLConnection()
                throws ClassNotFoundException, SQLException {
            String hostName = "localhost";
            String dbName = "ucsmpos";
            String userName = "supercashier";
            String password = "supercashier";
            return getMySQLConnection(hostName, dbName, userName, password);
        }

        public static Connection getMySQLConnection(String hostName, String dbName,
                String userName, String password) throws SQLException,
                ClassNotFoundException {

            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Get qq ... ");
            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

            Connection conn = DriverManager.getConnection(connectionURL, userName,
                    password);
            return conn;
        }
        */
	
    }