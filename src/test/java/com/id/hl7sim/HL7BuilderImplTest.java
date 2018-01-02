package com.id.hl7sim;


import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class HL7BuilderImplTest {
	
	
	HL7Builder testHl7builder;
	
	List<String> testAllHl7s;
	
	Patient testPatient;
	
	Patient testPatientTwo;
	
	List<Patient> testBothPatients;
	
	FileWriter fileWriter;
	
	
	@Before
	public void setUp() throws Exception {
		
		
		testHl7builder = new HL7BuilderImpl();
		
		testPatient = new Patient.Builder().build();
		testPatient.setBirthday(LocalDate.of(1911, 11, 11));
		testPatient.setFirstname("Test");
		testPatient.setLastname("Mann");
		testPatient.setGender("M");
		testPatient.setAdmissionDateTime(LocalDateTime.now());
		testPatient.setDischargeDateTime(LocalDateTime.now());
		
		testPatientTwo = new Patient.Builder().build();
		testPatientTwo = testPatient;
	
		testBothPatients = new ArrayList<Patient>();
		
		testAllHl7s = new ArrayList<String>();
		
		testBothPatients.add(testPatient);
		testBothPatients.add(testPatientTwo);
		
	
		fileWriter = new FileWriter("testFile");
	}
	
	
	@Test
	public void testGetMessageControlId() {
		
		int result = HL7BuilderImpl.getMessageControlId();
		
		assertTrue(result == 0);
	}
	
	@Test
	public void testAllHl7sAtBegin() {
		
		int result = testAllHl7s.size();
		
		assertTrue(result == 0);
	}
	
	@Test
	public void testAddingOneHl7ToAllHl7s() {
		
		String test = "Test";
		testAllHl7s.add(test);
		
		int result = testAllHl7s.size();
		
		assertTrue(result == 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageWithNullValuesForOnePatient() {
	
		testHl7builder.createMessage((Patient)null, null, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageOnePatientWithoutType() {
		
		testHl7builder.createMessage(testPatient, null, Format.PIPE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageOnePatientWithoutFormat() {
		
		testHl7builder.createMessage(testPatient, Type.ADMISSION, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageOnePatientWithWrongFormat() {
		
		testHl7builder.createMessage(testPatient, Type.ADMISSION, Format.UNKNOWN);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageOnePatientWithWrongType() {
		
		testHl7builder.createMessage(testPatient, Type.UNKNOWN, Format.PIPE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreatePipeMessageOnePatientNoType() {
		
		testHl7builder.createPipeMessage(testPatient, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreatePipeMessageWithoutOnePatient() {
		
		testPatient = null;
		
		testHl7builder.createPipeMessage(testPatient, Type.ADMISSION);
	}
	
	@Test
	public void testCreatePipeMessageOnePatientWrongType() {
		
		testHl7builder.createPipeMessage(testPatient, Type.TRANSFER);
	}
	
	@Test
	public void testPatientSetFirstNameName() {
		// Given
		testPatient.setFirstname("Albert");
		// When
		String result = testHl7builder.createMessage(testPatient, Type.ADMISSION, Format.PIPE);
		// Then
		assertTrue(result.contains("^Albert|"));		
	}
	
	@Test
	public void testPatientSetLastName() {
	
		testPatient.setLastname("Meier");
		
		String result = testHl7builder.createMessage(testPatient, Type.ADMISSION, Format.PIPE);
		
		assertTrue(result.contains("|Meier^"));		
	}
	
	@Test
	public void testcreatePipeMessageAdmission() {
	
		String result = testHl7builder.createMessage(testPatient, Type.ADMISSION, Format.PIPE);
		
		assertTrue(result.contains("|ADT^A01^ADT_A01"));
	}
	
	@Test 
	public void testcreatePipeMessageDischarge() {
		
		String result = testHl7builder.createMessage(testPatient, Type.DISCHARGE, Format.PIPE);
		
		assertTrue(result.contains("|ADT^A03^ADT_A03"));
	}
	
	@Test
	public void testWithIncompletePatientData() {
		
		testPatient.setLastname("");
		
		String result = testHl7builder.createPipeMessage(testPatient, Type.ADMISSION);
		
		System.out.println(result);
	}
	
	
}