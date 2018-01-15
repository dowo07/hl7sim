package com.id.hl7sim.application;

import java.util.List;
import javax.xml.bind.JAXB;
import com.id.hl7sim.database.DatabaseManager;
import com.id.hl7sim.database.PatientRepository;
import com.id.hl7sim.database.PatientRepositoryMSSqlImpl;
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
	
	private static int accelerationFactor;
	private static int numberOfBeds;
	private static String ip;
	private static int port;
	

	public static int getAccelerationFactor() {
		return accelerationFactor;
	}
	
	public static void setAccelerationFactor(int accelerationFactor) {
		App.accelerationFactor = accelerationFactor;
	}

	public static int getNumberOfBeds() {
		return numberOfBeds;
	}

	public static void setNumberOfBeds(int numberOfBeds) {
		App.numberOfBeds = numberOfBeds;
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		App.ip = ip;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		App.port = port;
	}

	public static void main(String[] args) {

		Departments departments = JAXB.unmarshal(ClassLoader.getSystemResource("departments.xml"), Departments.class);
		Wards wards = JAXB.unmarshal(ClassLoader.getSystemResource("wards.xml"), Wards.class);
		Lastnames lastnames = JAXB.unmarshal(ClassLoader.getSystemResource("lastnames.xml"), Lastnames.class);
		Firstnames firstnames = JAXB.unmarshal(ClassLoader.getSystemResource("firstnames.xml"), Firstnames.class);

		PatientGenerator myGenerator = new PatientGeneratorImpl(firstnames, lastnames, departments, wards);

		List<Patient> allPatients = myGenerator.createRandomPatients(100); 
	
		ComboPooledDataSource cpds = DatabaseManager.provideDataSource("MSSql"); 

		PatientRepository myPatientRepository = new PatientRepositoryMSSqlImpl(cpds, myGenerator);
		 
		myPatientRepository.insertListOfPatients(allPatients); 

		HL7Builder myHl7Builder = new HL7BuilderImpl(); 

		HL7Endpoint hl7endpoint = new HL7SocketEndpoint(App.getIp(), App.getPort());

		HL7Sender myHL7Sender = new HL7SenderImpl(hl7endpoint);

		Hospital myHospital = new HospitalImpl(App.getNumberOfBeds(), myHl7Builder, myHL7Sender, myPatientRepository);

		HospitalTimeSimulator myHospitalTimeSimulator = new HospitalTimeSimulatorImpl(myHospital, App.getAccelerationFactor());

		myHospitalTimeSimulator.simulateDay(); 
	} 

	
}