package com.id.hl7sim.threads;

import java.util.concurrent.TimeUnit;

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
			synchronized (this) {
				try {
					TimeUnit.SECONDS.sleep(6);
					this.hospital.transferPatient();logger.info("Free Beds: " + hospital.getCapacity());
				} catch (InterruptedException e) {}
			}
		} while (true);
	}

}
