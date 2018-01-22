package com.id.hl7sim;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.id.hl7sim.hospital.Hospital;
import com.id.hl7sim.hospital.HospitalImpl;
import com.id.hl7sim.threads.AdmissionThread;


public class AdmissionThreadTest {
	
	 
	Hospital hospitalMock;
	AdmissionThread testAdmissionThread;
	
	
	@Before
	public void setUp() throws Exception {
		
		hospitalMock = Mockito.mock(HospitalImpl.class);
		testAdmissionThread = new AdmissionThread(hospitalMock, 12000);
		
	}
	
	@Test
	public void testSimulateWholeDay() { 
		
		AdmissionThread spy = Mockito.spy(testAdmissionThread);
		try {
			spy.simulateWholeDay();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(20)).doAdmission();
	}
	
	@Test
	public void testSimulateNight() {
		
		AdmissionThread spy = Mockito.spy(testAdmissionThread);
		try {
			spy.simulateNight();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(4)).doAdmission();
	}
	
	@Test
	public void testSimulateMorning() { 
		
		AdmissionThread spy = Mockito.spy(testAdmissionThread);
		try {
			spy.simulateMorning();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(8)).doAdmission();
	}
	
	@Test
	public void testSimulateAfternoon() {
		
		AdmissionThread spy = Mockito.spy(testAdmissionThread);
		try {
			spy.simulateAfternoon();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(4)).doAdmission();
	}
	
	@Test
	public void testSimulateEvening() {
		
	
		AdmissionThread spy = Mockito.spy(testAdmissionThread);
		try {
			spy.simulateEvening();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(4)).doAdmission();
	}
	 
	@Test
	public void testGetterSetter() {
		
		int testAccelerationFactor = 42;
		
		testAdmissionThread.setAccelerationFactor(testAccelerationFactor);
		
		int result = testAdmissionThread.getAccelerationFactor();
		
		assertTrue(result == testAccelerationFactor);
	}
	
}