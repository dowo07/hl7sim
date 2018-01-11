package com.id.hl7sim.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.id.hl7sim.patient.Patient;
import com.id.hl7sim.patient.PatientGenerator;
import com.id.hl7sim.patient.PatientRowMapper;


public abstract class AbstractPatientRepository implements PatientRepository {
	
	
	private JdbcTemplate template;
	private PatientGenerator patientGenerator;
	
	
	public AbstractPatientRepository(DataSource dataSource, PatientGenerator patientGenerator) {
		this.patientGenerator = patientGenerator;
		this.template = new JdbcTemplate(dataSource);
	} 
	
	public void insertPatient(Patient patient) {
		template.update("INSERT INTO tbl_patient(lastname, firstname, gender, birthday) VALUES(?,?,?,?)",
				patient.getLastname(), patient.getFirstname(), patient.getGender(), patient.getBirthday().toString());
	}
	
	public void insertListOfPatients(List<Patient> allPatients) {
		for (Patient patient : allPatients) {
			insertPatient(patient);
		}
	}
	
	public Patient admitRandomPatient() {
		Patient patient = getRandomPatient();
		patient.setDepartment(patientGenerator.getRandomDepartment());
		patient.setWard(patientGenerator.getRandomWard());
		patient.setAdmissionDateTime(LocalDateTime.now());
		patient.setStatus("I");
		template.update(
				"INSERT INTO tbl_inpatients(id, ward, department, admissionDate, patientStatus) VALUES(?,?,?,?,?)",
				patient.getId(), patient.getWard(), patient.getDepartment(), patient.getAdmissionDateTime().toString(),
				patient.getStatus());
		return patient;
	}
	
	public Patient setPatientBasicData(Patient patient) {
		String sql = "SELECT * FROM tbl_patient WHERE id = '" + patient.getId() + "'";
		Patient patientNew = (Patient) template.queryForObject(sql, new Object[] {}, new PatientRowMapper());
		patient.setLastname(patientNew.getLastname());
		patient.setFirstname(patientNew.getFirstname());
		patient.setGender(patientNew.getGender());
		patient.setBirthday(patientNew.getBirthday());
		return patient;
	}
	
	public Patient transferRandomPatient() {
		Patient patient = getRandomInpatient();
		patient.setPriorWard(patient.getWard());
		patient.setPriorDepartment(patient.getPriorDepartment());
		patient.setDepartment(patientGenerator.getRandomDepartment());
		patient.setWard(patientGenerator.getRandomWard());
		template.update("UPDATE tbl_inpatients SET ward = ?, department = ? WHERE id = ?", patient.getWard(),
				patient.getDepartment(), patient.getId());
		return patient;
	}
	
	public Patient dischargeRandomPatient() {
		Patient patient = getRandomInpatient();
		patient.setDischargeDateTime(LocalDateTime.now());
		insertFormerPatient(patient);
		template.update("DELETE FROM tbl_inpatients WHERE instance= ?", patient.getInstance());
		return patient;
	}
	
	public void insertFormerPatient(Patient patient) {
		template.update(
				"INSERT INTO tbl_formerpatients(instance, id, ward, department, admissionDate, dischargeDate) VALUES('"
						+ patient.getInstance() + "', '" + patient.getId() + "', '" + patient.getWard() + "', '"
						+ patient.getDepartment() + "', '" + patient.getAdmissionDateTime().toString() + "', '"
						+ patient.getDischargeDateTime().toString() + "')");
	}
	
	public LocalDate parseBirthday(String birthday) {
		LocalDate localDate = LocalDate.parse(birthday);
		return localDate;
	}
	
	
}