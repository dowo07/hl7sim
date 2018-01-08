package com.id.hl7sim.threads;

import com.id.hl7sim.Hospital;

import java.util.concurrent.TimeUnit;

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
			synchronized (this) {
				try {
					TimeUnit.SECONDS.sleep(3);
					this.hospital.admitPatient();logger.info("Free Beds: " + hospital.getCapacity());
				} catch (InterruptedException e) {}
			}
		} while (true);
	}

}
