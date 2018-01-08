package com.id.hl7sim.threads;

import com.id.hl7sim.Hospital;

import java.util.concurrent.TimeUnit;

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
			synchronized (this) {
				try {
					TimeUnit.SECONDS.sleep(5);
					this.hospital.dischargePatient();logger.info("Free Beds: " + hospital.getCapacity());
				} catch (InterruptedException e) {}
			}
		} while (true);
	}

}
