package com.id.hl7sim;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PatientTest {
	
	Patient testPatient;
	Patient testPatientTwo;
	
	@Before
	public void setUp() throws Exception {
		
		testPatient = new Patient.Builder()
				.firstname("John")
				.lastname("Doe")
				.build();
	
	}

	@Test
	public void testCopyConstructor() {
		testPatientTwo = Patient.newInstance(testPatient);
				
		
		assertTrue(testPatientTwo.getLastname() == "Doe");
		
	}

}
