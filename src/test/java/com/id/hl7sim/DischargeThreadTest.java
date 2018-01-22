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
		
		DischargeThread spy = Mockito.spy(testDischargeThread);
		try {
			spy.simulateWholeDay();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(18)).doDischarge();
	}
	
	@Test
	public void testSimulateNight() {
		
		DischargeThread spy = Mockito.spy(testDischargeThread);
		try {
			spy.simulateNight();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(2)).doDischarge();
	}
	
	@Test
	public void testSimulateMorning() { 
		
		DischargeThread spy = Mockito.spy(testDischargeThread);
		try {
			spy.simulateMorning();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(7)).doDischarge();
	}
	
	@Test
	public void testSimulateAfternoon() {
		
		DischargeThread spy = Mockito.spy(testDischargeThread);
		try {
			spy.simulateAfternoon();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(7)).doDischarge();
	}
	
	@Test
	public void testSimulateEvening() {
		
		DischargeThread spy = Mockito.spy(testDischargeThread);
		try {
			spy.simulateEvening();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		verify(spy, times(2)).doDischarge();
	}
	
	@Test
	public void testGetterSetter() {
		
		int testAccelerationFactor = 42;
		
		testDischargeThread.setAccelerationFactor(testAccelerationFactor);
		
		int result = testDischargeThread.getAccelerationFactor();
		
		assertTrue(result == testAccelerationFactor);
	}
	
}