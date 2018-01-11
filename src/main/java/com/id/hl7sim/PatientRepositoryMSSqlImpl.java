package com.id.hl7sim;

import java.time.LocalDate;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;


public class PatientRepositoryMSSqlImpl extends AbstractPatientRepository implements PatientRepository {

	
	private JdbcTemplate template;
	public PatientGenerator patientGenerator;

	
	public PatientRepositoryMSSqlImpl(DataSource dataSource, PatientGenerator patientGenerator) {
		super(dataSource, patientGenerator);
		this.patientGenerator = patientGenerator;
		this.template = new JdbcTemplate(dataSource);
	} 


	public Patient getRandomPatient() {
		String sql = "SELECT TOP 1 * FROM tbl_patient ORDER BY NEWID()";
		Patient patient = (Patient) template.queryForObject(sql, new PatientRowMapper());
		return patient;
	}

	public Patient getRandomInpatient() {
		String sql = "SELECT TOP 1 * FROM tbl_inpatients ip, tbl_patient p WHERE p.id = ip.id ORDER BY NEWID()";
		Patient patient = (Patient) template.queryForObject(sql, new Object[] {}, new InPatientRowMapper());
		setPatientBasicData(patient);
		return patient;
	}

	public int countPatients() {
		String sql = "SELECT COUNT(id) AS numberOfPatients FROM tbl_patient";
		return template.queryForObject(sql, Integer.class);
	}

	public int countInpatients() {
		String sql = "SELECT COUNT(id) AS numberOfPatients FROM tbl_inpatients";
		return template.queryForObject(sql, Integer.class);
	}

	public LocalDate parseBirthday(String birthday) {
		LocalDate localDate = LocalDate.parse(birthday);
		return localDate;
	}

	
}