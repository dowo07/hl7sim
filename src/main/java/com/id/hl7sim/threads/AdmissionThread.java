package com.id.hl7sim.threads;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.id.hl7sim.hospital.Hospital;

public class AdmissionThread implements Runnable, ProcessThread {

	public Logger logger = LoggerFactory.getLogger(AdmissionThread.class);
	public Hospital hospital;
	public int accelerationFactor;

	public AdmissionThread(Hospital hospital, int accelerationFactor) {
		this.hospital = hospital;
		this.accelerationFactor = accelerationFactor;
	}

	public int getAccelerationFactor() {
		return accelerationFactor;
	}

	public void setAccelerationFactor(int accelerationFactor) {
		this.accelerationFactor = accelerationFactor;
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.id.hl7sim.threads.processThread#run()
	 */
	@Override
	public void run() {
		do {
			try {
				simulateWholeDay();
			} catch (InterruptedException e) {
				throw new RuntimeException("Error while simulating day");
			}
		} while (true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.id.hl7sim.threads.processThread#simulateWholeDay()
	 */
	@Override
	public void simulateWholeDay() throws InterruptedException {
		simulateNight();
		simulateMorning();
		simulateAfternoon();
		simulateEvening();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.id.hl7sim.threads.processThread#simulateNight()
	 */
	@Override
	public void simulateNight() throws InterruptedException {
		TimeUnit.SECONDS.sleep(7200 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(1800 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(600 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(10800 / accelerationFactor);
		this.hospital.admitPatient();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.id.hl7sim.threads.processThread#simulateMorning()
	 */
	@Override
	public void simulateMorning() throws InterruptedException {
		TimeUnit.SECONDS.sleep(60 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(1740 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(1200 / accelerationFactor);
		this.hospital.admitPatient();
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(4200 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(7200 / accelerationFactor);
		this.hospital.admitPatient();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.id.hl7sim.threads.processThread#simulateAfternoon()
	 */
	@Override
	public void simulateAfternoon() throws InterruptedException {
		TimeUnit.SECONDS.sleep(4200 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(7200 / accelerationFactor);
		this.hospital.admitPatient();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.id.hl7sim.threads.processThread#simulateEvening()
	 */
	@Override
	public void simulateEvening() throws InterruptedException {
		TimeUnit.SECONDS.sleep(7200 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(1800 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(600 / accelerationFactor);
		this.hospital.admitPatient();
		TimeUnit.SECONDS.sleep(10800 / accelerationFactor);
		this.hospital.admitPatient();
	}

}