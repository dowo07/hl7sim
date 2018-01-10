package com.id.hl7sim;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
	
	
	
}