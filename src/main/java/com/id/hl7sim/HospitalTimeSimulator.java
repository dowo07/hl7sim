package com.id.hl7sim;


import com.id.hl7sim.threads.AdmissionThread;
import com.id.hl7sim.threads.DischargeThread;
import com.id.hl7sim.threads.TransferThread;



public class HospitalTimeSimulator {
	
	
	public Hospital hospital;
	public Runnable admissionThread; 
	public Runnable dischargeThread;
	public Runnable transferThread;
	public static int accelerationFactor;
	
	
	public static int getAccelerationFactor() {
		return accelerationFactor;
	}

	public static void setAccelerationFactor(int accelerationFactor) {
		HospitalTimeSimulator.accelerationFactor = accelerationFactor;
	}

	public HospitalTimeSimulator(Hospital hospital, int accelerationFactor) {
		this.hospital = hospital;
		HospitalTimeSimulator.accelerationFactor = accelerationFactor;
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
	
	public void simulateDay() {
		if(this.getHospital().getOccupiedBeds() == 0) {
			this.initHospital();
		}
		
		Thread admissionThread = new Thread(new AdmissionThread(hospital, getAccelerationFactor()));
		Thread dischargeThread = new Thread(new DischargeThread(hospital, getAccelerationFactor()));
		Thread transferThread = new Thread(new TransferThread(hospital, getAccelerationFactor()));
		
		admissionThread.start();
		dischargeThread.start();
		transferThread.start();
	}

	
}