package com.id.hl7sim;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.id.hl7sim.threads.TransferThread;


public class TransferThreadTest { 
	
	
	Hospital hospitalMock;
	TransferThread testTransferThread;
	
	
	@Before
	public void setUp() throws Exception {
		
		hospitalMock = Mockito.mock(HospitalImpl.class);
		testTransferThread = new TransferThread(hospitalMock, 4000);
		
	}
	
	@Test
	public void testSimulateWholeDay() { 
		
		try {
			testTransferThread.simulateWholeDay();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, atLeast(17)).transferPatient();
	}
	
	@Test
	public void testSimulateNight() {
		
		//when
		try {
			testTransferThread.simulateNight();
		} catch (InterruptedException e) {}
		
		//then
		verify(hospitalMock, times(1)).transferPatient();
		verify(hospitalMock, never()).admitPatient();
		verify(hospitalMock, never()).dischargePatient();
	}
	
	@Test
	public void testSimulateMorning() { 
		
		try {
			testTransferThread.simulateMorning();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, times(10)).transferPatient();
	}
	
	@Test
	public void testSimulateAfternoon() {
		
		try {
			testTransferThread.simulateAfternoon();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, times(4)).transferPatient();
	}
	
	@Test
	public void testSimulateEvening() {
		
		try {
			testTransferThread.simulateEvening();
		} catch (InterruptedException e) {}
		
		verify(hospitalMock, times(2)).transferPatient();
	}
	
	@Test
	public void testGetterSetter() {
		
		int testAccelerationFactor = 42;
		
		testTransferThread.setAccelerationFactor(testAccelerationFactor);
		
		int result = testTransferThread.getAccelerationFactor();
		
		assertTrue(result == testAccelerationFactor);
	}
	
}