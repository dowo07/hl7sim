package com.id.hl7sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class HospitalImplTest {

	
	HL7Builder testHl7builder;

	PatientRepository testPatientRepository;

	DatabaseConnection testConnection;

	List<String> testAllHl7s;
 
	Hospital testHospital;
	
	List<Patient> testBothPatients;

	PatientGenerator testPatientGenerator;
	
	DataSource testDataSource;

	
	@Before
	public void setUp() throws Exception {

		testAllHl7s = new ArrayList<String>();

		testBothPatients = new ArrayList<Patient>();
		
		testConnection = new MySqlConnection();

		testHl7builder = Mockito.mock(HL7Builder.class);

		testPatientGenerator = Mockito.mock(PatientGeneratorImpl.class);

		Mockito.when(testHl7builder.createMessage(Mockito.<Patient>any(), Mockito.<Type>any(), Mockito.<Format>any()))
				.thenReturn("||||");
		
		testDataSource = testConnection.getDataSource();
		
		testPatientRepository = new PatientRepositoryMySqlImpl(testDataSource, testPatientGenerator);

		testHospital = new HospitalImpl(10, testHl7builder, testPatientRepository);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testHospitalWithTooLessBeds() {
		testHospital = new HospitalImpl(3, testHl7builder, testPatientRepository);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHospitalWithTooManyBeds() {
		testHospital = new HospitalImpl(1001, testHl7builder, testPatientRepository);
	}

	@Test
	public void testAddPatient() {

		testHospital.addPatient();

		assertEquals(9, testHospital.getCapacity());
	}

	@Test
	public void testRemovePatient() {

		testHospital.addPatient();
		testHospital.addPatient();
		testHospital.removePatient();

		assertEquals(1, testHospital.getOccupiedBeds());
	}

	@Test
	public void testFreeBedsCheckTrue() {

		testHospital.setCapacity(1);

		assertTrue(testHospital.freeBedsCheck());
	}

	@Test
	public void testFreeBedsCheckFalse() {

		testHospital.setCapacity(0);

		assertFalse(testHospital.freeBedsCheck());
	}

	@Test
	public void testOccupiedBedsTrue() {

		testHospital.setOccupiedBeds(9);

		assertTrue(testHospital.occupiedBedsCheck());
	}

	@Test
	public void testOccupiedBedsFalse() {

		testHospital.setOccupiedBeds(0);

		assertFalse(testHospital.occupiedBedsCheck());
	}

	@Test(expected = RuntimeException.class)
	public void testAdmitPatientWhenNoFreeBed() {

		testHospital.setCapacity(0);

		testHospital.admitPatient();
	}

	@Test(expected = RuntimeException.class)
	public void testDischargePatientWhenNoPatientInHospital() {

		testHospital.setOccupiedBeds(0);

		testHospital.dischargePatient();
	}

	@Test
	public void testAdmitRandomPatient() {

		testHospital.admitPatient();
	}

	@Test
	public void testTransferRandomPatient() {

		testHospital.transferPatient();
	}

	@Test
	public void testDischargeRandomPatient() {
		testHospital.addPatient();
		testHospital.dischargePatient();
	}
	
	@Test
	public void testAutoFillHospital() {
		
		testHospital.autoFillHospital();
		
		assertTrue(testHospital.getCapacity() == 0);
	}

}