package com.id.hl7sim.patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import com.id.hl7sim.xml.Departments;
import com.id.hl7sim.xml.Firstnames;
import com.id.hl7sim.xml.Lastnames;
import com.id.hl7sim.xml.Wards;

public class PatientGeneratorImpl implements PatientGenerator {

	private static Firstnames firstnames;
	private static Lastnames lastnames;
	private static Departments departments;
	private static Wards wards;
	private static List<Patient> allPatients;
	private static AtomicInteger idTemplate = new AtomicInteger(0);
	private static AtomicInteger caseNumber = new AtomicInteger(1);
	
	
	public PatientGeneratorImpl(Firstnames firstnames, Lastnames lastnames, Departments departments, Wards wards) {

		PatientGeneratorImpl.firstnames = firstnames;
		PatientGeneratorImpl.lastnames = lastnames;
		PatientGeneratorImpl.departments = departments;
		PatientGeneratorImpl.wards = wards;
		PatientGeneratorImpl.allPatients = new ArrayList<Patient>();
	}

	public Firstnames getFirstnames() {
		return firstnames;
	}

	public void setFirstnames(Firstnames firstnames) {
		PatientGeneratorImpl.firstnames = firstnames;
	}

	public Lastnames getLastnames() { 
		return lastnames;
	}

	public void setLastnames(Lastnames lastnames) {
		PatientGeneratorImpl.lastnames = lastnames;
	}

	public Departments getDepartments() {
		return departments;
	}

	public void setDepartments(Departments departments) {
		PatientGeneratorImpl.departments = departments;
	}

	public Wards getWards() {
		return wards;
	}

	public void setWards(Wards wards) {
		PatientGeneratorImpl.wards = wards;
	}

	public List<Patient> getAllPatients() {
		return allPatients;
	}

	public void setAllPatients(List<Patient> allPatients) {
		PatientGeneratorImpl.allPatients = allPatients;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientGenerator#randomizeNewPatient()
	 */
	@Override
	public Patient randomizeNewPatient() {
		return new Patient.Builder()
				
				.id("HL7SIM_" + String.format("%09d", idTemplate.incrementAndGet()))
				.lastname(getRandomLastName())
				.firstname(getRandomFirstName())
				.gender(getRandomGender())
				.birthday(getRandomBirthday())
				.status(null)
				.ward(null)
				.department(null)
				.admissionDateTime(null)
				.dischargeDateTime(null)
				.lengthOfStay(0)
				.build();
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientGenerator#createRandomPatients(int)
	 */
	@Override
	public List<Patient> createRandomPatients(int amount) {
		for (int i = 0; i < amount; i++) {
			Patient newPatient = randomizeNewPatient();
			allPatients.add(newPatient);
		}
		return allPatients;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientGenerator#setRandomLastName()
	 */
	@Override
	public String getRandomLastName() {
		Collections.shuffle(lastnames.getLastnames());
		return lastnames.getLastnames().get(0).getName();
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientGenerator#setRandomFirstName()
	 */
	@Override
	public String getRandomFirstName() {
		Collections.shuffle(firstnames.getFirstnames());
		return firstnames.getFirstnames().get(0).getName();
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientGenerator#setRandomGender()
	 */
	@Override
	public String getRandomGender() {
		return firstnames.getFirstnames().get(0).getGender();
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientGenerator#setRandomBirthday()
	 */
	@Override
	public LocalDate getRandomBirthday() {
		long minDay = LocalDate.of(1900, 1, 1).toEpochDay();
		long maxDay = LocalDate.now().toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		return LocalDate.ofEpochDay(randomDay);
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientGenerator#setRandomDepartment()
	 */
	@Override
	public String getRandomDepartment() {
		Collections.shuffle(departments.getDepartments());
		return departments.getDepartments().get(0).getDepartment();
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientGenerator#setRandomWard(com.id.hl7sim.xml.Departments)
	 */
	@Override
	public String getRandomWard() {
		
		return departments.getDepartments().get(0).getWard();
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.PatientGenerator#setUniqueInstance(com.id.hl7sim.xml.Departments)
	 */
	@Override
	public String setUniqueInstance() {	
		return String.valueOf(caseNumber.getAndIncrement());
	}
	
}