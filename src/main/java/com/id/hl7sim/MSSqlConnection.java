package com.id.hl7sim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSqlConnection implements DatabaseConnection {

	private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_CONNECTION = "jdbc:sqlserver://localhost:1433;databaseName=HL7Sim;user=scorer;password=scorer";



	/* (non-Javadoc)
	 * @see com.id.hl7sim.DatabaseConnection#getDBConnection()
	 */
	@Override
	public Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
			dbConnection = DriverManager.getConnection(DB_CONNECTION);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

}