package com.id.hl7sim.hospital;

public interface HospitalTimeSimulator {

	int getAccelerationFactor();
	
	void setAccelerationFactor(int accelerationFactor);
	
	Hospital getHospital();

	void setHospital(Hospital hospital);

	int getNumberOfpatientsForInitializing();

	void initHospital();

	void simulateDay();

}