package com.id.hl7sim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.id.hl7sim.database.DatabaseManager;
import com.id.hl7sim.database.PatientRepository;
import com.id.hl7sim.database.PatientRepositoryMSSqlImpl;
import com.id.hl7sim.hl7.Format;
import com.id.hl7sim.hl7.HL7Builder;
import com.id.hl7sim.hl7.HL7Endpoint;
import com.id.hl7sim.hl7.HL7Sender;
import com.id.hl7sim.hl7.HL7SenderImpl;
import com.id.hl7sim.hl7.HL7SocketEndpoint;
import com.id.hl7sim.hl7.Type;
import com.id.hl7sim.hospital.Hospital;
import com.id.hl7sim.hospital.HospitalImpl;
import com.id.hl7sim.patient.Patient;
import com.id.hl7sim.patient.PatientGenerator;
import com.id.hl7sim.patient.PatientGeneratorImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;


public class HospitalImplTest {

	
	HL7Builder testHl7builder;

	PatientRepository testPatientRepository;

	List<String> testAllHl7s;
 
	Hospital testHospital;
	
	List<Patient> testBothPatients;

	PatientGenerator testPatientGenerator;
	
	DataSource testDataSource;
	
	HL7Sender testSender;
	
	HL7Endpoint testEndpoint;
	
	
	
	ComboPooledDataSource cpds;
	
	@Before
	public void setUp() throws Exception {

		testAllHl7s = new ArrayList<String>();

		testBothPatients = new ArrayList<Patient>();
		
		cpds = DatabaseManager.provideDataSource("MSSql");
		
		testHl7builder = Mockito.mock(HL7Builder.class);

		testPatientGenerator = Mockito.mock(PatientGeneratorImpl.class);

		Mockito.when(testHl7builder.createMessage(Mockito.<Patient>any(), Mockito.<Type>any(), Mockito.<Format>any()))
				.thenReturn("||||");
		
		
		testEndpoint = new HL7SocketEndpoint("localhost", 6661);
		
		testSender = new HL7SenderImpl(testEndpoint);
		
		testPatientRepository = new PatientRepositoryMSSqlImpl(cpds, testPatientGenerator);

		testHospital = new HospitalImpl(10, testHl7builder, testSender, testPatientRepository);
 
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHospitalWithTooLessBeds() {
		testHospital = new HospitalImpl(3, testHl7builder, testSender, testPatientRepository); 
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHospitalWithTooManyBeds() {
		testHospital = new HospitalImpl(1001, testHl7builder, testSender, testPatientRepository);
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