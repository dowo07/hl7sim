package com.id.hl7sim;


import com.id.hl7sim.threads.AdmissionThread;
import com.id.hl7sim.threads.DischargeThread;
import com.id.hl7sim.threads.TransferThread;



public class HospitalTimeSimulatorImpl implements HospitalTimeSimulator {
	
	
	public Hospital hospital;
	public Runnable admissionThread; 
	public Runnable dischargeThread;
	public Runnable transferThread;
	public int accelerationFactor;
	 
	
	public int getAccelerationFactor() { 
		return accelerationFactor;
	}

	public void setAccelerationFactor(int accelerationFactor) {
		this.accelerationFactor = accelerationFactor;
	}

	public HospitalTimeSimulatorImpl(Hospital hospital, int accelerationFactor) {
		this.hospital = hospital;
		this.accelerationFactor = accelerationFactor;
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.HospitalTimeSimulator#getHospital()
	 */
	@Override
	public Hospital getHospital() {
		return hospital;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.HospitalTimeSimulator#setHospital(com.id.hl7sim.Hospital)
	 */
	@Override
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	} 

	/* (non-Javadoc)
	 * @see com.id.hl7sim.HospitalTimeSimulator#getNumberOfpatientsForInitializing()
	 */
	@Override
	public int getNumberOfpatientsForInitializing() {
		return (int) (this.hospital.getCapacity() * 0.75);
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.HospitalTimeSimulator#initHospital()
	 */
	@Override
	public void initHospital() {
		int patientsToAdmit = this.getNumberOfpatientsForInitializing();
		for(int i = 0; i< patientsToAdmit; i++) {
			this.hospital.admitPatient();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.HospitalTimeSimulator#simulateDay()
	 */
	@Override
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