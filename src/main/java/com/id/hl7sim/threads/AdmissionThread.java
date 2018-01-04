package com.id.hl7sim.threads;

import com.id.hl7sim.Hospital;

public class AdmissionThread implements Runnable {

	public Thread myThread;
	public Hospital hospital;

	public AdmissionThread(Hospital hospital) {
		this.hospital = hospital;
	}

	public void run() {
		do {
			try {
				this.hospital.admitPatient();
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
