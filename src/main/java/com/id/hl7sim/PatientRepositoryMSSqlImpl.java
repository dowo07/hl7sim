package com.id.hl7sim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PatientRepositoryMSSqlImpl implements PatientRepository {
	
	
	private DatabaseConnection connection;
	private PatientGenerator patientGenerator;

	
	public PatientRepositoryMSSqlImpl(DatabaseConnection connection, PatientGenerator patientGenerator) {
		this.connection = connection;
		this.patientGenerator = patientGenerator;
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientRepository#insertPatient()
	 */
	@Override
	public void insertPatient(Patient patient) {
		if (!patient.isValid()) {
			throw new IllegalArgumentException("Incomplete Patient");
		} else {
			String insert = "INSERT INTO tbl_patient(lastname, firstname, gender, birthday) VALUES('"
			+ patient.getLastname() + "', '" + patient.getFirstname() + "', '" + patient.getGender() + "', '"
			+ patient.getBirthday().toString() + "')";
			try (Connection dbConnection = connection.getDBConnection();
				 Statement statement = dbConnection.createStatement()) {
				statement.executeUpdate(insert);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientRepository#insertListOfPatients()
	 */
	@Override
	public void insertListOfPatients(List<Patient> allPatients) {
		for (Patient patient : allPatients) {
			insertPatient(patient); 
		}
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientRepository#getRandomPatient()
	 */
	@Override
	public Patient getRandomPatient() {
		Patient patient = new Patient.Builder().build();
		String query = "SELECT TOP 1 * FROM tbl_patient ORDER BY NEWID()";
		try (Connection dbConnection = connection.getDBConnection();
			 Statement statement = dbConnection.createStatement();) {
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			setPatientBasicData(patient, rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return patient;
	}
	
	private void setPatientBasicData(Patient patient, ResultSet rs) {
		try {
			patient.setId(rs.getInt("id"));
			patient.setLastname(rs.getString("lastname"));
			patient.setFirstname(rs.getString("firstname"));
			patient.setGender(rs.getString("gender"));
			patient.setBirthday(parseBirthday(rs.getString("birthday")));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public LocalDate parseBirthday(String birthday) {
		LocalDate localDate = LocalDate.parse(birthday);
		return localDate;
	}
	
	

	public Patient getRandomInpatient() {
		Patient patient = new Patient.Builder().build();
		String query = "SELECT TOP 1 * FROM tbl_inpatients ip, tbl_patient p WHERE p.id = ip.id ORDER BY NEWID()";
		try (Connection dbConnection = connection.getDBConnection();
			 Statement statement = dbConnection.createStatement();) {
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			setPatientBasicData(patient, rs);
			setPatientCaseData(patient, rs);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return patient;
	}
	
	public void setPatientCaseData(Patient patient, ResultSet rs) {
		try {
			patient.setWard(rs.getString("ward"));
			patient.setDepartment(rs.getString("department"));
			patient.setAdmissionDateTime(parseLocalDateTime(rs.getString("admissionDate")));
			patient.setStatus(rs.getString("patientStatus"));
			patient.setCaseId(rs.getInt("instance"));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public LocalDateTime parseLocalDateTime(String localdatetime) {
		localdatetime = localdatetime.replace("T", "");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss.SSS");
		LocalDateTime formattedLocalDateTime = LocalDateTime.parse(localdatetime, formatter);
		return formattedLocalDateTime;
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientRepository#transferRandomPatient()
	 */
	@Override
	public Patient transferRandomPatient() {
		Patient patient = getRandomInpatient();
		patient.setPriorWard(patient.getWard());
		patient.setPriorDepartment(patient.getPriorDepartment());
		patient.setDepartment(patientGenerator.getRandomDepartment());
		patient.setWard(patientGenerator.getRandomWard());
		String update = "UPDATE tbl_inpatients SET ward='" + patient.getWard() + "', department='"
				+ patient.getDepartment() + "' WHERE id='" + patient.getId() + "'";
		try (Connection dbConnection = connection.getDBConnection();
			 Statement statement = dbConnection.createStatement()) {
			statement.executeUpdate(update);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return patient;
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientRepository#admitRandomPatient()
	 */
	@Override
	public Patient admitRandomPatient() {
		Patient patient = getRandomPatient();
		patient.setDepartment(patientGenerator.getRandomDepartment());
		patient.setWard(patientGenerator.getRandomWard());
		patient.setAdmissionDateTime(LocalDateTime.now());
		patient.setStatus("I");
		String insert = "INSERT INTO tbl_inpatients(id, ward, department, admissionDate, patientStatus) VALUES('"
						+ patient.getId() + "', '" + patient.getWard() + "', '" + patient.getDepartment() + "', '"
						+ patient.getAdmissionDateTime().toString() + "', '" + patient.getStatus() + "')";
		try (Connection dbConnection = connection.getDBConnection();
			PreparedStatement statement = dbConnection.prepareStatement(insert)) {
			statement.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			patient.setCaseId(keys.getInt(1));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return patient;
	} 
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientRepository#dischargeRandomPatient()
	 */
	@Override
	public Patient dischargeRandomPatient() {
		Patient patient = getRandomInpatient();
		patient.setDischargeDateTime(LocalDateTime.now());
		insertFormerPatient(patient);
		String delete = "DELETE FROM tbl_inpatients WHERE instance= '" + patient.getCaseId() + "'";
		try (Connection dbConnection = connection.getDBConnection();
		     Statement statement = dbConnection.createStatement()) {
			statement.executeUpdate(delete);;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return patient;
	}
	
	public void insertFormerPatient(Patient patient) {
		String insert = "INSERT INTO tbl_formerpatients(instance, id, ward, department, admissionDate, dischargeDate) VALUES('"
				+ patient.getCaseId() + "', '" + patient.getId() + "', '" + patient.getWard() + "', '"
				+ patient.getDepartment() + "', '" + patient.getAdmissionDateTime().toString() + "', '"
				+ patient.getDischargeDateTime().toString() + "')";
		try (Connection dbConnection = connection.getDBConnection();
				 Statement statement = dbConnection.createStatement()) {
				statement.executeUpdate(insert);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}

	public int countInpatients() {
		int numberOfPatients = 0;
		String query = "SELECT COUNT(id) AS numberOfPatients FROM tbl_inpatients";
		try (Connection dbConnection = connection.getDBConnection();
				Statement statement = dbConnection.createStatement();) {
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				numberOfPatients = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return numberOfPatients;
	}  
	
	public int countPatients() {
		int numberOfPatients = 0;
		String query = "SELECT COUNT(id) AS numberOfPatients FROM tbl_patient";
		try (Connection dbConnection = connection.getDBConnection();
				Statement statement = dbConnection.createStatement();) {
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				numberOfPatients = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return numberOfPatients;
	}

}