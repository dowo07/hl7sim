package com.id.hl7sim.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.id.hl7sim.Hospital;

public class TransferThread implements Runnable {

	public Thread myThread;
	public Hospital hospital;
	public Logger logger = LoggerFactory.getLogger(TransferThread.class);

	public TransferThread(Hospital hospital) {
		this.hospital = hospital;
	}

	public void run() {
		do {
			try {
				this.hospital.transferPatient();
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
