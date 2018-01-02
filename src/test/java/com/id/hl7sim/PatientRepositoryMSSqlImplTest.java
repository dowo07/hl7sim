package com.id.hl7sim;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.id.hl7sim.xml.Departments;
import com.id.hl7sim.xml.Firstnames;
import com.id.hl7sim.xml.Lastnames;
import com.id.hl7sim.xml.Wards;


import java.util.List;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.xml.bind.JAXB;

public class PatientRepositoryMSSqlImplTest {

	
	PatientRepository testPatientRepository;

	Patient testPatient;
	
	Patient testPatientTwo;

	List<Patient> testBothPatients;

	DatabaseConnection testConnection;

	PatientGenerator testPatientGenerator;
	
	Firstnames testFirstnames;
	
	Lastnames testLastnames;
	
	Departments testDepartments;
	
	Wards testWards;
	
	PatientRepository myMock;

	
	
	
	@Before
	public void setUp() throws Exception {

		testDepartments = JAXB.unmarshal(ClassLoader.getSystemResource("departments.xml"), Departments.class);
		testWards = JAXB.unmarshal(ClassLoader.getSystemResource("wards.xml"), Wards.class);
		testLastnames = JAXB.unmarshal(ClassLoader.getSystemResource("lastnames.xml"), Lastnames.class);
		testFirstnames = JAXB.unmarshal(ClassLoader.getSystemResource("firstnames.xml"), Firstnames.class);
		
		testPatientGenerator = new PatientGeneratorImpl(testFirstnames, testLastnames, testDepartments, testWards);
		
		testPatient = testPatientGenerator.randomizeNewPatient();
		
		testPatientTwo = testPatientGenerator.randomizeNewPatient();
		
		testBothPatients = new ArrayList<Patient>();
		
		testConnection = new MSSqlConnection();
		
		testPatientRepository = new PatientRepositoryMSSqlImpl(testConnection, testPatientGenerator);
		
		testPatientRepository.admitRandomPatient();
		
		myMock = Mockito.mock(PatientRepositoryMSSqlImpl.class);
		
	}

	@Test
	public void testAdmitRandomPatient() {

		testPatient = testPatientRepository.admitRandomPatient();

		assertTrue(testPatient.isValid());
	}

	@Test
	public void testGetRandomInpatient() {

		testPatient = testPatientRepository.getRandomInpatient();

		assertTrue(testPatient.isValid());
	}

	@Test
	public void testDischargeRandomPatientValid() {

		testPatient = testPatientRepository.dischargeRandomPatient();

		assertTrue(testPatient.isValid());
		assertTrue(testPatient.getCaseId() != 0);
	}

	@Test
	public void testDischargeRandomPatientDatabase() {

		int beforeDischarge = testPatientRepository.countInpatients();
		testPatient = testPatientRepository.dischargeRandomPatient();
		int afterDischarge = testPatientRepository.countInpatients();

		assertTrue(afterDischarge == beforeDischarge - 1);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertPatientWitIncompletePatient() {
		
		testPatient.setFirstname("");
		
		testPatientRepository.insertPatient(testPatient);
	}
		
	@Test
	public void testTransferRandomPatient() {

		testPatient = testPatientRepository.transferRandomPatient();

		assertTrue(testPatient.getDepartment() != testPatient.getPriorDepartment());
	}

	@Test
	public void testInsertPatient() {

		int numberOfPatients = testPatientRepository.countInpatients();

		testPatientRepository.insertPatient(testPatient);

		assertTrue(testPatientRepository.countInpatients() >= numberOfPatients);
	}
	
	@Test
	public void testInsertListOfPatients() {
		
		testBothPatients.add(testPatient);
		testBothPatients.add(testPatientTwo);
		
		int countInpatientsBeforeInsertion = testPatientRepository.countPatients();
		
		testPatientRepository.insertListOfPatients(testBothPatients);
		
		int countInpatientsAfterInsertion = testPatientRepository.countPatients();
		assertTrue(countInpatientsAfterInsertion > countInpatientsBeforeInsertion);
	} 
	
	@Test(expected=SQLException.class)
	public void testMe() {
	 
	    	PatientRepository dao = mock(PatientRepository.class);
	    	Mockito.doThrow(SQLException.class).when(dao).insertPatient(Mockito.any(Patient.class));
	    	dao.insertPatient(testPatient);
	    	
	    	
	}
	
}
	    
	       
	 
	
