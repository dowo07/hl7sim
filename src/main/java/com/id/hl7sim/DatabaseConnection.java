package com.id.hl7sim;

import java.sql.Connection;

import javax.sql.DataSource;

public interface DatabaseConnection {

	public Connection getDBConnection();
	
	public DataSource getDataSource();
}