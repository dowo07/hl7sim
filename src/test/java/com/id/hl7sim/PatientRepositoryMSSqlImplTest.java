package com.id.hl7sim;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.id.hl7sim.database.PatientRepository;
import com.id.hl7sim.database.PatientRepositoryMSSqlImpl;
import com.id.hl7sim.patient.Patient;
import com.id.hl7sim.patient.PatientGenerator;
import com.id.hl7sim.patient.PatientGeneratorImpl;
import com.id.hl7sim.xml.Departments;
import com.id.hl7sim.xml.Firstnames;
import com.id.hl7sim.xml.Lastnames;
import com.id.hl7sim.xml.Wards;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import javax.xml.bind.JAXB;


public class PatientRepositoryMSSqlImplTest {
 
	 
	PatientRepository testPatientRepository;

	Patient testPatient;
	
	Patient testPatientTwo;

	List<Patient> testBothPatients;
 
	PatientGenerator testPatientGenerator;
	
	Firstnames testFirstnames;
	
	Lastnames testLastnames;
	
	Departments testDepartments;
	
	Wards testWards;
	
	PatientRepository myMock;

	DataSource testDataSource;
	
	ComboPooledDataSource cpds;
	
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
		
		cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=HL7Sim");
		cpds.setUser("scorer");
		cpds.setPassword("scorer");
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(100);
		
		testPatientRepository = new PatientRepositoryMSSqlImpl(cpds, testPatientGenerator);
		testPatientRepository.insertListOfPatients(testBothPatients);
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
	
	@Test(expected=SQLException.class)
	public void testMe() {
	 
	    	PatientRepository dao = mock(PatientRepository.class);
	    	Mockito.doThrow(SQLException.class).when(dao).insertPatient(Mockito.any(Patient.class));
	    	dao.insertPatient(testPatient);
	}
	
}