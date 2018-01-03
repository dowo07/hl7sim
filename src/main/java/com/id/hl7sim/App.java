package com.id.hl7sim;

import java.util.List;

import javax.xml.bind.JAXB;

import com.id.hl7sim.Hospital;
import com.id.hl7sim.xml.Departments;
import com.id.hl7sim.xml.Firstnames;
import com.id.hl7sim.xml.Lastnames;
import com.id.hl7sim.xml.Wards;

public class App { 
	
	
	public static void main(String[] args) { 
		
		Departments departments = JAXB.unmarshal(ClassLoader.getSystemResource("departments.xml"), Departments.class);
		Wards wards = JAXB.unmarshal(ClassLoader.getSystemResource("wards.xml"), Wards.class);
		Lastnames lastnames = JAXB.unmarshal(ClassLoader.getSystemResource("lastnames.xml"), Lastnames.class);
		Firstnames firstnames = JAXB.unmarshal(ClassLoader.getSystemResource("firstnames.xml"), Firstnames.class);
		
		PatientGenerator myGenerator = new PatientGeneratorImpl(firstnames, lastnames, departments, wards);
		
		List<Patient> allPatients = myGenerator.createRandomPatients(100);
		
		PatientRepository myPatientRepository = new PatientRepositoryMySqlImpl(myGenerator);    
        
		myPatientRepository.insertListOfPatients(allPatients);
		
		HL7Builder myHl7Builder = new HL7BuilderImpl();
		
		HL7Endpoint hl7endpoint = new HL7SocketEndpoint("localhost", 6661);
		
		HL7Sender myHL7Sender = new HL7SenderImpl(hl7endpoint);
		
		Hospital myHospital = new HospitalImpl(50, myHl7Builder, myPatientRepository);
		
		myHospital.autoFillHospital();
		
		for(int i = 0; i < 3; i++) {
			
			myHospital.dischargePatient();
			myHospital.admitPatient();
			myHospital.transferPatient();
		}
		
		List<String> allHL7s = myHl7Builder.getAllHL7s();
		
		myHL7Sender.sendListOfMessages(allHL7s);
	}
	
	
}