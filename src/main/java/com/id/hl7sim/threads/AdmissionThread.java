package com.id.hl7sim.threads;


import com.id.hl7sim.Hospital;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdmissionThread implements Runnable {

	
	public Logger logger = LoggerFactory.getLogger(AdmissionThread.class);
	public Hospital hospital;
	public int accelerationFactor;
	
	
	public AdmissionThread(Hospital hospital, int accelerationFactor) { 
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
					TimeUnit.SECONDS.sleep(7200 / accelerationFactor);
					this.hospital.admitPatient();
					TimeUnit.SECONDS.sleep(1800 / accelerationFactor);
					this.hospital.admitPatient();
					TimeUnit.SECONDS.sleep(600 / accelerationFactor);
					this.hospital.admitPatient();
					TimeUnit.SECONDS.sleep(10800 / accelerationFactor);
					this.hospital.admitPatient();
				} catch (InterruptedException e) {}
			}
		} while (true);
	}

	
}