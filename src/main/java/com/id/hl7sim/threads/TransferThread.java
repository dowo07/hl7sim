package com.id.hl7sim.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.id.hl7sim.Hospital;

public class TransferThread implements Runnable {

	public Hospital hospital;
	public Logger logger = LoggerFactory.getLogger(TransferThread.class);

	public TransferThread(Hospital hospital) {
		this.hospital = hospital;
	}

	public void run() {
		do {
			this.hospital.transferPatient();
			logger.info("Free Beds: " + hospital.getCapacity());
			synchronized (this) {
				try {
					wait(2000);
				} catch (InterruptedException e) {}
			}
		} while (true);
	}
}
