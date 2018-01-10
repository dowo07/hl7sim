package com.id.hl7sim.threads;

public interface ProcessThread {
	
	int getAccelerationFactor();
	
	void setAccelerationFactor(int accelerationFactor);
	
	void run();

	void simulateWholeDay() throws InterruptedException;

	void simulateNight() throws InterruptedException;

	void simulateMorning() throws InterruptedException;

	void simulateAfternoon() throws InterruptedException;

	void simulateEvening() throws InterruptedException;

}