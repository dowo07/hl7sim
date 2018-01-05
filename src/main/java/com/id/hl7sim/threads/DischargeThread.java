package com.id.hl7sim.threads;

import com.id.hl7sim.Hospital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DischargeThread implements Runnable {

	public Hospital hospital;
	public Logger logger = LoggerFactory.getLogger(DischargeThread.class);

	public DischargeThread(Hospital hospital) {
		this.hospital = hospital;
	}

	public void run() {
		do {
			this.hospital.dischargePatient();
			logger.info("Free Beds: " + hospital.getCapacity());
			synchronized (this) {
				try {
					wait(2000);
				} catch (InterruptedException e) {}
			}
		} while (true);
	}

}
