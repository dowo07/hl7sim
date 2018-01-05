package com.id.hl7sim.threads;

import com.id.hl7sim.Hospital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdmissionThread implements Runnable {

	public Logger logger = LoggerFactory.getLogger(AdmissionThread.class);
	public Hospital hospital;

	public AdmissionThread(Hospital hospital) {
		this.hospital = hospital;
	}

	public void run() {
		do {
			this.hospital.admitPatient();
			logger.info("Free Beds: " + hospital.getCapacity());
			synchronized (this) {
				try {
					wait(2000);
				} catch (InterruptedException e) {}
			}
		} while (true);
	}

}
