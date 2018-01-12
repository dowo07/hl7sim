package com.id.hl7sim;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.id.hl7sim.hl7.Format;
import com.id.hl7sim.hl7.HL7Builder;
import com.id.hl7sim.hl7.HL7BuilderImpl;
import com.id.hl7sim.hl7.Type;
import com.id.hl7sim.patient.Patient;


public class HL7BuilderImplTest {
	
	 
	HL7Builder testHl7builder;
	Patient testPatientMock;
	List<Patient> testPatients;
	List<String> testAllHl7s;
	
	
	@Before
	public void setUp() throws Exception {
		
		testHl7builder = new HL7BuilderImpl();
		
		testPatientMock = Mockito.mock(Patient.class);
		Mockito.when(testPatientMock.getId()).thenReturn(42);
		Mockito.when(testPatientMock.getLastname()).thenReturn("Meier");
		Mockito.when(testPatientMock.getFirstname()).thenReturn("Albert");
		Mockito.when(testPatientMock.getGender()).thenReturn("M");
		Mockito.when(testPatientMock.getBirthday()).thenReturn(LocalDate.now());
		
		
		testPatients = new ArrayList<Patient>();
		
		testAllHl7s = new ArrayList<String>();
		
		testPatients.add(testPatientMock);
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
		
		testHl7builder.createMessage(testPatientMock, null, Format.PIPE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageOnePatientWithoutFormat() {
		
		testHl7builder.createMessage(testPatientMock, Type.ADMISSION, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageOnePatientWithWrongFormat() {
		
		testHl7builder.createMessage(testPatientMock, Type.ADMISSION, Format.UNKNOWN);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateMessageOnePatientWithWrongType() {
		
		testHl7builder.createMessage(testPatientMock, Type.UNKNOWN, Format.PIPE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreatePipeMessageOnePatientNoType() {
		
		testHl7builder.createPipeMessage(testPatientMock, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreatePipeMessageWithoutOnePatient() {
		
		testPatientMock = null;
		
		testHl7builder.createPipeMessage(testPatientMock, Type.ADMISSION);
	}
	
	@Test
	public void testCreatePipeMessageOnePatientWrongType() {
		
		testHl7builder.createPipeMessage(testPatientMock, Type.TRANSFER);
	}
	
	@Test
	public void testPatientSetFirstNameName() {
	
		String result = testHl7builder.createMessage(testPatientMock, Type.ADMISSION, Format.PIPE);
	
		assertTrue(result.contains("^Albert|"));		
	}
	
	@Test
	public void testPatientSetLastName() {
	
		String result = testHl7builder.createMessage(testPatientMock, Type.ADMISSION, Format.PIPE);
		
		assertTrue(result.contains("|Meier^"));		
	}
	
	@Test
	public void testcreatePipeMessageAdmission() {
	
		String result = testHl7builder.createMessage(testPatientMock, Type.ADMISSION, Format.PIPE);
		
		assertTrue(result.contains("|ADT^A01^ADT_A01"));
	}
	
	@Test 
	public void testcreatePipeMessageDischarge() {
		
		String result = testHl7builder.createMessage(testPatientMock, Type.DISCHARGE, Format.PIPE);
		
		assertTrue(result.contains("|ADT^A03^ADT_A03"));
	}
	
	
}