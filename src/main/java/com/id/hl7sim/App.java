package com.id.hl7sim;

import java.util.List;
import javax.sql.DataSource;
import javax.xml.bind.JAXB;
import com.id.hl7sim.Hospital;
import com.id.hl7sim.xml.Departments;
import com.id.hl7sim.xml.Firstnames;
import com.id.hl7sim.xml.Lastnames;
import com.id.hl7sim.xml.Wards;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.impl.C3P0PooledConnectionPool;

public class App {

	public static void main(String[] args) {

		Departments departments = JAXB.unmarshal(ClassLoader.getSystemResource("departments.xml"), Departments.class);
		Wards wards = JAXB.unmarshal(ClassLoader.getSystemResource("wards.xml"), Wards.class);
		Lastnames lastnames = JAXB.unmarshal(ClassLoader.getSystemResource("lastnames.xml"), Lastnames.class);
		Firstnames firstnames = JAXB.unmarshal(ClassLoader.getSystemResource("firstnames.xml"), Firstnames.class);

		PatientGenerator myGenerator = new PatientGeneratorImpl(firstnames, lastnames, departments, wards);

		List<Patient> allPatients = myGenerator.createRandomPatients(100);

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=HL7Sim";
		cpds.setJdbcUrl(jdbcUrl);
		cpds.setUser("scorer");
		cpds.setPassword("scorer");
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(100);

		PatientRepository myPatientRepository = null;

		if (jdbcUrl.contains("jdbc:sqlserver")) {
			myPatientRepository = new PatientRepositoryMSSqlImpl(cpds, myGenerator);
		} else {
			myPatientRepository = new PatientRepositoryMySqlImpl(cpds, myGenerator);
		}

		myPatientRepository.insertListOfPatients(allPatients);

		HL7Builder myHl7Builder = new HL7BuilderImpl();

		HL7Endpoint hl7endpoint = new HL7SocketEndpoint("localhost", 6661);

		HL7Sender myHL7Sender = new HL7SenderImpl(hl7endpoint);

		Hospital myHospital = new HospitalImpl(50, myHl7Builder, myHL7Sender, myPatientRepository);

		int accelerationFactor = 10000; // 1 = Realtime, 2 = double speed, ...

		HospitalTimeSimulator myHospitalTimeSimulator = new HospitalTimeSimulator(myHospital, accelerationFactor);

		myHospitalTimeSimulator.simulateDay();

	}

}