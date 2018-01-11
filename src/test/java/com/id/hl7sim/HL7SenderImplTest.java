package com.id.hl7sim;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.id.hl7sim.hl7.HL7Endpoint;
import com.id.hl7sim.hl7.HL7Sender;
import com.id.hl7sim.hl7.HL7SenderImpl;

public class HL7SenderImplTest {
	
	HL7Sender testHL7Sender;
	ByteArrayOutputStream output;
	List<String> testAllHL7s;
	String testMessageOne;
	String testMessageTwo;
	HL7Endpoint endpoint;
	ByteArrayOutputStream baos;
	
	
	@Before
	public void setUp() throws Exception {
		endpoint = Mockito.mock(HL7Endpoint.class);
		baos = new ByteArrayOutputStream();
		Mockito.when(endpoint.getOutputStream()).thenReturn(baos);
		testHL7Sender = new HL7SenderImpl(endpoint);
		testAllHL7s = new ArrayList<String>();
		testMessageOne = "HL7 rockz!";
		testMessageTwo = "HL7 still rockz!";
		testAllHL7s.add(testMessageOne);
		testAllHL7s.add(testMessageTwo); 
	}

	@Test(expected=IllegalArgumentException.class) 
	public void testSendMessageNoValuesInArray() {
		testAllHL7s = null;
		testHL7Sender.writeLogFile(testAllHL7s); 
	}
	
	@Test
	public void testSendMessage() {
		// Given
		String message = "testAllHL7s";
		 
		// When
		testHL7Sender.sendMessage(message);
		
		// Then
        assertEquals(message,new String(baos.toByteArray(), StandardCharsets.UTF_8));
	}
	
	@Test
	public void testSendListOfMessages() {
	
		// When
		testHL7Sender.sendListOfMessages(testAllHL7s);
		
		// Then
        assertEquals(testAllHL7s.get(0) + testAllHL7s.get(1),new String(baos.toByteArray(), StandardCharsets.UTF_8));
	} 
	
}
