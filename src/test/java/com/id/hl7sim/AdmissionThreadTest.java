//package com.id.hl7sim;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import com.id.hl7sim.threads.AdmissionThread;
//
//public class AdmissionThreadTest {
//	
//	AdmissionThread testAdmissionThread;
//	
//	
//	@Before
//	public void setUp() throws Exception {
//		testAdmissionThread = Mockito.mock(AdmissionThread.class);
//	
//	}
//
//	@Test
//	public void testGetAndSetAccelerationFactor() {
//		testAdmissionThread.setAccelerationFactor(3000);
//		
//		int result = testAdmissionThread.getAccelerationFactor();
//		
//		assertTrue(result == 3000);
//	}
//	
//	@Test
//	public void testSimulateMorning() throws InterruptedException {
//		testAdmissionThread.simulateWholeDay();
//		
//		Mockito.verify(testAdmissionThread, Mockito.times(1)).simulateMorning();
//	}
//}
