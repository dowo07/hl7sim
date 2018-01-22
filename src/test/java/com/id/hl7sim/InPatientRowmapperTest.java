package com.id.hl7sim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.id.hl7sim.patient.InPatientRowMapper;


public class InPatientRowmapperTest {
	
	
	InPatientRowMapper testMapper; 
	LocalDateTime resultDate;
	
	@Before
	public void setUp() throws Exception {
	
		testMapper = new InPatientRowMapper();
		
	}

	
	@Test
	public void parseLocalDateTimeTestLongValue() {
		
		String dateTimeToTest = "2018-01-22T10:07:23.345";
		
		resultDate = testMapper.parseLocalDateTime(dateTimeToTest);
		
		assertTrue(resultDate.getYear() == 2018);
		assertTrue(resultDate.getMonthValue() == 01);
		assertTrue(resultDate.getDayOfMonth() == 22);
		assertTrue(resultDate.getHour() == 10);
		assertTrue(resultDate.getMinute() == 07);
		assertTrue(resultDate.getSecond() == 23);
	
	}
	
	@Test
	public void parseLocalDateTimeTestShortValue() {
		
		String dateTimeToTest = "2018-01-22T10:07:23";
		
		resultDate = testMapper.parseLocalDateTime(dateTimeToTest);
		
		assertTrue(resultDate.getYear() == 2018);
		assertTrue(resultDate.getMonthValue() == 01);
		assertTrue(resultDate.getDayOfMonth() == 22);
		assertTrue(resultDate.getHour() == 10);
		assertTrue(resultDate.getMinute() == 07);
		assertTrue(resultDate.getSecond() == 23);
	
	}

}
