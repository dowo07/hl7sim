package com.id.hl7sim;


import java.time.LocalDate;
import java.util.List;

public interface PatientRepository {

	void insertPatient(Patient patient);
	
	void insertListOfPatients(List<Patient> allPatients);
	
	Patient getRandomPatient();
	
	Patient getRandomInpatient();
	
	Patient admitRandomPatient();

	Patient transferRandomPatient();

	Patient dischargeRandomPatient();
	
	int countInpatients();
	
	int countPatients();
	
	LocalDate parseBirthday(String birthday);

}