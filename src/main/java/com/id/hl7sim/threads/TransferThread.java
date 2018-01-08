package com.id.hl7sim.threads;


import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.id.hl7sim.Hospital;


public class TransferThread implements Runnable {

	
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
			synchronized (this) {
				try {
					TimeUnit.SECONDS.sleep(3600 / accelerationFactor);
					this.hospital.transferPatient();
				} catch (InterruptedException e) {}
			}
		} while (true);
	}

	
}