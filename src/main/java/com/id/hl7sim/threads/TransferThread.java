package com.id.hl7sim.threads;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.id.hl7sim.hospital.Hospital;

public class TransferThread implements Runnable, ProcessThread {

	public Hospital hospital;
	public Logger logger = LoggerFactory.getLogger(TransferThread.class);
	public int accelerationFactor;

	public TransferThread(Hospital hospital, int accelerationFactor) {
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
			try {
				simulateWholeDay();
			} catch (InterruptedException e) {
				throw new RuntimeException("Error while simulating day");
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
		TimeUnit.SECONDS.sleep(9600 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(10800 / accelerationFactor);
	}

	public void simulateMorning() throws InterruptedException {
		synchronized (this) {
			this.hospital.transferPatient();
		}
		TimeUnit.SECONDS.sleep(60 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(1740 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(1200 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(2100 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(2100 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(1800 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(1800 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(7200 / accelerationFactor);
		this.hospital.transferPatient();
		this.hospital.transferPatient();
	}

	public void simulateAfternoon() throws InterruptedException {
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(7200 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(4200 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
	}

	public void simulateEvening() throws InterruptedException {
		TimeUnit.SECONDS.sleep(11400 / accelerationFactor);
		this.hospital.transferPatient();
		TimeUnit.SECONDS.sleep(9000 / accelerationFactor);
		this.hospital.transferPatient();
	}

}