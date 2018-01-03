package com.id.hl7sim;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;


public class PatientRowMapper implements RowMapper<Patient> {
	
	
	public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Patient.Builder()
				.id(rs.getInt("id"))
				.lastname(rs.getString("lastname"))
				.firstname(rs.getString("firstname"))
				.gender(rs.getString("gender"))
				.birthday(parseBirthday(rs.getString("birthday")))
				.build();	
	}
	
	public LocalDate parseBirthday(String birthday) {
		LocalDate localDate = LocalDate.parse(birthday);
		return localDate;
	}
	
	 
	
	
	
	
	 
	
	
	
}	

	
	