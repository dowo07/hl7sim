package com.id.hl7sim.threads;


import com.id.hl7sim.Hospital;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DischargeThread implements Runnable {

	
	public Hospital hospital;
	public Logger logger = LoggerFactory.getLogger(DischargeThread.class);
	public int accelerationFactor;

	
	public DischargeThread(Hospital hospital, int accelerationFactor) {
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
			synchronized (this) {
				try {
					TimeUnit.SECONDS.sleep(10800 / accelerationFactor);
					this.hospital.dischargePatient();
					TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
					this.hospital.dischargePatient();
				} catch (InterruptedException e) {}
			}
		} while (true);
	}

	
}