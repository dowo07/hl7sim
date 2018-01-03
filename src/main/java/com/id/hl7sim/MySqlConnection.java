package com.id.hl7sim;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySqlConnection implements DatabaseConnection {


	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_CONNECTION = "jdbc:mysql://localhost/new_schema?verifyServerCertificate=false&useSSL=true";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "root";
 
	 
	/* (non-Javadoc)
	 * @see com.id.hl7sim.DatabaseConnection#getDBConnection()
	 */
	@Override
	public Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection; 
	}


}
