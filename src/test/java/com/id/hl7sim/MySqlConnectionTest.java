package com.id.hl7sim;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class MySqlConnectionTest {
	
	public DatabaseConnection testMySqlConnection;
	
	@Before
	public void setUp() throws Exception {
		testMySqlConnection = new MySqlConnection();
		
	}

	@Test
	public void test() {
		java.sql.Connection connection = testMySqlConnection.getDBConnection();
		assertTrue(connection != null);
	}

}
