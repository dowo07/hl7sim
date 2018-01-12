package com.id.hl7sim;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import com.id.hl7sim.database.PatientRepository;
import com.id.hl7sim.hl7.HL7Builder;
import com.id.hl7sim.hl7.HL7Sender;
import com.id.hl7sim.hospital.Hospital;
import com.id.hl7sim.hospital.HospitalImpl;


public class HospitalImplTest {
 
	
	Hospital testHospital;
	HL7Builder testHl7builderMock;
	HL7Sender testHL7SenderMock;
	PatientRepository testPatientRepositoryMock;

	
	@Before
	public void setUp() throws Exception {

		testHl7builderMock = mock(HL7Builder.class);
		
		testHL7SenderMock = mock(HL7Sender.class);
		
		testPatientRepositoryMock = mock(PatientRepository.class);
		
		testHospital = new HospitalImpl(10, testHl7builderMock, testHL7SenderMock, testPatientRepositoryMock);

		when(testHl7builderMock.createMessage(any(), any(), any())).thenReturn("||||");
	}


	@Test(expected = IllegalArgumentException.class)
	public void testHospitalWithTooLessBeds() {
		
		testHospital = new HospitalImpl(3, testHl7builderMock, testHL7SenderMock, testPatientRepositoryMock); 
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHospitalWithTooManyBeds() {
		
		testHospital = new HospitalImpl(1001, testHl7builderMock, testHL7SenderMock, testPatientRepositoryMock);
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