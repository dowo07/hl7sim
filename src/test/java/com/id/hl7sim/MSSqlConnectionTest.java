package com.id.hl7sim;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class MSSqlConnectionTest {
	
	public DatabaseConnection testMSSqlConnection;
	
	@Before
	public void setUp() throws Exception {
		testMSSqlConnection = new MySqlConnection();
		
	}

	@Test
	public void test() {
		java.sql.Connection connection = testMSSqlConnection.getDBConnection();
		assertTrue(connection != null);
	}

}