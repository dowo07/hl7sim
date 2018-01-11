package com.id.hl7sim.threads;


import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.id.hl7sim.hospital.Hospital;
 

public class DischargeThread implements Runnable, ProcessThread {

	
	public Hospital hospital;
	public Logger logger = LoggerFactory.getLogger(DischargeThread.class);
	public int accelerationFactor; 

	
	public DischargeThread(Hospital hospital, int accelerationFactor) {
		this.hospital = hospital;
		this.accelerationFactor = accelerationFactor;
	}

	
	public int getAccelerationFactor() {
		return accelerationFactor;
	}

	public void setAccelerationFactor(int accelerationFactor) {
		this.accelerationFactor = accelerationFactor;
	}


	public void run() {
		do {
			synchronized (this) {
				try {
					simulateWholeDay();
				} catch (InterruptedException e) {}
			}
		} while (true);
	}
	
	public void simulateWholeDay() throws InterruptedException {
		simulateNight();
		simulateMorning();
		simulateAfternoon();
		simulateEvening();
	}


	public void simulateNight() throws InterruptedException {
		TimeUnit.SECONDS.sleep(9000 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(11400 / accelerationFactor);
		this.hospital.dischargePatient();
	}
	
	
	public void simulateMorning() throws InterruptedException {
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(60 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(1740 / accelerationFactor);
		this.hospital.dischargePatient();
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(1200 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(4200 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(7200 / accelerationFactor);
		this.hospital.dischargePatient();
	}
	
	public void simulateAfternoon() throws InterruptedException {
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(60 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(1740 / accelerationFactor);
		this.hospital.dischargePatient();
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(1200 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(4200 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(7200 / accelerationFactor);
		this.hospital.dischargePatient();
	}
	
	public void simulateEvening() throws InterruptedException {
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(11400 / accelerationFactor);
		this.hospital.dischargePatient();
		TimeUnit.SECONDS.sleep(9000 / accelerationFactor);
	}

	
}