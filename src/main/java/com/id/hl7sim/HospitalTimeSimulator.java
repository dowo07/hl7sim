package com.id.hl7sim;


import com.id.hl7sim.threads.AdmissionThread;
import com.id.hl7sim.threads.DischargeThread;
import com.id.hl7sim.threads.TransferThread;



public class HospitalTimeSimulator {
	
	
	public Hospital hospital;
	public Thread admissionThread; 
	public Thread dischargeThread;
	public Thread transferThread;
	
	
	public HospitalTimeSimulator(Hospital hospital) {
		this.hospital = hospital;
	}
	
	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public int getNumberOfpatientsForInitializing() {
		return (int) (this.hospital.getCapacity() * 0.75);
	}
	
	public void initHospital() {
		int patientsToAdmit = this.getNumberOfpatientsForInitializing();
		for(int i = 0; i< patientsToAdmit; i++) {
			this.hospital.admitPatient();
		}
	}
	
	public void simulateDay(int accelerationFactor) {
		if(this.getHospital().getOccupiedBeds() == 0) {
			this.initHospital();
		}
		
		Thread admissionThread = new Thread(new AdmissionThread(hospital, accelerationFactor));
		Thread dischargeThread = new Thread(new DischargeThread(hospital, accelerationFactor));
		Thread transferThread = new Thread(new TransferThread(hospital, accelerationFactor));
		
		admissionThread.start();
		dischargeThread.start();
		transferThread.start();
	}

	
}