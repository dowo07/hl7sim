package com.id.hl7sim;


import com.id.hl7sim.threads.AdmissionThread;
import com.id.hl7sim.threads.DischargeThread;
import com.id.hl7sim.threads.TransferThread;


public class HospitalTimeSimulator {
	
	
	public Hospital hospital;
	public Runnable hospitalStartThread;
	public AdmissionThread admissionThread;
	public DischargeThread dischargeThread;
	public TransferThread transferThread;
	
	
	public HospitalTimeSimulator(Hospital hospital, AdmissionThread admissionThread, DischargeThread dischargeThread, TransferThread transferThread) {
		this.hospital = hospital;
		this.admissionThread = admissionThread;
		this.dischargeThread = dischargeThread;
		this.transferThread = transferThread;
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
		this.initHospital();
		this.admissionThread.start();
		this.dischargeThread.start();
		this.transferThread.start();
	}

	
}