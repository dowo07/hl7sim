package com.id.hl7sim;

import java.util.List;
import javax.sql.DataSource;
import javax.xml.bind.JAXB;
import com.id.hl7sim.Hospital;
import com.id.hl7sim.threads.AdmissionThread;
import com.id.hl7sim.threads.DischargeThread;
import com.id.hl7sim.threads.TransferThread;
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
		
		DatabaseConnection myConnection = new MSSqlConnection();
		
		DataSource myDataSource = myConnection.getDataSource(); 
		
		PatientRepository myPatientRepository = null;
		
		   switch (myConnection.getClass().toString()) {
           case "class com.id.hl7sim.MSSqlConnection":  
           	myPatientRepository = new PatientRepositoryMSSqlImpl(myDataSource, myGenerator);
           	break;
           case "class com.id.hl7sim.MySqlConnection":	
           	myPatientRepository = new PatientRepositoryMySqlImpl(myDataSource, myGenerator);
           	break;
           default:
           	throw new IllegalArgumentException("Database Type not possible");
           	
       }     
               
		myPatientRepository.insertListOfPatients(allPatients);
		
		HL7Builder myHl7Builder = new HL7BuilderImpl();
		
		HL7Endpoint hl7endpoint = new HL7SocketEndpoint("localhost", 6661);
		
		HL7Sender myHL7Sender = new HL7SenderImpl(hl7endpoint);
		
		Hospital myHospital = new HospitalImpl(20, myHl7Builder, myHL7Sender, myPatientRepository);
		
		HospitalTimeSimulator myHospitalTimeSimulator = new HospitalTimeSimulator(myHospital);
		
		myHospitalTimeSimulator.simulateDay(1);
		} 
	 
	  
}