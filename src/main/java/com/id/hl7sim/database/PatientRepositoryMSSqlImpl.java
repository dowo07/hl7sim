package com.id.hl7sim.database;

import java.time.LocalDate;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.id.hl7sim.patient.InPatientRowMapper;
import com.id.hl7sim.patient.Patient;
import com.id.hl7sim.patient.PatientGenerator;
import com.id.hl7sim.patient.PatientRowMapper;


public class PatientRepositoryMSSqlImpl extends AbstractPatientRepository implements PatientRepository {

	
	private JdbcTemplate template;
	public PatientGenerator patientGenerator;

	
	public PatientRepositoryMSSqlImpl(DataSource dataSource, PatientGenerator patientGenerator) {
		super(dataSource, patientGenerator);
		this.patientGenerator = patientGenerator;
		this.template = new JdbcTemplate(dataSource);
	} 
	
	public Patient getRandomPatient() {
		Patient patient = null;
		do {
		String sql = "SELECT TOP 1 * FROM tbl_patient ORDER BY NEWID()";
		patient = (Patient) template.queryForObject(sql, new PatientRowMapper());
		}
		while(isPatientInHospital(patient));
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

	public boolean isPatientInHospital(Patient patient) {
		String sql = "SELECT COUNT(id) AS numberOfHits FROM tbl_inpatients WHERE id = '" + patient.getId() + "'";
		int result =  template.queryForObject(sql, Integer.class);
		return result > 0 ? true : false;
	}
	
	public LocalDate parseBirthday(String birthday) {
		LocalDate localDate = LocalDate.parse(birthday);
		return localDate;
	}

	
}