package com.id.hl7sim;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import com.id.hl7sim.hospital.Hospital;
import com.id.hl7sim.hospital.HospitalImpl;
import com.id.hl7sim.hospital.HospitalTimeSimulator;
import com.id.hl7sim.hospital.HospitalTimeSimulatorImpl;
import com.id.hl7sim.threads.AdmissionThread;
import com.id.hl7sim.threads.DischargeThread;
import com.id.hl7sim.threads.ProcessThread;
import com.id.hl7sim.threads.TransferThread;


public class HospitalTimeSimulatorImplTest {

	
	Hospital hospitalMock;
	ProcessThread admissionThreadMock;
	ProcessThread dischargeThreadMock;
	ProcessThread transferThreadMock;
	HospitalTimeSimulator testHospitalTimeSimulator;
	
	
	@Before
	public void setUp() throws Exception {
		 
		hospitalMock = Mockito.mock(HospitalImpl.class);
		admissionThreadMock = Mockito.mock(AdmissionThread.class);
		dischargeThreadMock = Mockito.mock(DischargeThread.class);
		transferThreadMock = Mockito.mock(TransferThread.class);
		
		testHospitalTimeSimulator = new HospitalTimeSimulatorImpl(hospitalMock, 4000);
	}
	
	@Test
	public void testGetterSetter() {
		
		int testAccelerationFactor = 42;
		
		testHospitalTimeSimulator.setAccelerationFactor(testAccelerationFactor);
		
		int result = testHospitalTimeSimulator.getAccelerationFactor();
		
		assertTrue(result == testAccelerationFactor);
	}
	
	@Test
	public void testGetumberOfpatientsForInitializing() {
		
		Mockito.when(hospitalMock.getCapacity()).thenReturn(42);
		
		int result = testHospitalTimeSimulator.getNumberOfpatientsForInitializing();
		
		assertTrue(result == 31);
	}
	
	@Test
	public void testInitHospital() {
		
		Mockito.when(hospitalMock.getCapacity()).thenReturn(42);
		
		testHospitalTimeSimulator.initHospital();
		
		verify(hospitalMock, times(31)).admitPatient();
	}
	
	@Test
	public void testGetAndSetHospital() {
		
		Mockito.when(hospitalMock.getCapacity()).thenReturn(123);
		
		testHospitalTimeSimulator.setHospital(hospitalMock);
		
		int result = testHospitalTimeSimulator.getHospital().getCapacity();
		
		assertTrue(result == 123);
	}
	
	@Test
	public void testSimulateDay() {
		
		testHospitalTimeSimulator.simulateDay();
		
		verify(hospitalMock, times(1)).getOccupiedBeds();
	}
	
	

	
}