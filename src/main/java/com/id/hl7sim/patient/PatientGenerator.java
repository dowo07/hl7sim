package com.id.hl7sim.patient;


import java.time.LocalDate;
import java.util.List;


public interface PatientGenerator {

	Patient randomizeNewPatient();

	List<Patient> createRandomPatients(int amount);

	String getRandomLastName();

	String getRandomFirstName();

	String getRandomGender();

	LocalDate getRandomBirthday();

	String getRandomDepartment();

	String getRandomWard();
	
	String setUniqueInstance();

}