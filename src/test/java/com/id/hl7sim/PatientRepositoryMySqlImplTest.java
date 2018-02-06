package com.id.hl7sim;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.id.hl7sim.database.DatabaseManager;
import com.id.hl7sim.database.PatientRepositoryMySqlImpl;
import com.id.hl7sim.patient.Patient;
import com.id.hl7sim.patient.PatientGenerator;
import com.id.hl7sim.patient.PatientGeneratorImpl;
import com.id.hl7sim.xml.Departments;
import com.id.hl7sim.xml.Firstnames;
import com.id.hl7sim.xml.Lastnames;
import com.id.hl7sim.xml.Wards;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.util.List;
import javax.xml.bind.JAXB;
import java.util.ArrayList;

public class PatientRepositoryMySqlImplTest {

	Patient testPatient;

	Patient testPatientTwo;

	List<Patient> testBothPatients;

	PatientGenerator testPatientGenerator;

	Firstnames testFirstnames; 

	Lastnames testLastnames;

	Departments testDepartments;

	Wards testWards;

	PatientRepositoryMySqlImpl testPatientRepository;
	
	ComboPooledDataSource cpds;
	
	DatabaseManager dbmgr;
	
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
		
		ComboPooledDataSource cpds = DatabaseManager.provideDataSource("MySql");
		
		cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl("jdbc:mysql://localhost/new_schema");
		cpds.setUser("root");
		cpds.setPassword("root");
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(100);
		
		testPatientRepository = new PatientRepositoryMySqlImpl(cpds, testPatientGenerator);
		
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
		assertTrue(testPatient.getInstance() != "0");
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