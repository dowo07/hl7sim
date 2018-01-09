package com.id.hl7sim;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DatabaseManager {

	public static ComboPooledDataSource provideDataSource(String databaseType) {
		ComboPooledDataSource cpds = null;
		
		switch (databaseType) {
		
		case "MSSql":
			cpds = new ComboPooledDataSource();
				cpds.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=HL7Sim");
				cpds.setUser("scorer");
				cpds.setPassword("scorer");
				cpds.setInitialPoolSize(5);
				cpds.setMinPoolSize(5);
				cpds.setAcquireIncrement(5);
				cpds.setMaxPoolSize(20);
				cpds.setMaxStatements(100);
			return cpds;
			
		case "MySql":
			cpds = new ComboPooledDataSource();
				cpds.setJdbcUrl("jdbc:mysql://localhost/new_schema");
				cpds.setUser("root");
				cpds.setPassword("root");
				cpds.setInitialPoolSize(5);
				cpds.setMinPoolSize(5);
				cpds.setAcquireIncrement(5);
				cpds.setMaxPoolSize(20);
				cpds.setMaxStatements(100);
			return cpds;
			
		default:
			return null;
		}
	}

}
