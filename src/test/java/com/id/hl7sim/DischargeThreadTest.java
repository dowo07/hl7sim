package com.id.hl7sim;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.id.hl7sim.hospital.Hospital;
import com.id.hl7sim.hospital.HospitalImpl;
import com.id.hl7sim.threads.DischargeThread;


public class DischargeThreadTest {
	
	
	Hospital hospitalMock;
	DischargeThread testDischargeThread;
	
	
	@Before
	public void setUp() throws Exception {
		
		hospitalMock = Mockito.mock(HospitalImpl.class);
		testDischargeThread = new DischargeThread(hospitalMock, 12000);
		
	}
	
	@Test
	public void testSimulateWholeDay() { 
		
		try {
			testDischargeThread.simulateWholeDay();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, atLeast(18)).dischargePatient();
	}
	
	@Test
	public void testSimulateNight() {
		
		//when
		try {
			testDischargeThread.simulateNight();
		} catch (InterruptedException e) {}
		
		//then
		verify(hospitalMock, times(2)).dischargePatient();
		verify(hospitalMock, never()).admitPatient();
		verify(hospitalMock, never()).transferPatient();
	}
	
	@Test
	public void testSimulateMorning() { 
		
		try {
			testDischargeThread.simulateMorning();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, times(8)).dischargePatient();
	}
	
	@Test
	public void testSimulateAfternoon() {
		
		try {
			testDischargeThread.simulateAfternoon();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, times(8)).dischargePatient();
	}
	
	@Test
	public void testSimulateEvening() {
		
		try {
			testDischargeThread.simulateEvening();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, times(2)).dischargePatient();
	}
	
	@Test
	public void testGetterSetter() {
		
		int testAccelerationFactor = 42;
		
		testDischargeThread.setAccelerationFactor(testAccelerationFactor);
		
		int result = testDischargeThread.getAccelerationFactor();
		
		assertTrue(result == testAccelerationFactor);
	}
	
}