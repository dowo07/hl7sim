package com.id.hl7sim.patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

public class InPatientRowMapper implements RowMapper<Patient> {
	
	public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new Patient.Builder()
				.instance(rs.getString("instance"))
				.id(rs.getString("id"))
				.ward(rs.getString("ward"))
				.department(rs.getString("department"))
				.admissionDateTime(parseLocalDateTime(rs.getString("admissionDate")))
				.status(rs.getString("patientStatus"))
				.build();	
	}
	 
	public LocalDateTime parseLocalDateTime(String localdatetime) {
		localdatetime = localdatetime.replace("T", "");
		if(localdatetime.length() <= 18) {
			localdatetime += ".000";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss.SSS");
		LocalDateTime formattedLocalDateTime = LocalDateTime.parse(localdatetime, formatter);
		return formattedLocalDateTime;
	}
	

	
}		 