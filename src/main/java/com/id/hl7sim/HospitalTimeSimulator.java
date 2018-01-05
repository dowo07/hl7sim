package com.id.hl7sim;


public class HospitalTimeSimulator {
	
	
	public Hospital hospital;
	public Runnable hospitalStartThread;
	public Runnable admissionThread;
	public Runnable transferThread;
	public Runnable dischargeThread;
	
	
	public HospitalTimeSimulator(Hospital hospital, Runnable hospitalStartThread, Runnable admissionThread, Runnable dischargeThread, Runnable transferThread) {
		this.hospital = hospital;
		this.hospitalStartThread = hospitalStartThread;
		this.admissionThread = admissionThread;
		this.transferThread = transferThread;
		this.dischargeThread = dischargeThread;
	}
	
	
	
	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Runnable getHospitalStartThread() {
		return hospitalStartThread;
	}

	public void setHospitalStartThread(Runnable hospitalStartThread) {
		this.hospitalStartThread = hospitalStartThread;
	}

	public Runnable getAdmissionThread() {
		return admissionThread;
	}

	public void setAdmissionThread(Runnable admissionThread) {
		this.admissionThread = admissionThread;
	}

	public Runnable getTransferThread() {
		return transferThread;
	}

	public void setTransferThread(Runnable transferThread) {
		this.transferThread = transferThread;
	}

	public Runnable getDischargeThread() {
		return dischargeThread;
	}

	public void setDischargeThread(Runnable dischargeThread) {
		this.dischargeThread = dischargeThread;
	}

	public int getNumberOfpatientsForInitializing() {
		return (int) (this.hospital.getCapacity() * 0.75);
	}
	
	public void initHospital() {
		
	}
	
	public static void simulateDay() {
		
	}

	
}