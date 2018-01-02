package com.id.hl7sim;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;

import javax.xml.bind.JAXB;

import com.id.hl7sim.xml.Departments;
import com.id.hl7sim.xml.Firstnames;
import com.id.hl7sim.xml.Lastnames;
import com.id.hl7sim.xml.Wards;


public class PatientGeneratorImplTest {
	
	
	public PatientGenerator testPatientGenerator;
	
	public Firstnames testFirstnames;
	
	public Lastnames testLastnames;
	
	public Departments testDepartments;
	
	public Wards testWards;
	
	public List<Patient> testAllPatients;
	
	public Patient testPatient;

	public LocalDate testDate;

	
	@Before
	public void setUp() {
		
		testDepartments = JAXB.unmarshal(ClassLoader.getSystemResource("departments.xml"), Departments.class);
		testWards = JAXB.unmarshal(ClassLoader.getSystemResource("wards.xml"), Wards.class);
		testLastnames = JAXB.unmarshal(ClassLoader.getSystemResource("lastnames.xml"), Lastnames.class);
		testFirstnames = JAXB.unmarshal(ClassLoader.getSystemResource("firstnames.xml"), Firstnames.class);
		
		testPatient = new Patient.Builder().build();
		
		testAllPatients = new ArrayList<Patient>();
		
		testPatientGenerator = new PatientGeneratorImpl(testFirstnames, testLastnames, testDepartments, testWards);
	
	}
	
	
	@Test
	public void testRandomizeNewPatient() {
	  
	  Patient patient = testPatientGenerator.randomizeNewPatient();
	  
	  assertNotNull(patient.getFirstname());
	  assertNotNull(patient.getLastname());
	  assertNotNull(patient.getBirthday());
	  assertNotNull(patient.getGender());
	}
	
	@Test
	public void testCreateRandomPatients() {
		
		List<Patient> allpatients = testPatientGenerator.createRandomPatients(10);

		assertNotNull(allpatients);
		assertEquals(10, allpatients.size());
	}
	
	@Test
	public void testSetRandomFirstname() {
	
		String testFirstName = testPatientGenerator.getRandomFirstName();
		
		assertNotNull(testFirstName);
		assertTrue(testFirstName.length() >= 3);
	}
	
	@Test
	public void testSetRandomLastname() {
		
		String testLastName = testPatientGenerator.getRandomLastName();
		
		assertNotNull(testLastName);
		assertTrue(testLastName.length() >=3);
	}
	
	@Test
	public void testSetRandomDepartment() {
		
		String ward = testPatientGenerator.getRandomWard();
		String department = testPatientGenerator.getRandomDepartment();
		
		assertNotNull(ward);
		assertNotNull(department);
		assertFalse(ward == department);
	}
	
	@Test
	public void testSetRandomBrithday() {
		
		testPatient.setBirthday(testPatientGenerator.getRandomBirthday());
		
		LocalDate testBirthday = testPatient.getBirthday();
		
		assertTrue(testBirthday.isAfter(LocalDate.of(1899, 12, 31)));
		assertTrue(testBirthday.isBefore(LocalDate.now()));
		assertNotNull(testBirthday);
	}
	
	@Test
	public void testGetRandomDateInRange() {
		
		testPatient.setBirthday(testPatientGenerator.getRandomBirthday());
		
		assertTrue(testPatient.getBirthday().isBefore(LocalDate.now()));
		assertTrue(testPatient.getBirthday().isAfter(LocalDate.of(1900, 1, 1)));
	}
	
	@Test
	public void testToString() {
		
		String result = testPatient.toString();
		
		assertTrue(result.contains("|CASE-ID: "));
		assertTrue(result.contains(" | GENDER:"));
	}
	
	@Test
	public void testGetRandomWard() {
		
		String result = testPatientGenerator.getRandomWard();
		
		assertTrue(result != null && result != "");
	}
	

}