package com.id.hl7sim.application;

import java.util.List;
import javax.xml.bind.JAXB;
import com.id.hl7sim.database.DatabaseManager;
import com.id.hl7sim.database.PatientRepository;
import com.id.hl7sim.database.PatientRepositoryMSSqlImpl;
import com.id.hl7sim.database.PatientRepositoryMySqlImpl;
import com.id.hl7sim.hl7.HL7Builder;
import com.id.hl7sim.hl7.HL7BuilderImpl;
import com.id.hl7sim.hl7.HL7Endpoint;
import com.id.hl7sim.hl7.HL7Sender;
import com.id.hl7sim.hl7.HL7SenderImpl;
import com.id.hl7sim.hl7.HL7SocketEndpoint;
import com.id.hl7sim.hospital.Hospital;
import com.id.hl7sim.hospital.HospitalImpl;
import com.id.hl7sim.hospital.HospitalTimeSimulator;
import com.id.hl7sim.hospital.HospitalTimeSimulatorImpl;
import com.id.hl7sim.patient.Patient;
import com.id.hl7sim.patient.PatientGenerator;
import com.id.hl7sim.patient.PatientGeneratorImpl;
import com.id.hl7sim.xml.Departments;
import com.id.hl7sim.xml.Firstnames;
import com.id.hl7sim.xml.Lastnames;
import com.id.hl7sim.xml.Wards;
import com.mchange.v2.c3p0.ComboPooledDataSource;


public class App { 
	

	public static void main(String[] args) {
		
		
		/**
		 * TODO:
		 * 
		 * isPatientInHospital
		 * borders for capacity working?
		 * hl7 prefix
		 * high speed socket exception
		 * dbTests
		 * 
		 */
		Departments departments = JAXB.unmarshal(ClassLoader.getSystemResource("departments.xml"), Departments.class);
		Wards wards = JAXB.unmarshal(ClassLoader.getSystemResource("wards.xml"), Wards.class);
		Lastnames lastnames = JAXB.unmarshal(ClassLoader.getSystemResource("lastnames.xml"), Lastnames.class);
		Firstnames firstnames = JAXB.unmarshal(ClassLoader.getSystemResource("firstnames.xml"), Firstnames.class);

		PatientGenerator myGenerator = new PatientGeneratorImpl(firstnames, lastnames, departments, wards);

		List<Patient> allPatients = myGenerator.createRandomPatients(500); 
	
		ComboPooledDataSource cpds = DatabaseManager.provideDataSource("MSSql"); 

		PatientRepository myPatientRepository = new PatientRepositoryMSSqlImpl(cpds, myGenerator);
		 
		myPatientRepository.insertListOfPatients(allPatients); 

		HL7Builder myHl7Builder = new HL7BuilderImpl(); 

		HL7Endpoint hl7endpoint = new HL7SocketEndpoint("localhost", 6661);

		HL7Sender myHL7Sender = new HL7SenderImpl(hl7endpoint);

		Hospital myHospital = new HospitalImpl(100, myHl7Builder, myHL7Sender, myPatientRepository);
		
		HospitalTimeSimulator myHospitalTimeSimulator = new HospitalTimeSimulatorImpl(myHospital, 1000);
		
		myHospitalTimeSimulator.simulateDay();
	} 
 
	
}