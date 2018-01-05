package com.id.hl7sim.threads;

import com.id.hl7sim.Hospital;

public class HospitalStartThread implements Runnable {

	public Thread myThread;
	public Hospital hospital;

	public HospitalStartThread(Hospital hospital) {
		this.hospital = hospital;
	}

	public int getNumberToAdmit() {
		return (int) (this.hospital.getCapacity() * 0.75);
	}

	public void run() {
		do {
			if (hospital.freeBedsCheck()) {
				for (int i = 0; i < this.getNumberToAdmit(); i++) {
					hospital.admitPatient();
				}
			} else {
				break;
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