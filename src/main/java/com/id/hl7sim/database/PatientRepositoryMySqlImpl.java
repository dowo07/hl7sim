package com.id.hl7sim.database;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.id.hl7sim.patient.InPatientRowMapper;
import com.id.hl7sim.patient.Patient;
import com.id.hl7sim.patient.PatientGenerator;
import com.id.hl7sim.patient.PatientRowMapper;


public class PatientRepositoryMySqlImpl extends AbstractPatientRepository implements PatientRepository  {

	
	private JdbcTemplate template;
	public PatientGenerator patientGenerator;
 
	
	public PatientRepositoryMySqlImpl(DataSource dataSource, PatientGenerator patientGenerator) {
		super(dataSource, patientGenerator);	
		this.patientGenerator = patientGenerator;
		this.template = new JdbcTemplate(dataSource);
	} 

	
	public Patient getRandomPatient() {
		String sql = "SELECT * FROM tbl_patient ORDER BY RAND() LIMIT 1";
		Patient patient = (Patient) template.queryForObject(sql, new PatientRowMapper());
		return patient;
	}

	public Patient getRandomInpatient() {
		String sql = "SELECT * FROM tbl_inpatients ORDER BY RAND() LIMIT 1";
		Patient patient = (Patient) template.queryForObject(sql, new Object[] {}, new InPatientRowMapper());
		setPatientBasicData(patient);
		return patient;
	} 

	public int countPatients() {
		String sql = "SELECT COUNT(*) FROM tbl_patient";
		return template.queryForObject(sql, Integer.class);
	}

	public int countInpatients() {
		String sql = "SELECT COUNT(*) FROM tbl_inpatients";
		return template.queryForObject(sql, Integer.class);
	}

	
}	