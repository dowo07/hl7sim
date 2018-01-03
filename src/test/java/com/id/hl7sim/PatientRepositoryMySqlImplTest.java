package com.id.hl7sim;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.id.hl7sim.xml.Departments;
import com.id.hl7sim.xml.Firstnames;
import com.id.hl7sim.xml.Lastnames;
import com.id.hl7sim.xml.Wards;

import java.util.List;

import javax.xml.bind.JAXB;

import java.util.ArrayList;


public class PatientRepositoryMySqlImplTest {

	Patient testPatient;

	Patient testPatientTwo;

	List<Patient> testBothPatients;

	DatabaseConnection testConnection;

	PatientGenerator testPatientGenerator;

	Firstnames testFirstnames;

	Lastnames testLastnames;

	Departments testDepartments;

	Wards testWards;

	PatientRepositoryMySqlImpl testPatientRepository;

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

		testConnection = new MySqlConnection();
		
		testPatientRepository = new PatientRepositoryMySqlImpl(testPatientGenerator);
		
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



}