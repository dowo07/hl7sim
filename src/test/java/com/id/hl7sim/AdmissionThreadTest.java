package com.id.hl7sim;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.id.hl7sim.threads.AdmissionThread;


public class AdmissionThreadTest {
	
	
	Hospital hospitalMock;
	AdmissionThread testAdmissionThread;
	
	
	@Before
	public void setUp() throws Exception {
		
		hospitalMock = Mockito.mock(HospitalImpl.class);
		testAdmissionThread = new AdmissionThread(hospitalMock, 4000);
		
	}
	
	@Test
	public void testSimulateWholeDay() { 
		
		try {
			testAdmissionThread.simulateWholeDay();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, atLeast(18)).admitPatient();
	}
	
	@Test
	public void testSimulateNight() {
		
		//when
		try {
			testAdmissionThread.simulateNight();
		} catch (InterruptedException e) {}
		
		//then
		verify(hospitalMock, times(4)).admitPatient();
		verify(hospitalMock, never()).dischargePatient();
		verify(hospitalMock, never()).transferPatient();
	}
	
	@Test
	public void testSimulateMorning() { 
		
		try {
			testAdmissionThread.simulateMorning();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, times(8)).admitPatient();
	}
	
	@Test
	public void testSimulateAfternoon() {
		
		try {
			testAdmissionThread.simulateAfternoon();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, times(4)).admitPatient();
	}
	
	@Test
	public void testSimulateEvening() {
		
		try {
			testAdmissionThread.simulateEvening();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, times(4)).admitPatient();
	}
	 
	@Test
	public void testGetterSetter() {
		
		int testAccelerationFactor = 42;
		
		testAdmissionThread.setAccelerationFactor(testAccelerationFactor);
		
		int result = testAdmissionThread.getAccelerationFactor();
		
		assertTrue(result == testAccelerationFactor);
	}
	
}