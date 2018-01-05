package com.id.hl7sim.threads;

import com.id.hl7sim.Hospital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdmissionThread implements Runnable {

	public Thread myThread;
	public Logger logger = LoggerFactory.getLogger(AdmissionThread.class);
	public Hospital hospital;

	public AdmissionThread(Hospital hospital) {
		this.hospital = hospital;
	}

	public void run() {
		do {
			try {
				this.hospital.admitPatient();
				logger.info("Free Beds: " + hospital.getCapacity());
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

	public void start() {
		if (myThread == null) {
			myThread = new Thread(this);
			myThread.start();
		}
	}
}
