package com.id.hl7sim.threads;

import com.id.hl7sim.Hospital;

public class TransferThread implements Runnable {

	public Thread myThread;
	public Hospital hospital;

	public TransferThread(Hospital hospital) {
		this.hospital = hospital;
	}

	public void run() {
		do {
			try {
				if (hospital.occupiedBedsCheck()) {
					this.hospital.transferPatient();
					System.out.println("Free Beds: " + hospital.getCapacity());
					Thread.sleep(2000);
				} else {
					Thread.sleep(10000);
				}
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
